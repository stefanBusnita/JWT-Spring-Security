/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.common.aspects;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Aspect that uses service pointcut where save functions are applied. Errors
 * and params will be logged accordigly in the corresponding logger file. #Test
 * if the result is negative => error and print message #else save was
 * succesfull, do nothing, or in dev environment print parameters.
 *
 * @author stefan.busnita
 */
@Aspect
@Component
@Profile(value = "dev")
public class LoggerAspect {

    @Pointcut("execution(* com.jwt.services..*.set*(..))")
    protected void setPointcut() {
    }

    /**
     * Capture returning of every call to a set function to print parameters of
     * failed call. Will be used for an easier debug.
     *
     * @param joinPoint
     * @param retVal
     */
    @AfterReturning(pointcut = "setPointcut()", returning = "retVal")
    public void afterSuccessfulReturn(JoinPoint joinPoint, Object retVal) {
        Date date = new Date();
        System.out.println("Accessed the save pointcut");
        System.out.println("Returned value " + retVal.getClass());

        Class<?> classCast = retVal.getClass();
        Object[] signatureArgs = joinPoint.getArgs();

        for (Object signatureArg : signatureArgs) {
            //TEMPORARY DEV FIX, UNTILL A BETTER SOLUTION IS FOUND
            if (retVal.getClass().getName().equals(signatureArg.getClass().getName())) {

                String classInfo = classCast.cast(retVal).toString().split(",")[0];
                String replacedString = classInfo.replaceAll("result=", "");
                Integer result = Integer.parseInt(replacedString);

                if (result < 0) {
                    System.out.println("Reponse " + classCast.cast(retVal));
                    //TODO REPLACE WITH LOGGER FILE CALL
                    System.out.println(date + "->Salvarea a esuat cu codul " + result + " cu parametrii " + classCast.cast(signatureArg));
                } else {
                    System.out.println("Reponse " + classCast.cast(retVal));
                    //TODO REPLACE WITH LOGGER FILE CALL
                    System.out.println(date + "->Salvarea a fost efectuata cu success cu codul " + result + " cu parametrii " + classCast.cast(signatureArg));
                }

            }
        }
    }
    
    @AfterThrowing(pointcut = "setPointcut()")
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("Accessed the save pointcut");
        
        Object[] signatureArgs = joinPoint.getArgs();

        for (Object signatureArg : signatureArgs) {
        	Class<?> classCast = signatureArg.getClass();
        	String classInfo = classCast.cast(signatureArg).toString();
        	System.out.println("Returned value " + classInfo);
        }
    }

}

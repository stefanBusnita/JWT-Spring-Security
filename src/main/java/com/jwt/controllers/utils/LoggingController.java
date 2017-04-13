/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.controllers.utils;

import com.jwt.services.utils.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Log frontend errors
 * Can be succesfully used to locate error location faster
 * @author stefan.busnita
 */
@Controller
@RequestMapping(value = "rest/log-error")
public class LoggingController {
    
    @Autowired
    LoggingService loggingService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody()
    public void logFromInterface(@RequestBody String loggingMessage) {
        loggingService.logFromInterface(loggingMessage);
    }
    
}

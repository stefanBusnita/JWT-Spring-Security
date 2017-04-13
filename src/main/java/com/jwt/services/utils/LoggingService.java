/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.services.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author stefan.busnita
 */
@Service
public class LoggingService {

    private  Logger _LOGGER = LoggerFactory.getLogger(LoggingService.class);
    
    public void logFromInterface(String loggingMessage){
        _LOGGER.error(loggingMessage, this);
    }
    
}

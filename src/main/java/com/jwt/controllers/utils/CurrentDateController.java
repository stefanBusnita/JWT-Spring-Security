/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.controllers.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author stefan.busnita Get current date after login from server side. Used
 * for validation, avoid js date, might be changed by user.
 */
@Controller
@RequestMapping(value = "rest/current-date")
public class CurrentDateController {

    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody()
    public Map<String, String> getCurrentDate() {
        Map<String, String> date = new HashMap<String, String>();

        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");

        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        date.put("currentDate", reportDate);
        return date;
    }

}

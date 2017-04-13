package com.jwt.controllers.test;

import com.jwt.common.json.TestJson;
import com.jwt.services.utils.test.TestPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Stefan on 14.04.2017.
 */
@Controller
@RequestMapping(value = "rest/permissions-data")
public class UserController {

    private TestPermissionsService testPermissionsService;

    @Autowired
    public UserController(TestPermissionsService testPermissionsService){
        this.testPermissionsService = testPermissionsService;
    }

    /**
     * Simple mock controller for interface testing
     * @param type
     * @return
     */
    @RequestMapping(method =  RequestMethod.GET,produces = "application/json",params = {"type"})
    @ResponseBody
    public TestJson getUserData(@RequestParam Integer type){
        if(type.equals(1)){
            return this.testPermissionsService.getUserTestJson();
        }else
        if(type.equals(2)){
            return this.testPermissionsService.getGuestTestJson();
        }else
        if(type.equals(3)){
            return this.testPermissionsService.getAdminTestJson();
        }
        return  null;
    }

}

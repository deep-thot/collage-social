package se.collagekoren.controller;

/**
 * Created by Eruenion on 30/12/15.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    @RequestMapping("/user")
    public Principal getUser(Principal principal){
        return principal;
    }
}

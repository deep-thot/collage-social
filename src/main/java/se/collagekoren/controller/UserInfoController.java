package se.collagekoren.controller;

/**
 * Created by Eruenion on 30/12/15.
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserInfoController {

    @RequestMapping("/user")
    public Map<String, Object> getUser(OAuth2Authentication principal){
        return (Map<String, Object>) principal.getUserAuthentication().getDetails();
    }
}

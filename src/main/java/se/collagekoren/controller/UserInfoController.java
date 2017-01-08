package se.collagekoren.controller;

/**
 * Created by Eruenion on 30/12/15.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.collagekoren.domain.CurrentUser;
import se.collagekoren.domain.Profile;
import se.collagekoren.repository.ProfileRepository;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserInfoController {


    @SuppressWarnings("unchecked")
    @RequestMapping("/user")
    public CurrentUser getUser(CurrentUser user){
       return user;
    }

}

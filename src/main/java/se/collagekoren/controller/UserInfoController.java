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

    private final CurrentProfileFactory profileFactory;

    @Autowired
    public UserInfoController(CurrentProfileFactory profileFactory) {
        this.profileFactory = profileFactory;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/user")
    public CurrentUser getUser(OAuth2Authentication principal){
        Map<String, Object> userDetails = (Map<String, Object>) principal.getUserAuthentication().getDetails();

        Profile profile = profileFactory.getCurrentProfile(principal).orElse(null);
        return new CurrentUser(profile, isAdmin(profile), userDetails);
    }

    private boolean isAdmin(Profile profile) {
        return Optional.ofNullable(profile).map(Profile::getEmail).filter(e -> e.equals("eruenion@gmail.com")).isPresent();
    }
}

package se.collagekoren.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import se.collagekoren.controller.CurrentProfileFactory;
import se.collagekoren.domain.CurrentUser;
import se.collagekoren.domain.Profile;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Jonatan on 2017-01-08.
 */

public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver{

    private final CurrentProfileFactory currentProfileFactory;

    @Autowired
    public CurrentUserMethodArgumentResolver(CurrentProfileFactory currentProfileFactory) {
        this.currentProfileFactory = currentProfileFactory;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)) {
            return CurrentUser.guest();
        }
        OAuth2Authentication oauth = (OAuth2Authentication) authentication;
        if(!oauth.isAuthenticated()){
            return CurrentUser.guest();
        }
        Optional<Profile> currentProfile = currentProfileFactory.getCurrentProfile(oauth);
        Map<String, Object> userDetails = (Map<String, Object>) oauth.getUserAuthentication().getDetails();
        return currentProfile.map(p -> CurrentUser.verified(p, isAdmin(p), userDetails)).orElse(CurrentUser.loggedInUser(userDetails));

    }

    private boolean isAdmin(Profile profile) {
        return Optional.ofNullable(profile).map(Profile::getEmail).filter(e -> e.equals("eruenion@gmail.com")).isPresent();
    }
}

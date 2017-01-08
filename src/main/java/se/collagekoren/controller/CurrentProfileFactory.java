package se.collagekoren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import se.collagekoren.domain.Profile;
import se.collagekoren.repository.ProfileRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Created by Jonatan on 2017-01-07.
 */
@Service
public class CurrentProfileFactory {

    private final ProfileRepository profileRepository;

    @Autowired
    public CurrentProfileFactory(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getCurrentProfile(OAuth2Authentication auth2Authentication){
        return getCurrentProfile(getUserDetails(auth2Authentication));
    }

    @SuppressWarnings("unchecked")
    Optional<Profile> getCurrentProfile(Map<String, Object> details) {
        List<Map<String, String>> emails = (List<Map<String, String>>) details.getOrDefault("emails", emptyList());
        return emails.stream().map(m -> m.get("value")).map(profileRepository::findFirstByEmail).filter(Objects::nonNull).findFirst();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getUserDetails(OAuth2Authentication authentication) {
        return (Map<String, Object>)authentication.getUserAuthentication().getDetails();
    }
}

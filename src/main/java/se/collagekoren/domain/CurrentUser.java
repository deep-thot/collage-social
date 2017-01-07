package se.collagekoren.domain;

import java.util.Map;

/**
 * Created by Jonatan on 2017-01-07.
 */
public class CurrentUser {
    private final Profile profile;
    private final boolean admin;
    private final Map<String, Object> userDetails;

    public CurrentUser(Profile profile, boolean admin, Map<String, Object> userDetails) {
        this.profile = profile;
        this.admin = admin;
        this.userDetails = userDetails;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isLoggedIn(){
        return profile != null;
    }

    public Map<String, Object> getUserDetails() {
        return userDetails;
    }
}

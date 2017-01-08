package se.collagekoren.domain;

import java.util.Optional;

/**
 * Created by Eruenion on 10/11/16.
 */
public class ProfileView {
    private final Profile profile;
    private final boolean loggedIn;
    private final String formattedBio;

    public static ProfileView profile(Profile profile){
        return new ProfileView(profile);
    }

    public static ProfileView loggedInProfile(Profile profile){
        return new ProfileView(profile, true);
    }

    private ProfileView(Profile profile, boolean loggedIn){
        this.profile = profile;
        this.loggedIn = loggedIn;
        this.formattedBio = Optional.ofNullable(profile.getBio()).map(s -> s.replace("\n", "<br />")).orElse("");
    }

    private ProfileView(Profile profile) {
        this(profile, false);
    }

    public String getFirstName(){
        return profile.getName().split(" ")[0];
    }

    public Integer getId() {
        return profile.getId();
    }

    public String getName() {
        return profile.getName();
    }

    public String getBio() {
        return formattedBio;
    }

    public String getRawBio(){
        return profile.getBio();
    }

    public String getFbLink() {
        return profile.getFbLink();
    }

    public String getLinkedInProfile() {
        return profile.getLinkedInProfile();
    }

    public String getLastFmProfile() {
        return profile.getLastFmProfile();
    }

    public Voice getVoice() {
        return profile.getVoice();
    }

    public byte[] getImage() {
        return profile.getImage();
    }

    public String getEmail() {return profile.getEmail();}

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public Integer getStarted() {
        return profile.getStarted();
    }

    public String getAddress() {
        return profile.getAddress();
    }

    public String getPhoneNumber() {
        return profile.getPhoneNumber();
    }
}

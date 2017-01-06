package se.collagekoren.domain;

/**
 * Created by Eruenion on 10/11/16.
 */
public class ProfileView {
    private final Profile profile;

    public ProfileView(Profile profile) {
        this.profile = profile;
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

    public String getImage() {
        return profile.getImage();
    }
}

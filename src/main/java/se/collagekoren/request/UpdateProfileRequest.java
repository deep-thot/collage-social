package se.collagekoren.request;

/**
 * Created by Jonatan on 2017-01-07.
 */
public class UpdateProfileRequest {
    private Integer id;
    private String bio;
    private String fbLink;
    private String lastFmProfile;
    private Integer started;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getLastFmProfile() {
        return lastFmProfile;
    }

    public void setLastFmProfile(String lastFmProfile) {
        this.lastFmProfile = lastFmProfile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStarted() {
        return started;
    }

    public void setStarted(Integer started) {
        this.started = started;
    }
}

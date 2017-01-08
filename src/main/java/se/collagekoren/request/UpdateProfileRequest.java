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
    private String address;
    private String phoneNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

package se.collagekoren.request;

import se.collagekoren.domain.Voice;

/**
 * Created by Jonatan on 2015-11-26.
 */
public class ProfileRequest {

    private String name;
    private String bio;
    private String fbLink;
    private String linkedInProfile;
    private String lastFmProfile;
    private Voice voice;
    private String image;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(String linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }

    public String getLastFmProfile() {
        return lastFmProfile;
    }

    public void setLastFmProfile(String lastFmProfile) {
        this.lastFmProfile = lastFmProfile;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

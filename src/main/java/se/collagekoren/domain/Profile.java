package se.collagekoren.domain;

import javax.persistence.*;

/**
 * Created by Jonatan on 2015-11-25.
 */
@Entity
public class Profile {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String bio;
    private String fbLink;
    private String linkedInProfile;
    private String lastFmProfile;
    private Voice voice;
    private String image;

    protected Profile(){

    }

    public Profile(String name, String bio, String fbLink, String linkedInProfile, String lastFmProfile, Voice voice, String image) {
        this.name = name;
        this.bio = bio;
        this.fbLink = fbLink;
        this.linkedInProfile = linkedInProfile;
        this.lastFmProfile = lastFmProfile;
        this.voice = voice;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }



    public String getFbLink() {
        return fbLink;
    }

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public String getLastFmProfile() {
        return lastFmProfile;
    }

    public Voice getVoice() {
        return voice;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", voice=" + voice +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public void setLinkedInProfile(String linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }

    public void setLastFmProfile(String lastFmProfile) {
        this.lastFmProfile = lastFmProfile;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

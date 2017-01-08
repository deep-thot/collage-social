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
    @Column(length = 1000000)
    private String bio;
    private String fbLink;
    private String linkedInProfile;
    private String lastFmProfile;
    private Voice voice;
    private Integer started;
    @Column(length=1000)
    private String address;
    private String phoneNumber;

    @Lob
    private byte[] image;

    private String email;

    protected Profile(){

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

    public Profile(String name, String bio, String fbLink, String linkedInProfile, String lastFmProfile, Voice voice, Integer started, byte[] image, String email, String address, String phoneNumber) {
        this.name = name;
        this.bio = bio;
        this.fbLink = fbLink;
        this.linkedInProfile = linkedInProfile;
        this.lastFmProfile = lastFmProfile;
        this.voice = voice;
        this.started = started;
        this.image = image;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public byte[] getImage() {
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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}

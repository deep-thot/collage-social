package se.collagekoren.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created by Jonatan on 2017-01-07.
 */
@Entity
public class Image {
    @Id
    private Integer profileId;
    private byte[] imageData;

    public Image(Integer profileId, byte[] imageData) {
        this.profileId = profileId;
        this.imageData = imageData;
    }

    public Image() {
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}

package se.collagekoren.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created by Jonatan on 2017-01-07.
 */
@Entity
public class RequestedProfile {

    @Id
    private String email;

    private String namn;

    @Column(length = 1000000)
    private String dataBlob;

    public RequestedProfile(String email, String namn, String dataBlob) {
        this.email = email;
        this.namn = namn;
        this.dataBlob = dataBlob;
    }

    public RequestedProfile() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getDataBlob() {
        return dataBlob;
    }

    public void setDataBlob(String dataBlob) {
        this.dataBlob = dataBlob;
    }
}

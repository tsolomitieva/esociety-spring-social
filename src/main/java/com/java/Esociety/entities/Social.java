package com.java.Esociety.entities;

import javax.persistence.*;

@Entity
@Table(name="social")
public class Social {

    @Id
    @Column(name="social_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int social_id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="facebookname")
    private String facebookName;

    @Column(name="facebookphoto")
    private String facebookPhoto;

    @Column(name="instagramname")
    private String instagramName;

    @Column(name="instagramphoto")
    private String instagramPhoto;

    @Column(name="twittername")
    private String twitterName;

    @Column(name="twitterphoto")
    private String twitterPhoto;

    public int getSocial_id() {
        return social_id;
    }

    public void setSocial_id(int social_id) {
        this.social_id = social_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName (String facebookName) {
        this.facebookName = facebookName;
    }

    public String getFacebookPhoto() {
        return facebookPhoto;
    }

    public void setFacebookPhoto(String facebookPhoto) {
        this.facebookPhoto = facebookPhoto;
    }

    public String getInstagramName() {
        return instagramName;
    }

    public void setInstagramName(String instagramName) {
        this.instagramName = instagramName;
    }

    public String getInstagramPhoto() {
        return instagramPhoto;
    }

    public void setInstagramPhoto(String instagramPhoto) {
        this.instagramPhoto = instagramPhoto;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public void setTwitterName(String twitterName) {
        this.twitterName = twitterName;
    }

    public String getTwitterPhoto() {
        return twitterPhoto;
    }

    public void setTwitterPhoto(String twitterPhoto) {
        this.twitterPhoto = twitterPhoto;
    }
}

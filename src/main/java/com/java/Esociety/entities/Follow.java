package com.java.Esociety.entities;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="followers")
public class Follow {

    @Id
    @Column(name="follow_id")
    private int follow_id;


    @Column(name="follower")
    private int follower;


    @Column(name="following")
    private int following;



    public int getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(int follow_id) {
        this.follow_id = follow_id;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
}

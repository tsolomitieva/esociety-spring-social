package com.java.Esociety.entities;



import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name="posts")
public class Post {

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;

    @Column(name="date")
    private Timestamp date;


    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    @Column(name="description")
    private String description;

    @Column(name="image")
    private String image;

    //whether post is from facebook, instagram, twitter or esociety
    @Column(name="item")
    private String item;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}

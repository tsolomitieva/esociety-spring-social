package com.java.Esociety.entities;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;



    @Column(name="password")
    @NotEmpty(message = "Password cannot be empty.")
    private String password;

    @Column(name="firstname")
    @NotEmpty(message = "Firstname cannot be empty.")
    private String firstname;

    @Column(name="lastname")
    @NotEmpty(message = "Lastname cannot be empty.")
    private String lastname;

    @Column(name="email")
    @NotEmpty(message = "Email cannot be empty.")
    private String email;

  /*  @Column(name="title")
    @Nullable
    private String title;

    @Column(name="phone")
    @Nullable
    private String phone;

    @Column(name="birthday")
    @Nullable
    private String birthday;

  @Column(name="gender")
    @Nullable
    private String gender;
*/
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @OneToMany(mappedBy="user")
    private List<Post> posts;

    @OneToOne(mappedBy="user")
    private Social social;

    @Column(name = "photo", length = Integer.MAX_VALUE, nullable = true)
    private String photo;

    @Column(name= "birthday")
    private Date birthday;

    @Column(name= "gender")
    private String gender;

    @Column(name= "phone")
    private String phone;

    @Column(name= "title")
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="followers",
            joinColumns=@JoinColumn(name="follower"),
            inverseJoinColumns=@JoinColumn(name="following")
    )
    private Set<User> following;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="followers",
            joinColumns=@JoinColumn(name="following"),
            inverseJoinColumns=@JoinColumn(name="follower")
    )
    private Set<User> followers;

    @OneToMany
    @JoinTable(name="likes", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likedPosts;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public Social getSocial() {
      return social;
    }

   public void setSocial(Social social) {
      this.social = social;
   }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



   public String getImgData(byte[] byteData) {
            return Base64.getMimeEncoder().encodeToString(byteData);
   }

}

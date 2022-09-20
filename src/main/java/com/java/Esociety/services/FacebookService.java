package com.java.Esociety.services;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.Social;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Facebook;

import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacebookService {

    private FacebookConnectionFactory factory = new FacebookConnectionFactory("1044551659727911",
            "c3ee51fc1b33108f554a8a9ff8415115");

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;
    @Autowired
    SocialRepository socialRepository;
    @Autowired
    UsersConnectionRepository usersConnectionRepository;


    public List<Post> getUsersPosts() {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        User user = userRepository.findByEmail(auth.getName());

        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);

        if(connection==null){
            return new ArrayList<Post>() ;

        }

        Facebook facebook = connection.getApi();

        ArrayList<Post> posts = new ArrayList<Post>();

        //fetch facebook user's posts
        for (org.springframework.social.facebook.api.Post p : facebook.feedOperations().getFeed()){
            if(p.getDescription()!=null || p.getPicture()!=null) {
                com.java.Esociety.entities.Post myPost = new com.java.Esociety.entities.Post();
                myPost.setUser(user);
                //myPost.setDate((Timestamp) p.getCreatedTime());
                myPost.setImage(p.getPicture());
                myPost.setDescription(p.getDescription());
                myPost.setItem("facebook");
                posts.add(myPost);
            }
        }



        return posts;
    }


   public List<Post> getFacebookFeed() {

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
       User user = userRepository.findByEmail(auth.getName());

       Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);

       if(connection==null){
           return new ArrayList<Post>() ;

       }

       Facebook facebook = connection.getApi();

       ArrayList<Post> posts = new ArrayList<Post>();

        //fetch user's posts
        for (org.springframework.social.facebook.api.Post p : facebook.feedOperations().getFeed()){
            com.java.Esociety.entities.Post myPost = new com.java.Esociety.entities.Post();
            if(p.getDescription()!=null || p.getPicture()!=null) {
                myPost.setUser(user);
                //myPost.setDate((Timestamp) p.getCreatedTime());
                myPost.setImage(p.getPicture());
                myPost.setDescription(p.getDescription());
                myPost.setItem("facebook");


                //take every comment
               // for(org.springframework.social.facebook.api.Comment c:facebook.commentOperations().getComments(p.getObjectId())) {
                 //   com.java.Esociety.entities.Comment myComment = new com.java.Esociety.entities.Comment();
                 //   myComment.setDescription(c.getMessage());
                 //   //get user that did the comment
                 //   Social socialComment = socialRepository.findByFacebookName(c.getFrom().getName());
                 //   User userComment = socialComment.getUser();
                 //  myComment.setUser(userComment);
                 //   myPost.getComments().add(myComment);
              //  }
                posts.add(myPost);
            }
        }


        //receive a list with facebook friends
        List<String> friends =facebook.friendOperations().getFriendIds();

        //get posts from every friend
        for (int i=0; i<friends.size(); i++){

            Social social= socialRepository.findByFacebookName(facebook.friendOperations().getFriendProfiles().get(i).getName());
             User postUser= social.getUser();
           // User postUser = userRepository.findByfacebook.friendOperations().getFriendProfiles().get(i).getEmail());
            //if i follow friend in e society
            if (user.getFollowing().contains(postUser)) {
                for (org.springframework.social.facebook.api.Post p : facebook.feedOperations().getPosts(friends.get(i))){
                    com.java.Esociety.entities.Post myPost = new com.java.Esociety.entities.Post();
                    if(p.getDescription()!=null || p.getPicture()!=null) {
                        myPost.setUser(postUser);
                        //myPost.setDate((Timestamp) p.getCreatedTime());
                        myPost.setImage(p.getPicture());
                        myPost.setDescription(p.getDescription());
                        myPost.setItem("facebook");
                        //take every comment
                     //   for(org.springframework.social.facebook.api.Comment c:facebook.commentOperations().getComments(p.getObjectId())) {
                       //     com.java.Esociety.entities.Comment myComment = new com.java.Esociety.entities.Comment();
                       //     myComment.setDescription(c.getMessage());
                       //     //get user that did the comment
                       //     Social socialComment = socialRepository.findByFacebookName(c.getFrom().getName());
                       //     User userComment = socialComment.getUser();
                       //    myComment.setUser(userComment);
                        //    myPost.getComments().add(myComment);
                      //  }


                        posts.add(myPost);
                    }
                }
            }

        }
        return posts;
    }

    public void saveFacebookData(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        Facebook facebook = connection.getApi();

        //add user's facebook name to social table
        Social social= user.getSocial();
        String name=connection.getDisplayName();
        social.setFacebookName(name);
        social.setFacebookPhoto(connection.getImageUrl());
        socialRepository.save(social);
    }


}

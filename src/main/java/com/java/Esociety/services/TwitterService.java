package com.java.Esociety.services;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.Social;
import com.java.Esociety.entities.User;

import com.java.Esociety.repositories.SocialRepository;
import com.java.Esociety.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterService {

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;

    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @Autowired
    SocialRepository socialRepository;


    public void saveTwitterData() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);
        Twitter twitter = connection.getApi();

        //add user's twitter name to social table
        Social social = user.getSocial();
        String name = twitter.userOperations().getUserProfile().getName();
        social.setTwitterName((name));
        //social.setFacebookPhoto(connection.getImageUrl());
        socialRepository.save(social);
    }

    public List<Post> getUserTweets() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        User user = userRepository.findByEmail(auth.getName());

        Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);

        Twitter twitter = connection.getApi();
        List<Post> posts = new ArrayList<Post>();
        //fetch user's tweets into posts
        for (Tweet tweet: twitter.timelineOperations().getUserTimeline()){
            Post myPost = new Post();
            myPost.setUser(user);
            myPost.setDescription(tweet.getText());
            myPost.setTotal_likes(tweet.getFavoriteCount());
            myPost.setItem("twitter");

            posts.add(myPost);
        }
        return posts;
    }

    public List<Post> getTwitterFeed() {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        User user = userRepository.findByEmail(auth.getName());
        Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);

        Twitter twitter = connection.getApi();
        List<Post> posts = new ArrayList<Post>();



        //fetch friend's tweets into esociety posts
        for (Tweet tweet : twitter.timelineOperations().getHomeTimeline()) {
            Post myPost = new Post();
            //if(tweet.getText()!=null || p.getPicture()!=null) {
            for (User friend : user.getFollowing()) {
                if (friend.getSocial().getTwitterName()!=null) {
                    if (friend.getSocial().getTwitterName().equals(tweet.getUser().getName())) {
                        myPost.setUser(friend);
                        //myPost.setDate((Timestamp) p.getCreatedTime());
                        //myPost.setImage(tweet);
                        myPost.setDescription(tweet.getText());
                        myPost.setTotal_likes(tweet.getFavoriteCount());
                        myPost.setItem("twitter");


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



            }
        }
        return posts;
    }

    public void logout(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        user.getSocial().setTwitterName(null);
        user.getSocial().setTwitterPhoto(null);
        socialRepository.save(user.getSocial());
    }
}

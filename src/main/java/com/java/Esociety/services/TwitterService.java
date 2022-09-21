package com.java.Esociety.services;

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
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;

    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @Autowired
    SocialRepository socialRepository;



    public void saveTwitterData(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);
       Twitter twitter=connection.getApi();

        //add user's twitter name to social table
        Social social= user.getSocial();
        String name = twitter.userOperations().getUserProfile().getName();
        social.setTwitterName((name));
        //social.setFacebookPhoto(connection.getImageUrl());
        socialRepository.save(social);
    }
}

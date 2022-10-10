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
import org.springframework.social.instagram.api.Instagram;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstagramService {

    @Autowired
    userRepository userRepository;

    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @Autowired
    SocialRepository socialRepository;

    public void saveInstagramData(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName());
        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        Connection<Instagram> connection = repository.findPrimaryConnection(Instagram.class);
        Instagram instagram = connection.getApi();


        //add user's Instagram name to social table
        Social social= user.getSocial();

        instagram.userOperations().getUser().getUsername();
       social.setInstagramName( instagram.userOperations().getUser().getUsername());
       // social.setFacebookPhoto(connection.getImageUrl());
       // socialRepository.save(social);
    }
    public List<Post> getInstagramFeed() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(auth.getName());
        User user = userRepository.findByEmail(auth.getName());

        Connection<Instagram> connection = repository.findPrimaryConnection(Instagram.class);






        Instagram instagram = connection.getApi();



        return new ArrayList<Post>();
    }
}

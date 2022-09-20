package com.java.Esociety.services;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.PostRepository;
import com.java.Esociety.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    userRepository userRepo;

    @Autowired
    PostRepository postRepository;



    public Set<Post> getFriendsPosts() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());
        Set<Post> AllPosts= user.getPosts();
        AllPosts.addAll(postRepository.findAllByUserIn(user.getFollowing()));
        return AllPosts;
    }





}

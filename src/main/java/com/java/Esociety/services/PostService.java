package com.java.Esociety.services;

import com.java.Esociety.entities.Follow;
import com.java.Esociety.entities.Like;
import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.LikeRepository;
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

    @Autowired
    LikeRepository likeRepository;


    public List<Post> getFriendsPosts() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());
        List<Post> AllPosts = user.getPosts();
        AllPosts.addAll(postRepository.findAllByUserIn(user.getFollowing()));
        return AllPosts;
    }


    public boolean likePost(Post post) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());

        //unlike
        for (Like like : post.getLikes()) {
            if (like.getUser() == user) {

                likeRepository.delete(like);
                return false;


            }
        }

        //like
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);
        return true;
    }


}

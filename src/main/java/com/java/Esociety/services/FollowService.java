package com.java.Esociety.services;

import com.java.Esociety.entities.Follow;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.FollowRepository;
import com.java.Esociety.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

   @Autowired
   FollowRepository followRepository;
   @Autowired
   userRepository userRepo;


   public boolean followUser(User profile_user){

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userRepo.findByEmail(auth.getName());

       //unfollow
       if (user.getFollowing().contains(profile_user)){

           followRepository.deleteByFollowerAndFollowing(user.getUser_id(),profile_user.getUser_id());
           return false;

        }
       //follow
       else{
           Follow follow = new Follow();
           follow.setFollower(user.getUser_id());
           follow.setFollowing(profile_user.getUser_id());
           followRepository.save(follow);
           return true;
       }




   }

   // check if user is already following this profile
   public boolean CheckIfFollowing(User profile_user){

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userRepo.findByEmail(auth.getName());

       if (user.getFollowing().contains(profile_user)){
           return true;

       }else{

          return false;
       }



   }
}

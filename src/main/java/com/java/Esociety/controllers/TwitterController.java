package com.java.Esociety.controllers;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.userRepository;
import com.java.Esociety.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;


    @GetMapping("/twitter/connect")
    public String getTwitterConnection()
    {
        twitterService.saveTwitterData();
        return "twitterConnected";

    }

    @GetMapping("/home/twitter")
    public String home(Model model)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        if(user==null) System.out.println("User is null");
        model.addAttribute("User",user);


        //List<Post> posts= new ArrayList<Post>();

        List<Post> posts=twitterService.getTwitterFeed();
         posts.addAll(twitterService.getUserTweets());



        Collections.shuffle(posts);
        model.addAttribute("Feed",posts);



        return "twitter_home";

    }

}

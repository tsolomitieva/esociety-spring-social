package com.java.Esociety.controllers;

import com.java.Esociety.entities.Comment;
import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.SocialRepository;
import com.java.Esociety.repositories.userRepository;
import com.java.Esociety.services.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class FacebookController {



    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;

    @Autowired
    FacebookService facebookService;




    @GetMapping("/home/facebook")
    public String home(Model model)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        if(user==null) System.out.println("User is null");
        model.addAttribute("User",user);


        //List<Post> posts= new ArrayList<Post>();

        List<Post> posts=facebookService.getFacebookFeed();


        Collections.shuffle(posts);
        model.addAttribute("Feed",posts);



        return "facebook_home";

    }

    @GetMapping("/facebook/logout")
    public RedirectView logout(Model model)
    {


        facebookService.logout();

        return new RedirectView("/profile");

    }
}

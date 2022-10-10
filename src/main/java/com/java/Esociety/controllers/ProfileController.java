package com.java.Esociety.controllers;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.userRepository;
import com.java.Esociety.services.FacebookService;
import com.java.Esociety.services.TwitterService;
import com.java.Esociety.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;

    @Autowired
    FacebookService facebookService;

    @Autowired
    TwitterService twitterService;

    @Autowired
    com.java.Esociety.services.userService userService;

    @GetMapping("/profile")
    public String Me(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        List<Post> posts = user.getPosts();


        if (user.getSocial().getFacebookName() != null) {
          //  posts.addAll(facebookService.getFacebookFeed());
        }
        if (user.getSocial().getTwitterName() != null) {

          //  posts.addAll(twitterService.getTwitterFeed());
          //  posts.addAll(twitterService.getUserTweets());
        }
        Collections.shuffle(posts);
        model.addAttribute("Feed", posts);

        //save to model
        model.addAttribute("User", user);
        model.addAttribute("post", new Post());

        return "profile";
    }

    @GetMapping("/profile/settings")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        model.addAttribute("user", user);
        return "settings";

    }

    @PostMapping(value = "/profile/update")
    public RedirectView updateProfile(@ModelAttribute(name = "user") User user, Model model) {

        userRepository.save(user);
        model.addAttribute("User", user);
        return new RedirectView("/profile/settings");
    }

    @PostMapping(value = "/profile/password")
    public RedirectView updatePassword(@RequestParam(name = "old_password") String old_password, @RequestParam(name = "new_password") String new_password, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        model.addAttribute("User", user);
        model.addAttribute("message", userService.changePassword(old_password, new_password));
        return new RedirectView("/profile/settings");


    }
    @PostMapping(value = "/profile/photo")
    public RedirectView updatePhoto(@RequestParam(name = "photo") String photo,  Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        user.setPhoto('/'+photo);
        userRepository.save(user);
        model.addAttribute("User", user);

        return new RedirectView("/profile/settings");


    }
}

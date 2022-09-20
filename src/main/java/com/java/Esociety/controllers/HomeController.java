package com.java.Esociety.controllers;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.Role;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.roleRepository;
import com.java.Esociety.repositories.userRepository;
import com.java.Esociety.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    FollowService followService;

    @Autowired
    InstagramService instagramService;

    @Autowired
    private userRepository userRepo;

    @Autowired
    roleRepository roleRepository;

    @Autowired
    PostService postService;
    @Autowired
    FacebookService facebookService;

    @Autowired
    TwitterService twitterService;

    @Autowired
    userService userService;

    @Autowired
    DataSource dataSource;

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    TextEncryptor textEncryptor;

    @RequestMapping("/home")
    public String home(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());
        if(user==null) System.out.println("User is null");
        model.addAttribute("User",user);

        model.addAttribute("post",new Post());
      // List<Post>  posts= new ArrayList<Post>();

       List<Post> posts=facebookService.getFacebookFeed();
        posts.addAll(user.getPosts());
        Collections.shuffle(posts);
        model.addAttribute("Feed",posts);
        return "index";

   }

    @RequestMapping("/instagramConnected")
    public String instagramConnected()
    {

        instagramService.saveInstagramData();
        return "instagramConnected";

    }

    @RequestMapping("/twitterConnected")
    public String twitterConnected()
    {

        twitterService.saveTwitterData();
        return "twitterConnected";

    }

    @PostMapping (value="/home/profile")
    public String profile(@RequestParam(name="email") String email, Model model)
    {

        User profile_user= userRepo.findByEmail(email);
        model.addAttribute("profile",profile_user);
        model.addAttribute("following",followService.CheckIfFollowing(profile_user));


        return "user_profile";
    }

    @RequestMapping("/home/me")
    public String Me(Model model)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());
        List<Post>  posts= new ArrayList<Post>();


       // get facebook posts
       //List<Post> posts = facebookService.getUsersPosts();

        //add e-society posts
       posts.addAll(user.getPosts());

        //save to model
        model.addAttribute("User",user);
        model.addAttribute("post",new Post());
        model.addAttribute("posts",posts);




        return "profile";
    }

    @RequestMapping("/home/posts")
    public String MyPosts(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());
        if(user==null) System.out.println("User is null");
        model.addAttribute("User",user);
        model.addAttribute("Posts", user.getPosts());
        model.addAttribute("post",new Post());

        return "index";

    }



    @GetMapping(value="/admin")
    public String adminHome(){
        return "admin";
    }





   @GetMapping("login")
   public String login(){


        return "login";
    }
    @GetMapping("/register")
    public String register(Model model ){
        model.addAttribute("user", new User());

        return "register";
    }


    @PostMapping("/register_submit")
    public String signup(@Valid User user, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("user",user);
            return "register";
        }

      //  if (user.getFirstname().length() < 2){
       //     model.addAttribute("errorFirstName","First name required and must be at least 2 characters long");
      //      return "register";
      //  }

       // if (user.getLastname().length() < 2){
       //     model.addAttribute("errorLastName","Last name required and must be at least 2 characters long");
       //     return "register";
      //  }



       // if (!user.getPassword().equals(user.getConfirmPassword())){
       //     model.addAttribute("errorPassword","password dont match");
       //     return "register";
       // }


       // if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,}$")){
        //    model.addAttribute("errorPassword", "password must be at least 8 characters long,must be one lower or upper character and one special character");
       //     return "register";
        //}
       // if (!(userService.isEmailUnique(theUser.getEmail()))){

        //    model.addAttribute("errorEmail","email must be unique");
        //    return "register";
       // }

        Role role= new Role();
        role.setId(1);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(role);

        userRepo.save(user);
        return "register_success";
    }


 @PostMapping("/home/follow")
  public String followUser(@RequestParam(name="email") String email, Model  model) {

        //find user by email
        User user_profile = userRepo.findByEmail(email);


        model.addAttribute("following",followService.followUser(user_profile));
        model.addAttribute("profile",user_profile);

        //take current user
     return "user_profile";



    }

    @GetMapping(value="/saveData")
    public RedirectView saveFacebook(){

         facebookService.saveFacebookData();

         return new RedirectView("/home/me");


    }

    @GetMapping(value="/saveTwitter")
    public RedirectView saveTwitter(){

        twitterService.saveTwitterData();

        return new RedirectView("/home/me");


    }




}

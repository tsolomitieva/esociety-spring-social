package com.java.Esociety.controllers;

import com.java.Esociety.entities.*;
import com.java.Esociety.repositories.SocialRepository;
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
import org.springframework.social.instagram.api.Instagram;
import org.springframework.social.instagram.api.PagedMediaList;
import org.springframework.social.instagram.api.impl.InstagramTemplate;
import org.springframework.social.instagram.connect.InstagramOAuth2Template;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    CommentService commentService;

    @Autowired
    SocialRepository socialRepository;

    @Autowired
    userService userService;

    @Autowired
    DataSource dataSource;

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    TextEncryptor textEncryptor;

    @GetMapping("/home")
    public String home(Model model)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());
        if(user==null) System.out.println("User is null");
        model.addAttribute("User",user);
        model.addAttribute("comment",new Comment());
        model.addAttribute("post",new Post());


       List<Post>  posts= user.getPosts();;

        if(user.getSocial().getFacebookName()!=null) {
          // posts.addAll(facebookService.getFacebookFeed());
        }
        if (user.getSocial().getTwitterName()!=null){

           // posts.addAll(twitterService.getTwitterFeed());
           // posts.addAll(twitterService.getUserTweets());
        }
        Collections.shuffle(posts);
        model.addAttribute("Feed",posts);

      // Twitter twitter = new TwitterTemplate("FFv3iqk0WyOtmAxMnQ4GAJyur", "kfLBxD2mjXJbmyTYQogxGv4GIr46vqHouk4itC1cEgB6qFVTOy", "1568177439129444354-CIf1tYav13Hph0divwRCZf9LYbNJwd", "wxE6as3ufzmPTXWwqS4q4T9eHXbjshS45Kmm7fsIBAJ08");
       // twitter.userOperations().getUserProfile().getName();
      //  Instagram instagram = new InstagramTemplate("807956450244413","IGQVJYNUJ6NWFqUlktRG44cFZAqWmJ0XzNBam1HdlZAtS0JGUnhwUjNHMldFOXZANM3M4emdHVHE2U1hBT3A5UjI4R1NsYy1KVnNmU0kyamRtdHJINEstWUlWRFpOZAzJKWVJWaUYxcERpN0p0bGdOSHNwYgZDZD");
       //instagramService.getInstagramFeed();

       // InstagramTemplate instagramTemplate = new InstagramTemplate("1081266129443404","IGQVJWOHVIbWJYMEtfdmk5M2t2WWpNY2V3MnNrTHJOYkUtSG1iTVl1a0VyalhjcFVqNV9kajlzaUlrc0FjaVpoMFZAMc1NFTEJ4US1MUG9XZAW5MREVYd2dxcmI2WTFiZAndCb0lCM25Eczh6d3dfYmZAfSQZDZD");
      //  PagedMediaList media =instagramTemplate.userOperations().getFeed() ;

        return "index";

   }

    @GetMapping("/instagramConnected")
    public String instagramConnected()
    {

        instagramService.saveInstagramData();
        return "instagramConnected";

    }

    @GetMapping("/twitterConnected")
    public String twitterConnected()
    {

        twitterService.saveTwitterData();
        return "twitterConnected";

    }

    @PostMapping (value="/home/profile")
    public String profile(@RequestParam(name="email") String email, Model model)
    {Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepo.findByEmail(auth.getName());
        model.addAttribute("current_user",current_user);
        User profile_user= userRepo.findByEmail(email);
        model.addAttribute("User",profile_user);
        model.addAttribute("following",followService.CheckIfFollowing(profile_user));

        List<Post> posts = profile_user.getPosts();

        //if user is on facebook
        if(profile_user.getSocial().getFacebookName()!=null) {

           // posts.addAll(facebookService.getUsersPosts());

        }
        if (profile_user.getSocial().getTwitterName()!=null){

            // posts.addAll(twitterService.getTwitterFeed());
            // posts.addAll(twitterService.getUserTweets());
        }

        model.addAttribute("post",new Post());
        model.addAttribute("posts",posts);


        return "user_profile";
    }



    @GetMapping("/home/posts")
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
        user.setPhoto("/empty_profile.png");


        userRepo.save(user);

        Social social=new Social();
        social.setUser(user);
        socialRepository.save(social);
        return "register_success";
    }


 @PostMapping("/home/follow")
  public String followUser(@RequestParam(name="email") String email, Model  model) {


     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     User current_user = userRepo.findByEmail(auth.getName());
     model.addAttribute("current_user",current_user);
     User profile_user= userRepo.findByEmail(email);
     model.addAttribute("User",profile_user);
     model.addAttribute("following",followService.followUser(profile_user));

     List<Post> posts = profile_user.getPosts();

     //if user is on facebook
     if(profile_user.getSocial().getFacebookName()!=null) {

         posts.addAll(facebookService.getUsersPosts());

     }

     model.addAttribute("post",new Post());
     model.addAttribute("posts",posts);


        //take current user
     return "user_profile";



    }

    @GetMapping(value="/saveFacebook")
    public RedirectView saveFacebook(){

         facebookService.saveFacebookData();

         return new RedirectView("/profile");


    }

    @GetMapping(value="/saveTwitter")
    public RedirectView saveTwitter(){

        twitterService.saveTwitterData();

        return new RedirectView("/profile");


    }


    @PostMapping("/home/comment")
    public RedirectView addComment(@ModelAttribute Comment comment,  BindingResult result) {


     commentService.addComment(comment);

     return new RedirectView("/home");
     }




}

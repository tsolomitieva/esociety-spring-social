package com.java.Esociety.controllers;


import com.java.Esociety.entities.Comment;
import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.PostRepository;
import com.java.Esociety.repositories.userRepository;
import com.java.Esociety.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;

    @PostMapping("/post/edit")
    public String edit(@RequestParam(name="post_code") int post_code, Model model)
    {
        Post post = postRepository.findByPostId(post_code);
       model.addAttribute("post", post);
       return "edit_post";


    }

    @PostMapping("/post/update")
    public RedirectView update(@RequestParam(name="post_id") int post_id, @RequestParam(name="description") String description)
    {

        Post post=postRepository.findByPostId(post_id);
        post.setDescription(description);
        postRepository.save(post);

        return new RedirectView("/profile");


    }

    @PostMapping("/post/delete")
    public RedirectView delete(@RequestParam(name="post_id") int post_id)
    {
        Post post = postRepository.findByPostId(post_id);
        postRepository.delete(post);

        return new RedirectView("/home");

    }


    @PostMapping("/post/like")
    public RedirectView likePost(@RequestParam(name="post_id") int post_id, Model model)
    {
        Post post = postRepository.findByPostId(post_id);
        postService.likePost(post);


        return new RedirectView("/home");



    }

    @PostMapping("profile/post/like")
    public RedirectView profileLikePost(@RequestParam(name="post_id") int post_id, Model model)
    {
        Post post = postRepository.findByPostId(post_id);
        postService.likePost(post);


        return new RedirectView("/home/me");



    }



}

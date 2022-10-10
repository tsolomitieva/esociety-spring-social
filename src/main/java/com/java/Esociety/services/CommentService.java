package com.java.Esociety.services;

import com.java.Esociety.entities.Comment;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.CommentRepository;
import com.java.Esociety.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;


@Service
public class CommentService {

    @Autowired
    com.java.Esociety.repositories.userRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    public void addComment(Comment comment) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());

        comment.setUser(user);



        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        comment.setDate(timestamp);

        commentRepository.save(comment);


    }
}

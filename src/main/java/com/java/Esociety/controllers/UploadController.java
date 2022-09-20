package com.java.Esociety.controllers;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.PostRepository;
import com.java.Esociety.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;


@Controller
public class UploadController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    userRepository userRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static";



    @PostMapping("/upload") public RedirectView uploadImage(Model model, @RequestParam("image") MultipartFile file, @Valid Post post, BindingResult result) throws IOException {
        //upload photo on static file
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());

        //save post in database
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(auth.getName());

        post.setUser(user);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setDate(timestamp);
        post.setImage(fileNames.toString());
        postRepository.save(post);
        return new RedirectView("home");
    }


}

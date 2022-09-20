package com.java.Esociety.repositories;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface PostRepository extends JpaRepository<Post,Integer> {

    Set<Post> findAllByUserIn(Set<User> following);



}

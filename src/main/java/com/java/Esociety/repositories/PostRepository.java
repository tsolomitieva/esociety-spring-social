package com.java.Esociety.repositories;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    Set<Post> findAllByUserIn(Set<User> following);



}

package com.java.Esociety.repositories;

import com.java.Esociety.entities.Post;
import com.java.Esociety.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userRepository  extends JpaRepository<User,Integer> {

    User findByEmail(String email);

   @Query( value = "SELECT p FROM Posts p INNER JOIN Likes l ON p.post_id=l.like_id WHERE l.user_id= :user_id ",nativeQuery = true)
   List<Post> findLikedPosts(int user_id);



}

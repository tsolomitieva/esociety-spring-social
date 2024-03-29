package com.java.Esociety.repositories;

import com.java.Esociety.entities.Social;
import com.java.Esociety.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends JpaRepository<Social,Integer> {

   Social findById(int id);

   Social findByFacebookName(String name);
}

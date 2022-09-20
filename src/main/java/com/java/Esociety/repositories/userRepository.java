package com.java.Esociety.repositories;

import com.java.Esociety.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository  extends JpaRepository<User,Integer> {

    User findByEmail(String email);



}

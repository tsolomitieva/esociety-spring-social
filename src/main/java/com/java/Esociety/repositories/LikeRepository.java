package com.java.Esociety.repositories;


import com.java.Esociety.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LikeRepository extends JpaRepository<Like,Integer> {

}

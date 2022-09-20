package com.java.Esociety.repositories;

import com.java.Esociety.entities.Follow;
import com.java.Esociety.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {

     @Transactional

    void deleteByFollowerAndFollowing(int follower, int following);


}

package com.java.Esociety.repositories;

import com.java.Esociety.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepository extends JpaRepository<Role,Integer> {
}

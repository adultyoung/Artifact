package com.artifact.dao;

import com.artifact.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsDao extends JpaRepository<User, String> {

    User findByUsername(String username);

    User findByEmail(String email);


}
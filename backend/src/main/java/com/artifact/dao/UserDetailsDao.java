package com.artifact.dao;

import com.artifact.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsDao extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);


}
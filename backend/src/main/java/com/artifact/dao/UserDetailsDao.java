package com.artifact.dao;

import com.artifact.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsDao extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(String s);

    Optional<User> findByUsername(String username);

    User findByActivationCode(String code);

    User findByEmail(String email);

}
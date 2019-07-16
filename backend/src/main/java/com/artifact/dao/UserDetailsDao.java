package com.artifact.dao;

import com.artifact.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsDao extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(Long s);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

}
package com.artifact.dao;

import com.artifact.domain.Post;
import com.artifact.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = { "comments" })
    Page<Post> findByAuthorIn(List<User> users, Pageable pageable);

}

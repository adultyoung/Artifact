package com.artifact.dao;

import com.artifact.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "posts", collectionResourceRel = "posts", itemResourceRel = "post")
public interface PostDao extends JpaRepository<Post, Long> {
}

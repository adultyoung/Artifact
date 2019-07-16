package com.artifact.controller;

import com.artifact.domain.Post;
import com.artifact.domain.User;
import com.artifact.domain.Views;
import com.artifact.dto.PostPageDto;
import com.artifact.service.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("post")
public class PostController {
    public static final int POSTS_PER_PAGE = 3;

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @JsonView(Views.FullPost.class)
    public PostPageDto list(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = POSTS_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return postService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Post getOne(@PathVariable("id") Post post) {
        return post;
    }

    @PostMapping
    public Post create(
            @RequestBody Post post,
            @AuthenticationPrincipal User user
    ) throws IOException {
        return postService.create(post, user);
    }

    @PutMapping("{id}")
    public Post update(
            @PathVariable("id") Post postFromDb,
            @RequestBody Post post
    ) throws IOException {
        return postService.update(postFromDb, post);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Post post) {
        postService.delete(post);
    }
}

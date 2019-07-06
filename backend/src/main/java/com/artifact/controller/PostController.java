package com.artifact.controller;

import com.artifact.dao.PostDao;
import com.artifact.domain.Post;
import com.artifact.jwt.PostForm;
import com.artifact.utils.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private PostDao postDao;

    public PostController (PostDao postDao) {
        this.postDao = postDao;
    }

    @GetMapping("")
    public ResponseEntity all() {
        return ok(this.postDao.findAll());
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody PostForm form, HttpServletRequest request) {
        Post saved = this.postDao.save(Post.builder().message(form.getMessage()).build());
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/posts/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        return ok(this.postDao.findById(id).orElseThrow(() -> new PostNotFoundException()));
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody PostForm form) {
        Post existed = this.postDao.findById(id).orElseThrow(() -> new PostNotFoundException());
        existed.setMessage(form.getMessage());

        this.postDao.save(existed);
        return noContent().build();
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Post existed = this.postDao.findById(id).orElseThrow(() -> new PostNotFoundException());
        this.postDao.delete(existed);
        return noContent().build();
    }
}

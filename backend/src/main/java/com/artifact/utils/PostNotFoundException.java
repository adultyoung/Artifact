package com.artifact.utils;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
    }

    public PostNotFoundException(Long postId ) {
        super("Post: " + postId +" not found.");
    }
}


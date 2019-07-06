package com.artifact;

import com.artifact.dao.PostDao;
import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.artifact.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    PostDao postDao;

    @Autowired
    UserDetailsDao userDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        log.debug("initializing posts   data...");
        Arrays.asList("moto", "car").forEach(v -> this.postDao.saveAndFlush(Post.builder().message(v).build()));

        log.debug("printing all posts...");
        this.postDao.findAll().forEach(v -> log.debug(" Posts :" + v.toString()));

//this.userDao.save(User.builder()
//                .username("user")
//                .password(this.encoder.encode("password"))
//                .roles(Arrays.asList( "ROLE_USER"))
//                .build()
//        );
//
//        this.userDao.save(User.builder()
//                .username("admin")
//                .password(this.encoder.encode("password"))
//                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
//                .build()
//        );
//
//        log.debug("printing all users...");
//        this.userDao.findAll().forEach(v -> log.debug(" User :" + v.toString()));

    }
}

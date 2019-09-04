package com.artifact.controller;

import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import com.artifact.domain.Views;
import com.artifact.dto.PostPageDto;
import com.artifact.service.CustomUserDetailsService;
import com.artifact.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private final CustomUserDetailsService service;
    private final PostService postService;
    private final UserDetailsDao userDetailsDao;
    private final ObjectWriter postWriter;
    private final ObjectWriter profileWriter;
    @Value("true")
    private String profile;

    @Autowired
    public MainController(PostService postService, UserDetailsDao userDetailsDao, CustomUserDetailsService service, ObjectMapper mapper) {
        this.postService = postService;
        this.userDetailsDao = userDetailsDao;
        this.service = service;

        ObjectMapper objectMapper = mapper
                .setConfig(mapper.getSerializationConfig());

        this.postWriter = objectMapper
                .writerWithView(Views.FullPost.class);
        this.profileWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal User user
    ) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            User userFromDb = userDetailsDao.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, PostController.POSTS_PER_PAGE, sort);
            PostPageDto postPageDto = postService.findForUser(pageRequest, user);

            String posts = postWriter.writeValueAsString(postPageDto.getPosts());

            model.addAttribute("posts", posts);
            data.put("currentPage", postPageDto.getCurrentPage());
            data.put("totalPages", postPageDto.getTotalPages());
        } else {
            model.addAttribute("posts", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }

    @GetMapping("activate/{code}")
    public String activate(@PathVariable String code) {
        service.activateUser(code);

        return "redirect:/";

    }
}

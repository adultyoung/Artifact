//package com.artifact;
//
//import com.artifact.controller.PostController;
//import com.artifact.dao.PostDao;
//import com.artifact.domain.Post;
//import com.artifact.jwt.PostForm;
//import com.artifact.service.PostService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//
//import java.util.HashMap;
//import java.util.Optional;
//
//import static org.mockito.BDDMockito.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = PostController.class, secure = false)
//@RunWith(SpringRunner.class)
//@WithUserDetails
//public class PostControllerTest {
//
//    @MockBean
//    PostDao postDao;
//
//    @MockBean
//    PostService service;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    @WithUserDetails
//    public void testGetById() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        get("/post/{id}", 125L)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("test"));
//
//        verify(this.postDao, times(1)).findById(any(Long.class));
//        verifyNoMoreInteractions(this.postDao);
//    }
//
//    @Test
//    public void testGetByIdNotFound() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        get("/post/{id}", 255L)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isNotFound());
//
//        verify(this.postDao, times(1)).findById(any(Long.class));
//        verifyNoMoreInteractions(this.postDao);
//    }
//
//    @Test
//    public void testSave() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        post("/post")
//                                .content(this.objectMapper.writeValueAsBytes(new HashMap<String, String>(){{put("text", "message");}}))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().is2xxSuccessful());
//
//        verify(this.postDao, times(1)).save(any(Post.class));
//        verifyNoMoreInteractions(this.postDao);
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        put("post/{id}", 125L)
//                                .content(this.objectMapper.writeValueAsBytes(new HashMap<String, String>(){{put("text", "message");}}))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isNoContent());
//
//        verify(this.postDao, times(1)).findById(any(Long.class));
//        verify(this.postDao, times(1)).save(any(Post.class));
//        verifyNoMoreInteractions(this.postDao);
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//
//        this.mockMvc
//                .perform(
//                        delete("/post/{id}", 125L)
//                )
//                .andExpect(status().isNoContent());
//
//        verify(this.postDao, times(1)).findById(any(Long.class));
//        verify(this.postDao, times(1)).delete(any(Post.class));
//        verifyNoMoreInteractions(this.postDao);
//    }
//
//}
//

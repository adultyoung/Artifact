package com.artifact;

import com.artifact.dao.PostDao;
import com.artifact.domain.Post;
import com.artifact.controller.PostController;
import com.artifact.jwt.PostForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class, secure = false)
@RunWith(SpringRunner.class)
public class PostControllerTest {

    @MockBean
    PostDao postDao;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        given(this.postDao.findById(1L))
                .willReturn(Optional.of(Post.builder().message("test").build()));

        given(this.postDao.findById(2L))
                .willReturn(Optional.empty());

        given(this.postDao.save(any(Post.class)))
                .willReturn(Post.builder().message("test").build());

        doNothing().when(this.postDao).delete(any(Post.class));
    }

    @Test
    public void testGetById() throws Exception {

        this.mockMvc
                .perform(
                        get("/v1/posts/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        verify(this.postDao, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(this.postDao);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {

        this.mockMvc
                .perform(
                        get("/v1/posts/{id}", 2L)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        verify(this.postDao, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(this.postDao);
    }

    @Test
    public void testSave() throws Exception {

        this.mockMvc
                .perform(
                        post("/v1/posts")
                                .content(this.objectMapper.writeValueAsBytes(PostForm.builder().message("test").build()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        verify(this.postDao, times(1)).save(any(Post.class));
        verifyNoMoreInteractions(this.postDao);
    }

    @Test
    public void testUpdate() throws Exception {

        this.mockMvc
                .perform(
                        put("/v1/posts/1")
                                .content(this.objectMapper.writeValueAsBytes(PostForm.builder().message("test").build()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(this.postDao, times(1)).findById(any(Long.class));
        verify(this.postDao, times(1)).save(any(Post.class));
        verifyNoMoreInteractions(this.postDao);
    }

    @Test
    public void testDelete() throws Exception {

        this.mockMvc
                .perform(
                        delete("/v1/posts/1")
                )
                .andExpect(status().isNoContent());

        verify(this.postDao, times(1)).findById(any(Long.class));
        verify(this.postDao, times(1)).delete(any(Post.class));
        verifyNoMoreInteractions(this.postDao);
    }

}

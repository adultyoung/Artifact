package com.artifact;

import com.artifact.jwt.PostForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApplicationTests {

    @Autowired
    WebApplicationContext applicationContext;
    @Autowired
    ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testSave() throws Exception {

        this.mockMvc
                .perform(
                        post("/post")
                                .content(this.objectMapper.writeValueAsBytes(new HashMap<String, String>(){{put("text", "message");}}))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());

    }
    @Test
    @WithUserDetails
    public void testUpdate() throws Exception {

        this.mockMvc
                .perform(
                        put("/post/{id}", 125L).with(csrf())
                                .content(this.objectMapper.writeValueAsBytes(new HashMap<String, String>(){{put("text", "message");}}))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails
    public void testSaveWithMock() throws Exception {

        this.mockMvc
                .perform(
                        post("/post").with(csrf())
                                .content(this.objectMapper.writeValueAsBytes(new HashMap<String, String>(){{put("text", "message");}}))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDelete() throws Exception {

        this.mockMvc
                .perform(
                        delete("/post/{id}", 125L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is2xxSuccessful());
    }
}

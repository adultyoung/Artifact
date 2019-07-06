package com.artifact;

import com.artifact.domain.Post;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PostTest {

    @Test
    public void testVehicle(){
        Post v = Post.builder().message("test").build();
        v.setId(1L);
        assertTrue("id is 1L", 1L == v.getId());
        assertTrue("name is test", "test".equals(v.getMessage()));

        Post v2 =  Post.builder().message("test2").build();
        v2.setId(2L);
        assertTrue("id is 2L", 2L == v2.getId());
        assertTrue("name is test2", "test2".equals(v2.getMessage()));
    }
}

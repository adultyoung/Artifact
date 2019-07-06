package com.artifact;

import com.artifact.domain.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostJpaTest {

    @Autowired
    private TestEntityManager tem;

    @Test
    public void mapping() {
        Post v = this.tem.persistFlushFind(Post.builder().message("test").build());
        assertThat(v.getMessage()).isEqualTo("test");
        assertThat(v.getId()).isNotNull();
        assertThat(v.getId()).isGreaterThan(0);
        //assertThat(v.getCreatedDate()).isNotNull();
    }
}

package com.artifact.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;
    private String tags;
    private String pic;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String creationDate;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")*/
    private User author;

    public Post(String message, String tags, User author) {
        this.author = author;
        this.tags = tags;
        this.message = message;

    }
}

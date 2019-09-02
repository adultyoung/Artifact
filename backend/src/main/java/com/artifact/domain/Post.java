package com.artifact.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id"})
@Data
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullPost.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullPost.class)
    private User author;

    @OneToMany(mappedBy = "post",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonView(Views.FullPost.class)
    private List<Comment> comments;

    @JsonView(Views.FullPost.class)
    private String link;
    @JsonView(Views.FullPost.class)
    private String linkTitle;
    @JsonView(Views.FullPost.class)
    private String linkDescription;
    @JsonView(Views.FullPost.class)
    private String linkCover;
}

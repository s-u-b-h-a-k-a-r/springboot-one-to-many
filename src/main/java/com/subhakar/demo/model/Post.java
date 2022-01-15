package com.subhakar.demo.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "Posts")
@Table(name = "tbl_posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "posts_generator",
            sequenceName = "posts_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "posts_generator"
    )
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "published", nullable = false)
    private boolean published;
}

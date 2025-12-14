package com.marbel.job.pojo;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    private String postId;      // Link comment to the blog
    private String parentId;    // For replies
    private String name;
    private String comment;
    private int likes = 0;

    private Instant createdAt = Instant.now();
    private boolean liked = false;
}


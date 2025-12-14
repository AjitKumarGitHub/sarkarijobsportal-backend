package com.marbel.job.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marbel.job.pojo.Comment;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(String postId);
    void deleteByParentId(String parentId);
    void deleteById(String id);
    List<Comment> findByParentId(String parentId);
}


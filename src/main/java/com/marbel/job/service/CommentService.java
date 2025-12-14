package com.marbel.job.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marbel.job.pojo.Comment;
import com.marbel.job.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
    private CommentRepository repo;

    // Fetch comments (root + replies)
    public List<Comment> getComments(String postId) {
        return repo.findByPostIdOrderByCreatedAtDesc(postId);
    }

    // Add comment or reply
    public Comment addComment(Comment comment) {
        
        return repo.save(comment);
    }

    // Delete comment (parent or reply)
    public void deleteComment(String id) {
        

        repo.deleteById(id);
        repo.deleteByParentId(id);

         
    }

    // Update text only
    public Comment updateComment(String id, Comment updated) {
        Comment existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        existing.setComment(updated.getComment());
        existing.setCreatedAt(Instant.now());
        return repo.save(existing);
    }

    /**
     * Like toggle — but correctly using userId
     * You must store:
     *
     * private Set<String> likedBy;  // in Comment model
     */
//    public Comment toggleLike(String commentId, String userId) {
//        Comment comment = repo.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//
//        if (comment.getLikedBy().contains(userId)) {
//            comment.getLikedBy().remove(userId); // Unlike
//        } else {
//            comment.getLikedBy().add(userId); // Like
//        }
//
//        comment.setLikes(comment.getLikedBy().size());
//        return repo.save(comment);
//    }
}


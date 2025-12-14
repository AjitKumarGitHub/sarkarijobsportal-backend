package com.marbel.job.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marbel.job.pojo.Comment;
import com.marbel.job.service.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
@CrossOrigin(origins="*")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping("/{postId}")
    public Map<String, Object> getComments(@PathVariable String postId) {
        Map<String, Object> map = new HashMap<>();
        map.put("comments", service.getComments(postId));
        return map;
    }

    @PostMapping("/{postId}")
    public Comment addComment(@PathVariable String postId, @RequestBody Comment c) {
        c.setPostId(postId);
        return service.addComment(c);
    }

//    @PutMapping("/like/{id}")
//    public Comment like(@PathVariable String id) {
//        return service.toggleLike(id);
//    }
    @DeleteMapping("/delete/{id}")
    public String deleteComment(@PathVariable String id) {
        service.deleteComment(id);
        return "Comment deleted successfully";
    }
    
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable String id, @RequestBody Comment c) {
        return service.updateComment(id, c);
    }
}


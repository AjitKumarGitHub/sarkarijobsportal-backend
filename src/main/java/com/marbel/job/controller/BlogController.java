package com.marbel.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.marbel.job.service.BlogService;
import com.marbel.job.pojo.Blog;

@RestController
@RequestMapping("/api/v1/blogs")
@CrossOrigin(origins = "*")
public class BlogController {

	 @Autowired
	    private BlogService blogService;

	    @PostMapping("/add")
	    public Blog addBlog(@RequestBody Blog blog) {
	        return blogService.saveBlog(blog);
	    }

	    @GetMapping("/all")
	    public List<Blog> getAllBlogs() {
	        return blogService.getAllBlogs();
	    }

	    @GetMapping("/{id}")
	    public Optional<Blog> getBlogById(@PathVariable String id) {
	        return blogService.getBlogById(id);
	    }

	    @DeleteMapping("/{id}")
	    public String deleteBlog(@PathVariable String id) {
	        blogService.deleteBlog(id);
	        return "Blog deleted successfully!";
	    }

	    @GetMapping("/title/{title}")
	    public Blog getBlogByTitle(@PathVariable String title) {
	        return blogService.getBlogByTitle(title);
	    }
	    
	    @PutMapping("/update/{id}")
	    public Blog updateBlog(@PathVariable String id, @RequestBody Blog blog) {
	        Blog updated = blogService.updateBlog(id, blog);
	        return updated;
	    }
}

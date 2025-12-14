package com.marbel.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.marbel.job.pojo.Blog;
import com.marbel.job.repositories.BlogRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;

	public Blog saveBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	public List<Blog> getAllBlogs() {
		return blogRepository.findAll();
	}

	public Optional<Blog> getBlogById(String id) {
		return blogRepository.findById(id);
	}

	public void deleteBlog(String id) {
		blogRepository.deleteById(id);
	}

	public Blog getBlogByTitle(String title) {
		return blogRepository.findByTitle(title);
	}

	public Blog updateBlog(String id, Blog updatedBlog) {
		return blogRepository.findById(id).map(existing -> {
			if (updatedBlog.getTitle() != null)
				existing.setTitle(updatedBlog.getTitle());
			if (updatedBlog.getContent() != null)
				existing.setContent(updatedBlog.getContent());
			if (updatedBlog.getImageUrl() != null)
				existing.setImageUrl(updatedBlog.getImageUrl());
			if (updatedBlog.getAuthor() != null)
				existing.setAuthor(updatedBlog.getAuthor());
			return blogRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
	}
}

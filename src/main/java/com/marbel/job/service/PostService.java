package com.marbel.job.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.marbel.job.pojo.Post;
import com.marbel.job.pojo.PostRequest;
import com.marbel.job.repositories.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Caching(evict = { @CacheEvict(value = "postDetailCache", allEntries = true),
			@CacheEvict(value = "postListCache", allEntries = true),
			@CacheEvict(value = "postLatestCache", allEntries = true),
			@CacheEvict(value = "postGroupCache", allEntries = true) })
	public Post create(Post post) {

		if (post.getGroupId() == null || post.getGroupId().isEmpty()) {
			post.setGroupId(java.util.UUID.randomUUID().toString());
		}

		if (post.getLanguage() == null) {
			post.setLanguage("en");
		}
		post.setSlug(toSlug(post.getTitle()) + "-" + post.getLanguage());
		return postRepository.save(post);
	}

	@Caching(evict = { @CacheEvict(value = "postDetailCache", allEntries = true),
			@CacheEvict(value = "postListCache", allEntries = true),
			@CacheEvict(value = "postLatestCache", allEntries = true),
			@CacheEvict(value = "postGroupCache", allEntries = true) })
	public Post createPostHindi(Post post, String groupId) {
		post.setGroupId(groupId);
		if (post.getLanguage() == null) {
			post.setLanguage("hi");
		}
		post.setSlug(toSlug(post.getTitle()) + "-" + post.getLanguage());
		return postRepository.save(post);
	}

	public String toSlug(String text) {
		return text.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("-$", "").replaceAll("^-", "");
	}

	@Caching(evict = { @CacheEvict(value = "postDetailCache", allEntries = true),
			@CacheEvict(value = "postListCache", allEntries = true),
			@CacheEvict(value = "postLatestCache", allEntries = true),
			@CacheEvict(value = "postGroupCache", allEntries = true) })
	public Post update(String id, PostRequest request) {
		Post post = postRepository.findById(id).orElseThrow();

		post.setTitle(request.getTitle());
		post.setSlug(toSlug(request.getTitle()));
		post.setContent(request.getContent());
		post.setExcerpt(request.getExcerpt());
		post.setImageUrl(request.getImageUrl());
		post.setCategory(request.getCategory());
		post.setUpdatedAt(Instant.now());

		return postRepository.save(post);
	}

	@Cacheable(value = "postListCache")
	public Page<Post> getAllPosts(int page, int size, String sortBy, String order, String lang) {

		if (page < 0)
			page = 0;
		if (size <= 0)
			size = 8;

		Query query = new Query();
		query.addCriteria(Criteria.where("language").is(lang));
		Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		query.with(Sort.by(direction, sortBy));

		// 📄 Pagination
		long total = mongoTemplate.count(query, Post.class);

		Pageable pageable = PageRequest.of(page, size);
		query.with(pageable);

		List<Post> posts = mongoTemplate.find(query, Post.class);

		return new PageImpl<>(posts, pageable, total);
	}

	public Optional<Post> getById(String id) {
		return postRepository.findById(id);
	}

	@Cacheable(value = "postDetailCache", key = "#id + '_' + #lang")
	public Optional<Post> getByIdAndLang(String id, String lang) {
		return postRepository.findByIdAndLanguage(id, lang);
	}

	public Optional<Post> getBySlug(String slug) {
		return postRepository.findBySlug(slug);
	}

	@Cacheable(value = "postGroupCache", key = "#id")
	public List<Post> getByGroupId(String id) {
		return postRepository.findByGroupId(id);
	}

	@Cacheable(value = "postLatestCache", key = "#language")
	public List<Post> getLatestPosts(String language) {
		if (language == null || language.isEmpty()) {
			language = "en"; // default English
		}
		return postRepository.findAllByLanguageOrderByCreatedAtDesc(language);
	}

	@Caching(evict = { @CacheEvict(value = "postDetailCache", allEntries = true),
			@CacheEvict(value = "postListCache", allEntries = true),
			@CacheEvict(value = "postLatestCache", allEntries = true),
			@CacheEvict(value = "postGroupCache", allEntries = true) })
	public String delete(String id) {
		postRepository.deleteById(id);
		return "Post is successfully deleted";
	}
}

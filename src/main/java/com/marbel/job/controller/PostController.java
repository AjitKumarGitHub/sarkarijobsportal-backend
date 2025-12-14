package com.marbel.job.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marbel.job.pojo.Post;
import com.marbel.job.pojo.PostRequest;
import com.marbel.job.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "*")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/createpost")
	public Post create(@RequestBody Post post) {
		return postService.create(post);
	}

	@PostMapping("/createposthindi")
	public Post createHindiPost(@RequestBody Post post, @RequestParam() String groupId) {
		return postService.createPostHindi(post, groupId);
	}

    @PutMapping("/{id}")
    public Post update(@PathVariable String id, @RequestBody PostRequest request) {
        return postService.update(id, request);
    }

	@GetMapping("/allpost")
	public Map<String, Object> getAllPost(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc") String order, @RequestParam(defaultValue = "en") String lang) {

		Page<Post> jobPage = postService.getAllPosts(page, size, sortBy, order, lang);

		Map<String, Object> response = new HashMap<>();
		response.put("posts", jobPage.getContent());
		response.put("currentPage", jobPage.getNumber());
		response.put("totalPages", jobPage.getTotalPages());
		response.put("totalPosts", jobPage.getTotalElements());
		response.put("pageSize", jobPage.getSize());
		response.put("isLastPage", jobPage.isLast());

		return response;
	}

    @GetMapping("/singlepost/{id}")
    public Optional<Post> getById(@PathVariable String id) {
    	return postService.getById(id);
    }

	@GetMapping("/{id}")
	public Optional<Post> getById(@PathVariable String id, @RequestParam(defaultValue = "en") String lang) {
		return postService.getByIdAndLang(id, lang);
	}
	
	@GetMapping("/group/{groupId}")
	public List<Post> getByGroupId(@PathVariable String groupId) {
	    return postService.getByGroupId(groupId);
	}


	@GetMapping("/slug/{slug}")
	public Optional<Post> getBySlug(@PathVariable String slug) {
		return postService.getBySlug(slug);
	}

	@GetMapping("/latest")
	public List<Post> getLatestPosts(@RequestParam(required = false) String lang) {
		return postService.getLatestPosts(lang);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		postService.delete(id);
		return "Post deleted";
	}
}

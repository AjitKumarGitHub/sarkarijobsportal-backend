package com.marbel.job.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.marbel.job.pojo.Post;
import java.util.List;

public interface PostRepository extends MongoRepository<Post, String>{
	
	Page<Post> findAll(Pageable pageable);
	Optional<Post> findBySlug(String slug);
	
	Optional<Post> findBySlugAndLanguage(String slug, String language);

    List<Post> findByLanguageOrderByCreatedAtDesc(String language);
   
    List<Post> findByGroupId(String groupId);
   List<Post> findAllByLanguageOrderByCreatedAtDesc(String language);
   Optional<Post> findByIdAndLanguage(String id, String language);


}

package com.marbel.job.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.marbel.job.pojo.Blog;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
    Blog findByTitle(String title);
}

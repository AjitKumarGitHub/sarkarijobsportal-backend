package com.marbel.job.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.marbel.job.pojo.Results;

@Repository
public interface ResultRepository extends MongoRepository<Results, String> {
    Results findByTitle(String examName);
}

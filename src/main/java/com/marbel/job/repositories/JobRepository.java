package com.marbel.job.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.marbel.job.pojo.JobDetails;
public interface JobRepository extends MongoRepository<JobDetails, String>{
	JobDetails findByTitle(String title);
//	 // Dynamic query using method name for search and filters
//    Page<JobDetails> findByJobTitleContainingIgnoreCaseOrOrganizationNameContainingIgnoreCaseOrShortFormContainingIgnoreCase(
//            String title, String organizationName, String shortForm, Pageable pageable);
//
//    // Optional: find by category and state
    Page<JobDetails> findByCategoryAndState(String category, String state, Pageable pageable);

    // Fallback: find all with pagination
    Page<JobDetails> findAll(Pageable pageable);
}

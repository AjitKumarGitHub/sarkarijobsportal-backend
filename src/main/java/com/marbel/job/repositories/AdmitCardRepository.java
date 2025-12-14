package com.marbel.job.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.marbel.job.pojo.AdmitCards;
import com.marbel.job.pojo.JobDetails;

@Repository
public interface AdmitCardRepository extends MongoRepository<AdmitCards, String> {
    // Example custom query method
    AdmitCards findByTitle(String examName);
    Page<AdmitCards> findByCategoryAndStatus(String category, String status, Pageable pageable);

    // Fallback: find all with pagination
    Page<AdmitCards> findAll(Pageable pageable);
}

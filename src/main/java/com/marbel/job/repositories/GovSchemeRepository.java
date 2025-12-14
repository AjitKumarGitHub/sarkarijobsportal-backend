package com.marbel.job.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.marbel.job.pojo.GovScheme;
@Repository
public interface GovSchemeRepository extends MongoRepository<GovScheme, String> {
    GovScheme findBySchemeName(String schemeName);
}

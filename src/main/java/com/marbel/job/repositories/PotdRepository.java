package com.marbel.job.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.marbel.job.pojo.Potd;
@Repository
public interface PotdRepository extends MongoRepository<Potd, String> {
    Potd findByDate(String date);
}
 

package com.marbel.job.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marbel.job.pojo.PushSubscription;

public interface SubscriptionRepository extends MongoRepository<PushSubscription, String> {}


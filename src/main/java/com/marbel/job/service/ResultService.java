package com.marbel.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Optional;
import com.marbel.job.repositories.ResultRepository;
import com.marbel.job.pojo.Results;
import com.marbel.job.pojo.UpdatedResult;

@Service
public class ResultService {

	@Autowired
	private ResultRepository resultRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@CacheEvict(value = "resultListCache", allEntries = true)
	public Results saveResult(Results results) {
		return resultRepository.save(results);
	}

	@Cacheable(value = "resultListCache")
	public Page<Results> getResults(int page, int size, String sortBy, String order, String search,
			String category, String status) {

		Query query = new Query();
		List<Criteria> criteriaList = new ArrayList<>();

// SEARCH
		if (search != null && !search.isEmpty()) {
			
			String safe = escapeRegex(search);
			criteriaList.add(new Criteria().orOperator(Criteria.where("title").regex(safe, "i"),
					Criteria.where("organization").regex(safe, "i"), Criteria.where("category").regex(safe, "i")));
		}

// CATEGORY FILTER
		if (category != null && !category.equalsIgnoreCase("All")) {
			criteriaList.add(Criteria.where("category").is(category));
		}

// STATUS FILTER
		if (status != null && !status.equalsIgnoreCase("All")) {
			criteriaList.add(Criteria.where("status").is(status));
		}

		if (!criteriaList.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
		}

// SORT
		Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		query.with(Sort.by(direction, sortBy));

// PAGINATION
		long total = mongoTemplate.count(query,  Results.class);
		Pageable pageable = PageRequest.of(page, size);
		query.with(pageable);

		List<Results> admitcards = mongoTemplate.find(query, Results.class);

		return new PageImpl<>(admitcards, pageable, total);
	}
	
	public static String escapeRegex(String input) {
	    return input.replaceAll("([\\\\+*?\\[\\]^$(){}=!<>|:\\-])", "\\\\$1");
	}

	public List<Results> getAllResults() {
		return resultRepository.findAll();
	}

	public Optional<Results> getResultById(String id) {
		return resultRepository.findById(id);
	}

	@CacheEvict(value = "resultListCache", allEntries = true)
	public void deleteResult(String id) {
		resultRepository.deleteById(id);
	}

	public Results getResultByExamName(String examName) {
		return resultRepository.findByTitle(examName);
	}

	@CacheEvict(value = "resultListCache", allEntries = true)
	public Results updateResult(String id, UpdatedResult updatedResult) {
		return resultRepository.findById(id).map(existing -> {
			if (updatedResult.getDate() != null)
				existing.setDate(updatedResult.getDate());
			if (updatedResult.getResultDate() != null)
				existing.setResultDate(updatedResult.getResultDate());
			if (updatedResult.getResultLink() != null)
				existing.setResultLink(updatedResult.getResultLink());
			if (updatedResult.getCutOffMatks() != null)
				existing.setCutOffMatks(updatedResult.getCutOffMatks());
			if (updatedResult.getStatus() != null)
				existing.setStatus(updatedResult.getStatus());
			if (updatedResult.getCategory() != null)
				existing.setCategory(updatedResult.getCategory());
			return resultRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Result not found with id: " + id));
	}
}
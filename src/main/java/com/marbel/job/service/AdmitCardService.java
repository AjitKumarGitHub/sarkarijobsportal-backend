package com.marbel.job.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.marbel.job.pojo.AdmitCards;
import com.marbel.job.pojo.UpdatedAdmitCard;
import com.marbel.job.repositories.AdmitCardRepository;

@Service
public class AdmitCardService {

	@Autowired
	private AdmitCardRepository admitCardRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@CacheEvict(value = "admitCardListCache", allEntries = true)
	public AdmitCards saveAdmitCard(AdmitCards admitCards) {
		return admitCardRepository.save(admitCards);
	}
	@Cacheable(value = "admitCardListCache")
	public Page<AdmitCards> getAdmitCards(int page, int size, String sortBy, String order, String search,
			String category, String status) {

		Query query = new Query();

		List<Criteria> criteriaList = new ArrayList<>();

		// 🔍 SEARCH (title, organization, shortForm)
		if (search != null && !search.isEmpty()) {
			 String safe = escapeRegex(search);
			Criteria searchCriteria = new Criteria().orOperator(Criteria.where("title").regex(safe, "i"),
					Criteria.where("organizationName").regex(safe, "i"),
					Criteria.where("category").regex(safe, "i"));
			criteriaList.add(searchCriteria);
		}

		// 🏷 CATEGORY FILTER
		if (category != null && !category.equalsIgnoreCase("All")) {
			criteriaList.add(Criteria.where("category").is(category));
		}

		// 🗺 STATE FILTER
		if (status != null && !status.equalsIgnoreCase("All")) {
			criteriaList.add(Criteria.where("status").is(status));
		}

		// Combine all criteria
		if (!criteriaList.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
		}

		// 🔽 Sorting
		Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		query.with(Sort.by(direction, sortBy));

		// 📄 Pagination
		long total = mongoTemplate.count(query, AdmitCards.class);

		Pageable pageable = PageRequest.of(page, size);
		query.with(pageable);

		List<AdmitCards> admitcards = mongoTemplate.find(query, AdmitCards.class);

		return new PageImpl<>(admitcards, pageable, total);
	}

	public static String escapeRegex(String input) {
	    return input.replaceAll("([\\\\+*?\\[\\]^$(){}=!<>|:\\-])", "\\\\$1");
	}

	public List<AdmitCards> getAllAdmitCards() {
		return admitCardRepository.findAll();
	}

	public Optional<AdmitCards> getAdmitCardById(String id) {
		return admitCardRepository.findById(id);
	}

	@CacheEvict(value = "admitCardListCache", allEntries = true)
	public void deleteAdmitCard(String id) {
		admitCardRepository.deleteById(id);
	}
    
	
	public AdmitCards getAdmitCardByExamName(String examName) {
		return admitCardRepository.findByTitle(examName);
	}

	@CacheEvict(value = "admitCardListCache", allEntries = true)
	public AdmitCards updateAdmitCard(String id, UpdatedAdmitCard updatedAdmitCard) {
		return admitCardRepository.findById(id).map(existing -> {
			if (updatedAdmitCard.getDate() != null)
				existing.setDate(updatedAdmitCard.getDate());
			if (updatedAdmitCard.getExamDate() != null)
				existing.setExamDate(updatedAdmitCard.getExamDate());
			if (updatedAdmitCard.getDownloadLink() != null)
				existing.setDownloadLink(updatedAdmitCard.getDownloadLink());
			if (updatedAdmitCard.getInstructions() != null)
				existing.setInstructions(updatedAdmitCard.getInstructions());
			if (updatedAdmitCard.getStatus() != null)
				existing.setStatus(updatedAdmitCard.getStatus());
			if (updatedAdmitCard.getCategory() != null) existing.setCategory(updatedAdmitCard.getCategory());
			return admitCardRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Admit card not found with id: " + id));
	}
}

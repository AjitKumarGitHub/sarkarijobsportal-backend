package com.marbel.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Optional;
import com.marbel.job.pojo.JobDetails;
import com.marbel.job.pojo.UpdatedJob;
import com.marbel.job.repositories.JobRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.domain.*;

@Service
public class JobService {

	   @Autowired
	    private JobRepository jobRepository;
	   
	   @Autowired 
	   private MongoTemplate mongoTemplate;

	    // Create or Update Job
	   @CacheEvict(value = "jobList", allEntries = true)
	    public JobDetails saveJob(JobDetails jobDetails) {
		   jobDetails.setSlug(jobDetails.getTitle());
	        return jobRepository.save(jobDetails);
	    }
        
	     
	    public String autoGenerateSlugs() {
	        List<JobDetails> jobs = jobRepository.findAll();

	        for (JobDetails job : jobs) {
	            job.setSlug(toSlug(job.getTitle()));
	            jobRepository.save(job);
	        }

	        return "Slugs added to all jobs!";
	    }
	    
	    public String toSlug(String text) {
	        if (text == null || text.isEmpty()) return "";
	        return Normalizer.normalize(text, Normalizer.Form.NFD)
	                .replaceAll("\\p{M}", "")
	                .toLowerCase()
	                .replaceAll("[^a-z0-9]+", "-")
	                .replaceAll("^-+|-+$", "");
	    }

	    // Get all Jobs
	    public List<JobDetails> getAllJobs() {
	        return jobRepository.findAll();
	    }
	    
	    @Cacheable(value = "jobsList")
	    public Page<JobDetails> getJobs(
	            int page,
	            int size,
	            String sortBy,
	            String order,
	            String search,
	            String category,
	            String state
	    ) {

	        Query query = new Query();

	        List<Criteria> criteriaList = new ArrayList<>();

	        // 🔍 SEARCH (title, organization, shortForm)
	        if (search != null && !search.isEmpty()) {
	        	 String safe = escapeRegex(search);
	            Criteria searchCriteria = new Criteria().orOperator(
	                    Criteria.where("title").regex(safe, "i"),
	                    Criteria.where("organizationName").regex(safe, "i"),
	                    Criteria.where("shortForm").regex(safe, "i")
	            );
	            criteriaList.add(searchCriteria);
	        }

	        // 🏷 CATEGORY FILTER
	        if (category != null && !category.equalsIgnoreCase("All Categories")) {
	            criteriaList.add(Criteria.where("category").is(category));
	        }

	        // 🗺 STATE FILTER
	        if (state != null && !state.equalsIgnoreCase("All States")) {
	            criteriaList.add(Criteria.where("state").is(state));
	        }

	        // Combine all criteria
	        if (!criteriaList.isEmpty()) {
	            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
	        }

	        // 🔽 Sorting
	        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	        query.with(Sort.by(direction, sortBy));

	        // 📄 Pagination
	        long total = mongoTemplate.count(query, JobDetails.class);

	        Pageable pageable = PageRequest.of(page, size);
	        query.with(pageable);

	        List<JobDetails> jobs = mongoTemplate.find(query, JobDetails.class);

	        return new PageImpl<>(jobs, pageable, total);
	    }
	    
	    public static String escapeRegex(String input) {
		    return input.replaceAll("([\\\\+*?\\[\\]^$(){}=!<>|:\\-])", "\\\\$1");
		}

	    // Get Job by ID
	    @Cacheable(value = "jobDetail", key = "#id")
	    public Optional<JobDetails> getJobById(String id) {
	        return jobRepository.findById(id);
	    }

	    @Cacheable(value="latestJobListCache")
	    public List<JobDetails> getAllJobsSortedByDate() {
	        return jobRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
	    }
	    
	    // Delete Job
	    @CacheEvict(value = { "jobDetail", "jobsList" }, allEntries = true)
	    public void deleteJob(String id) {
	        jobRepository.deleteById(id);
	    }

	    @CacheEvict(value = { "jobDetail", "jobsList" }, allEntries = true)
	    public JobDetails updateJob(String id, UpdatedJob updatedJob) {
	        return jobRepository.findById(id)
	                .map(existingJob -> {
	                     if(updatedJob.getVacancy() !=null) existingJob.setTotalVacancy(updatedJob.getVacancy());
	                     if(updatedJob.getMoreDetailsLink() != null) existingJob.setMoreDetailsLink(updatedJob.getMoreDetailsLink());
	                     if(updatedJob.getLinks() != null ) existingJob.setLinks(updatedJob.getLinks());
	                     if(updatedJob.getImportantDates() != null) existingJob.setImportantDates(updatedJob.getImportantDates());
	                     if(updatedJob.getPostDate() != null) existingJob.setPostDate(updatedJob.getPostDate());
	                     if(updatedJob.getDate() != null) existingJob.setDate(updatedJob.getDate());
	                    return jobRepository.save(existingJob);
	                })
	                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
	    }

	    // Find Job by Title
	    
	    public JobDetails getJobByTitle(String title) {
	        return jobRepository.findByTitle(title);
	    }
}

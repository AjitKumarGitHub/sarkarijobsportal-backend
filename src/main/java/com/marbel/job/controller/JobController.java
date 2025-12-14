package com.marbel.job.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import com.marbel.job.pojo.JobDetails;
import com.marbel.job.pojo.UpdatedJob;
import com.marbel.job.service.JobService;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin(origins = "*")  // allow frontend requests
public class JobController {

	@Autowired
    private JobService jobService;

	 
    @PostMapping("/add")
    public JobDetails addJob(@RequestBody JobDetails job) {
    	
        return jobService.saveJob(job);
    }
    
    @GetMapping("/auto-slug")
    public String autoGenerateSlugs() {
        

        return jobService.autoGenerateSlugs();
    }

     
    @GetMapping("/alljobs")
    public Map<String, Object> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String state
    ) {

        Page<JobDetails> jobPage = jobService.getJobs(page, size, sortBy, order, search, category, state);

        Map<String, Object> response = new HashMap<>();
        response.put("jobs", jobPage.getContent());
        response.put("currentPage", jobPage.getNumber());
        response.put("totalPages", jobPage.getTotalPages());
        response.put("totalJobs", jobPage.getTotalElements());
        response.put("pageSize", jobPage.getSize());
        response.put("isLastPage", jobPage.isLast());

        return response;
    }

     
    @GetMapping("/all")
    public List<JobDetails> getAllJobs() {
        return jobService.getAllJobs();
    }

     
    @GetMapping("/{id}")
    public Optional<JobDetails> getJobById(@PathVariable String id) {
        return jobService.getJobById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
        return "Job deleted successfully!";
    }

    @GetMapping("/latest")
    public List<JobDetails> getLatestJobs() {
        return jobService.getAllJobsSortedByDate();
    }
    @GetMapping("/title/{title}")
    public JobDetails getJobByTitle(@PathVariable String title) {
        return jobService.getJobByTitle(title);
    }
    
     
    @PutMapping("/update/{id}")
    public JobDetails updateJob(@PathVariable String id, @RequestBody UpdatedJob updateJob) {
        JobDetails updatedJob = jobService.updateJob(id, updateJob);
        return updatedJob;
    }
}

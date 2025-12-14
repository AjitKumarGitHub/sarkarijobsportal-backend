package com.marbel.job.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marbel.job.pojo.Results;
import com.marbel.job.pojo.UpdatedResult;
import com.marbel.job.service.ResultService;

@RestController
@RequestMapping("/api/v1/results")
@CrossOrigin(origins = "*")
public class ResultController {

	@Autowired
	private ResultService resultService;

	@PostMapping("/add")
	public Results addResult(@RequestBody Results result) {
		return resultService.saveResult(result);
	}

	@GetMapping("/allresults")
	public Map<String, Object> getResults(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "date") String sortBy,
			@RequestParam(defaultValue = "desc") String order, @RequestParam(required = false) String search,
			@RequestParam(required = false) String category, @RequestParam(required = false) String status) {

		Page<Results> resultPage = resultService.getResults(page, size, sortBy, order, search, category, status);

		Map<String, Object> response = new HashMap<>();
		response.put("results", resultPage.getContent());
		response.put("currentPage", resultPage.getNumber());
		response.put("totalPages", resultPage.getTotalPages());
		response.put("totalResults", resultPage.getTotalElements());
		response.put("pageSize", resultPage.getSize());
		response.put("isLastPage", resultPage.isLast());

		return response;
	}

	@GetMapping("/all")
	public List<Results> getAllResults() {
		return resultService.getAllResults();
	}

	@GetMapping("/{id}")
	public Optional<Results> getResultById(@PathVariable String id) {
		return resultService.getResultById(id);
	}

	@DeleteMapping("/{id}")
	public String deleteResult(@PathVariable String id) {
		resultService.deleteResult(id);
		return "Result deleted successfully!";
	}

	@GetMapping("/exam/{examName}")
	public Results getByExamName(@PathVariable String examName) {
		return resultService.getResultByExamName(examName);
	}

	@PutMapping("/update/{id}")
	public Results updateResult(@PathVariable String id, @RequestBody UpdatedResult result) {
		Results updated = resultService.updateResult(id, result);
		return updated;
	}
}

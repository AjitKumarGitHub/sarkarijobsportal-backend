package com.marbel.job.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import com.marbel.job.pojo.AdmitCards;
import com.marbel.job.pojo.UpdatedAdmitCard;
import com.marbel.job.service.AdmitCardService;

@RestController
@RequestMapping("/api/v1/admitcards")
@CrossOrigin(origins = "*")
public class AdmitCardController {

	 @Autowired
	    private AdmitCardService admitCardService;
        
	     
	    @PostMapping("/add")
	    public AdmitCards addAdmitCard(@RequestBody AdmitCards admitCards) {
	        return admitCardService.saveAdmitCard(admitCards);
	    }
	     
	    @GetMapping("/alladmitcards")
	    public Map<String, Object> getAdmitCards(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "date") String sortBy,
	            @RequestParam(defaultValue = "desc") String order,
	            @RequestParam(required = false) String search,
	            @RequestParam(required = false) String category,
	            @RequestParam(required = false) String status
	    ) {

	        Page<AdmitCards> admitCardPage = admitCardService.getAdmitCards(page, size, sortBy, order, search, category, status);

	        Map<String, Object> response = new HashMap<>();
	        response.put("admitcards", admitCardPage.getContent());
	        response.put("currentPage", admitCardPage.getNumber());
	        response.put("totalPages", admitCardPage.getTotalPages());
	        response.put("totaladmitcards", admitCardPage.getTotalElements());
	        response.put("pageSize", admitCardPage.getSize());
	        response.put("isLastPage", admitCardPage.isLast());

	        return response;
	    }

	    @GetMapping("/all")
	    public List<AdmitCards> getAllAdmitCards() {
	        return admitCardService.getAllAdmitCards();
	    }

	    @GetMapping("/{id}")
	    public Optional<AdmitCards> getAdmitCardById(@PathVariable String id) {
	        return admitCardService.getAdmitCardById(id);
	    }

	    @DeleteMapping("/{id}")
	    public String deleteAdmitCard(@PathVariable String id) {
	        admitCardService.deleteAdmitCard(id);
	        return "Admit card deleted successfully!";
	    }

	    @GetMapping("/exam/{examName}")
	    public AdmitCards getByExamName(@PathVariable String examName) {
	        return admitCardService.getAdmitCardByExamName(examName);
	    }
	    
	     
	    @PutMapping("/update/{id}")
	    public AdmitCards updateAdmitCard(@PathVariable String id, @RequestBody UpdatedAdmitCard admitCard) {
	        AdmitCards updated = admitCardService.updateAdmitCard(id, admitCard);
	        return updated;
	    }
}

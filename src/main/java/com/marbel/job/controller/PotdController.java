package com.marbel.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.marbel.job.service.PotdService;
import com.marbel.job.pojo.Potd;

@RestController
@RequestMapping("/api/v1/potd")
@CrossOrigin(origins = "*")
public class PotdController {

	 @Autowired
	    private PotdService potdService;

	    @PostMapping("/add")
	    public Potd addPotd(@RequestBody Potd potd) {
	        return potdService.savePotd(potd);
	    }

	    @GetMapping("/all")
	    public List<Potd> getAllPotd() {
	        return potdService.getAllPotd();
	    }

	    @GetMapping("/{id}")
	    public Optional<Potd> getPotdById(@PathVariable String id) {
	        return potdService.getPotdById(id);
	    }

	    @DeleteMapping("/{id}")
	    public String deletePotd(@PathVariable String id) {
	        potdService.deletePotd(id);
	        return "POTD deleted successfully!";
	    }

	    @GetMapping("/date/{date}")
	    public Potd getPotdByDate(@PathVariable String date) {
	        return potdService.getPotdByDate(date);
	    }
	    
//	    @PutMapping("/update/{id}")
//	    public Potd updatePotd(@PathVariable String id, @RequestBody Potd potd) {
//	        Potd updated = potdService.updatePotd(id, potd);
//	        return updated;
//	    }
}

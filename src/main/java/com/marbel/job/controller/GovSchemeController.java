package com.marbel.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.marbel.job.service.GovSchemeService;
import com.marbel.job.pojo.GovScheme;

@RestController
@RequestMapping("/api/v1/schemes")
@CrossOrigin(origins = "*")
public class GovSchemeController {

	@Autowired
    private GovSchemeService govSchemeService;

    @PostMapping("/add")
    public GovScheme addScheme(@RequestBody GovScheme scheme) {
        return govSchemeService.saveGovScheme(scheme);
    }

    @GetMapping("/all")
    public List<GovScheme> getAllSchemes() {
        return govSchemeService.getAllSchemes();
    }

    @GetMapping("/{id}")
    public Optional<GovScheme> getSchemeById(@PathVariable String id) {
        return govSchemeService.getSchemeById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteScheme(@PathVariable String id) {
        govSchemeService.deleteScheme(id);
        return "Scheme deleted successfully!";
    }

    @GetMapping("/name/{name}")
    public GovScheme getSchemeByName(@PathVariable String name) {
        return govSchemeService.getSchemeByName(name);
    }
    
    @PutMapping("/update/{id}")
    public GovScheme updateScheme(@PathVariable String id, @RequestBody GovScheme scheme) {
        GovScheme updated = govSchemeService.updateScheme(id, scheme);
        return updated;
    }
}

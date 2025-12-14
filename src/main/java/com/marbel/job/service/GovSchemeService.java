package com.marbel.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.marbel.job.repositories.GovSchemeRepository;
import com.marbel.job.pojo.GovScheme;

@Service
public class GovSchemeService {

    @Autowired
    private GovSchemeRepository govSchemeRepository;

    public GovScheme saveGovScheme(GovScheme scheme) {
        return govSchemeRepository.save(scheme);
    }

    public List<GovScheme> getAllSchemes() {
        return govSchemeRepository.findAll();
    }

    public Optional<GovScheme> getSchemeById(String id) {
        return govSchemeRepository.findById(id);
    }

    public void deleteScheme(String id) {
        govSchemeRepository.deleteById(id);
    }

    public GovScheme getSchemeByName(String name) {
        return govSchemeRepository.findBySchemeName(name);
    }
    
    public GovScheme updateScheme(String id, GovScheme updatedScheme) {
        return govSchemeRepository.findById(id)
                .map(existing -> {
                    existing.setSchemeName(updatedScheme.getSchemeName());
                    existing.setBenefits(updatedScheme.getBenefits());
                    existing.setDescription(updatedScheme.getDescription());
                    existing.setApplyLink(updatedScheme.getApplyLink());
                    existing.setSeoKeywords(updatedScheme.getSeoKeywords());
                    return govSchemeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Scheme not found with id: " + id));
    }
}

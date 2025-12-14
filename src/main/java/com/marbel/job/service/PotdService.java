package com.marbel.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.marbel.job.repositories.PotdRepository;
import com.marbel.job.pojo.Potd;

@Service
public class PotdService {

    @Autowired
    private PotdRepository potdRepository;

    public Potd savePotd(Potd potd) {
        return potdRepository.save(potd);
    }

    public List<Potd> getAllPotd() {
        return potdRepository.findAll();
    }

    public Optional<Potd> getPotdById(String id) {
        return potdRepository.findById(id);
    }

    public void deletePotd(String id) {
        potdRepository.deleteById(id);
    }

    public Potd getPotdByDate(String date) {
        return potdRepository.findByDate(date);
    }
    
//    public Potd updatePotd(String id, Potd updatedPotd) {
//        return potdRepository.findById(id)
//                .map(existing -> {
//                    existing.setPotdList(updatedPotd.getPotdList());
//                    existing.setDate(updatedPotd.getDate());
//                    return potdRepository.save(existing);
//                })
//                .orElseThrow(() -> new RuntimeException("POTD not found with id: " + id));
//    }
}

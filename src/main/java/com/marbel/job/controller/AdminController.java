package com.marbel.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.marbel.job.pojo.Admin;
import com.marbel.job.repositories.AdminRepository;
import com.marbel.job.utills.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        Optional<Admin> dbAdmin = adminRepository.findByUsername(admin.getUsername());
        if (dbAdmin.isPresent()) {
            if (passwordEncoder.matches(admin.getPassword(), dbAdmin.get().getPassword())) {
                return jwtUtil.generateToken(admin.getUsername());
            } else {
                return "Invalid Password";
            }
        } else {
            return "Admin not found";
        }
    }

    // Optional: Add admin creation for testing
    @PostMapping("/signup")
    public Admin createAdmin(@RequestBody Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }
}

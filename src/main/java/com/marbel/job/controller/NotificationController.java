package com.marbel.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marbel.job.pojo.PushSubscription;
import com.marbel.job.repositories.SubscriptionRepository;
import com.marbel.job.service.NotificationService;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private SubscriptionRepository repo;

    @Autowired
    private NotificationService notificationService;
    @PostMapping("/subscribe")
    public String subscribe(@RequestBody PushSubscription sub) {
        repo.save(sub);
        return "Subscribed";
    }
    
    @GetMapping("/allsubscribers")
    public List<PushSubscription> getAllSubscribers(){
    	return repo.findAll();
    }
    
    @PostMapping("/send")
    public String send(@RequestParam String title, @RequestParam String message) throws Exception {
        notificationService.sendNotificationToAll(title, message);
        return "Notification Sent";
    }
}


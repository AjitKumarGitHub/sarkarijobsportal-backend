package com.marbel.job.service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService; // library class

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marbel.job.pojo.PushSubscription;
import com.marbel.job.repositories.SubscriptionRepository;

 

@Service
public class NotificationService {

    private final String PUBLIC_KEY = "BFs1_kW0c7LQIxc53Uh8lAX6stsiPkt1w6gof1rTwdv6bKy2DfvSZIsk5WYz2kEtmXhqFvFUx9uyEb4P7Or9ikw";
    private final String PRIVATE_KEY = "WWIjL5sQLxgH216qsAsfdsDG9laF_9ou8RwCH8ClKJA";

    @Autowired
    private SubscriptionRepository repo;

    public void sendNotificationToAll(String title, String message) throws Exception {

        PushService webPush = new PushService(PUBLIC_KEY, PRIVATE_KEY); // ✔ correct class

        for (PushSubscription sub : repo.findAll()) {

            Notification notification = new Notification(
                    sub.getEndpoint(),
                    sub.getKeys().get("p256dh"),
                    sub.getKeys().get("auth"),
                    ("{\"title\":\"" + title + "\",\"message\":\"" + message + "\"}").getBytes()
            );

            webPush.send(notification); // ✔ correct method
        }
    }
}


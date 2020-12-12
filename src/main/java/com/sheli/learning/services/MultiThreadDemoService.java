package com.sheli.learning.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MultiThreadDemoService {
    @Async
    public void executeAsyncTask() {
        System.out.println("Started thread: "+Thread.currentThread().getId());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread: "+Thread.currentThread().getId()+ " ends.");
    }
}

package com.github.nicolasholanda.service;

public class ConsoleNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        System.out.println("Desktop notifications not available on this OS");
        System.out.println("REMINDER: " + message);
    }
} 
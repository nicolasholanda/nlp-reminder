package com.github.nicolasholanda.service.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        log.info("Desktop notifications not available on this OS");
        log.info("REMINDER: {}", message);
    }
} 
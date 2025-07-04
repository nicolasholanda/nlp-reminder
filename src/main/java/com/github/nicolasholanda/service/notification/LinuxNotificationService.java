package com.github.nicolasholanda.service.notification;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LinuxNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        try {
            ProcessBuilder pb = new ProcessBuilder("notify-send", title, message);
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                log.info("Desktop notification sent via notify-send");
            } else {
                log.error("notify-send failed with exit code: {}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error showing Linux notification: {}", e.getMessage());
        }
    }
} 
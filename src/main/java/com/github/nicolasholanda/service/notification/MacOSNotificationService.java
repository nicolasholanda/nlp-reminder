package com.github.nicolasholanda.service.notification;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MacOSNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        try {
            String script = String.format("display notification \"%s\" with title \"%s\"", message, title);
            ProcessBuilder pb = new ProcessBuilder("osascript", "-e", script);
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                log.info("Desktop notification sent via osascript");
            } else {
                log.error("osascript failed with exit code: {}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error showing macOS notification: {}", e.getMessage());
        }
    }
} 
package com.github.nicolasholanda.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class WindowsNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        try {
            String command = String.format("New-BurntToastNotification -Text '%s' -SecondLine '%s'", message, title);
            ProcessBuilder pb = new ProcessBuilder("powershell", "-Command", command);
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                log.info("Desktop notification sent via PowerShell");
            } else {
                log.error("PowerShell notification failed with exit code: {}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error showing Windows notification: {}", e.getMessage());
        }
    }
} 
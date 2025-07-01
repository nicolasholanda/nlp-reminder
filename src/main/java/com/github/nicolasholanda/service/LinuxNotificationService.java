package com.github.nicolasholanda.service;

import java.io.IOException;

public class LinuxNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        try {
            ProcessBuilder pb = new ProcessBuilder("notify-send", title, message);
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("Desktop notification sent via notify-send");
            } else {
                System.err.println("notify-send failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error showing Linux notification: " + e.getMessage());
        }
    }
} 
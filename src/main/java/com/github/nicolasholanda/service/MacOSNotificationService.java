package com.github.nicolasholanda.service;

import java.io.IOException;

public class MacOSNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        try {
            String script = String.format("display notification \"%s\" with title \"%s\"", message, title);
            ProcessBuilder pb = new ProcessBuilder("osascript", "-e", script);
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("Desktop notification sent via osascript");
            } else {
                System.err.println("osascript failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error showing macOS notification: " + e.getMessage());
        }
    }
} 
package com.github.nicolasholanda.service;

import java.io.IOException;

public class WindowsNotificationService implements NotificationService {
    
    @Override
    public void notify(String title, String message) {
        try {
            String command = String.format("New-BurntToastNotification -Text '%s' -SecondLine '%s'", message, title);
            ProcessBuilder pb = new ProcessBuilder("powershell", "-Command", command);
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("Desktop notification sent via PowerShell");
            } else {
                System.err.println("PowerShell notification failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error showing Windows notification: " + e.getMessage());
        }
    }
} 
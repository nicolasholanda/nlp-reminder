package com.github.nicolasholanda.service;

public class NotificationServiceFactory {
    
    public static NotificationService createNotificationService() {
        String osName = System.getProperty("os.name").toLowerCase();
        
        System.out.println("Initializing notification system...");
        System.out.println("OS: " + System.getProperty("os.name"));
        
        if (osName.contains("linux")) {
            return new LinuxNotificationService();
        } else if (osName.contains("mac")) {
            return new MacOSNotificationService();
        } else if (osName.contains("windows")) {
            return new WindowsNotificationService();
        } else {
            return new ConsoleNotificationService();
        }
    }
} 
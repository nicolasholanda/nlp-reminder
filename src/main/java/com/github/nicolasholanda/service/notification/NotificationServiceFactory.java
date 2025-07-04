package com.github.nicolasholanda.service.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationServiceFactory {
    
    public static NotificationService createNotificationService() {
        String osName = System.getProperty("os.name").toLowerCase();
        
        log.info("Initializing notification system...");
        log.info("OS: {}", System.getProperty("os.name"));
        
        if (osName.contains("linux")) {
            log.info("Using notify-send for Linux notifications");
            return new LinuxNotificationService();
        } else if (osName.contains("mac")) {
            log.info("Using osascript for macOS notifications");
            return new MacOSNotificationService();
        } else if (osName.contains("windows")) {
            log.info("Using PowerShell for Windows notifications");
            return new WindowsNotificationService();
        } else {
            log.info("Using console notifications (no native support)");
            return new ConsoleNotificationService();
        }
    }
} 
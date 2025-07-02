package com.github.nicolasholanda.service;

import com.github.nicolasholanda.model.Reminder;
import com.github.nicolasholanda.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ReminderMonitor {
    private final ReminderRepository repository;
    private final ScheduledExecutorService scheduler;
    private final NotificationService notificationService;
    
    public ReminderMonitor(ReminderRepository repository) {
        this.repository = repository;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.notificationService = NotificationServiceFactory.createNotificationService();
    }
    
    public void start() {
        log.info("Starting reminder monitor...");
        scheduler.scheduleAtFixedRate(this::checkDueReminders, 0, 1, TimeUnit.MINUTES);
    }
    
    public void stop() {
        log.info("Stopping reminder monitor...");
        scheduler.shutdown();
    }
    
    private void checkDueReminders() {
        List<Reminder> dueReminders = repository.getDueReminders();
        
        if (!dueReminders.isEmpty()) {
            log.info("Found {} due reminder(s)", dueReminders.size());
        }
        
        for (Reminder reminder : dueReminders) {
            showNotification(reminder);
            repository.markCompleted(reminder.getId());
        }
    }
    
    private void showNotification(Reminder reminder) {
        String title = "Reminder";
        String message = reminder.getTask();
        
        log.info("🔔 REMINDER: {}", message);
        notificationService.notify(title, message);
    }
} 
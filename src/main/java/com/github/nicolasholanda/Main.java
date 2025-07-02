package com.github.nicolasholanda;

import com.github.nicolasholanda.model.Reminder;
import com.github.nicolasholanda.repository.ReminderRepository;
import com.github.nicolasholanda.service.ReminderMonitor;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

@Slf4j
public class Main {
    private static final ReminderRepository repository = new ReminderRepository();
    private static final ReminderMonitor monitor = new ReminderMonitor(repository);
    
    public static void main(String[] args) {
        if(args.length < 1) {
            log.error("Provide at least one argument");
            System.exit(1);
        }

        startMonitor();
        
        String input = String.join(" ", args);
        log.info("Input: {}", input);

        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(input);
        
        if (groups.isEmpty()) {
            log.error("No dates found");
            System.exit(1);
        }

        List<Date> futureDates = filterFutureDates(groups);
        
        if (futureDates.isEmpty()) {
            log.error("No future dates found");
            System.exit(1);
        }

        Date selectedDate;
        if (futureDates.size() > 1) {
            log.info("Multiple future dates found:");
            for (int i = 0; i < futureDates.size(); i++) {
                log.info("  {}: {}", (i + 1), futureDates.get(i));
            }
            
            selectedDate = promptUserForDateSelection(futureDates);
        } else {
            selectedDate = futureDates.get(0);
        }

        String task = extractTask(input, groups);
        
        Reminder reminder = new Reminder(task, selectedDate);
        repository.saveReminder(reminder);
        
        log.info("Reminder created:");
        log.info("Task: {}", task);
        log.info("Due: {}", selectedDate);
        
        showPendingReminders();
        
        log.info("Monitor is running in background. Press Ctrl+C to stop.");
        keepAlive();
    }
    
    private static void startMonitor() {
        monitor.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down...");
            monitor.stop();
        }));
    }
    
    private static void keepAlive() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private static void showPendingReminders() {
        List<Reminder> pending = repository.getPendingReminders();
        if (!pending.isEmpty()) {
            log.info("Pending reminders:");
            for (Reminder reminder : pending) {
                log.info("  {}. {} (due: {})", reminder.getId(), reminder.getTask(), reminder.getDueDate());
            }
        }
    }
    
    private static Date promptUserForDateSelection(List<Date> dates) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Select a date (1-" + dates.size() + "): ");
                
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= dates.size()) {
                        return dates.get(choice - 1);
                    }
                }
                
                System.out.println("Invalid choice. Please enter a number between 1 and " + dates.size());
                scanner.nextLine();
            }
        }
    }
    
    private static List<Date> filterFutureDates(List<DateGroup> groups) {
        List<Date> futureDates = new ArrayList<>();
        Date now = new Date();
        
        for (DateGroup group : groups) {
            for (Date date : group.getDates()) {
                if (date.after(now)) {
                    futureDates.add(date);
                }
            }
        }
        
        return futureDates;
    }
    
    private static String extractTask(String input, List<DateGroup> groups) {
        String task = input;
        
        for (DateGroup group : groups) {
            String dateText = group.getText();
            task = task.replace(dateText, "").trim();
        }
        
        return task;
    }
}
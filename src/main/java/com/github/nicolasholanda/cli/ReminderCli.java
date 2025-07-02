package com.github.nicolasholanda.cli;

import com.github.nicolasholanda.model.Reminder;
import com.github.nicolasholanda.service.ReminderMonitor;
import com.github.nicolasholanda.service.ReminderService;
import com.github.nicolasholanda.util.ParserUtil;
import com.joestelmach.natty.DateGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ReminderCli {
    private final ReminderService service;
    private final ReminderMonitor monitor;

    public ReminderCli(ReminderService service, ReminderMonitor monitor) {
        this.service = service;
        this.monitor = monitor;
    }

    public void run(String[] args) {
        if (args.length < 1) {
            log.error("Provide at least one argument");
            System.exit(1);
        }

        monitor.start();

        String input = String.join(" ", args);

        List<DateGroup> groups = ParserUtil.parseDates(input);
        if (groups.isEmpty()) {
            log.error("No dates found");
            System.exit(1);
        }

        List<Date> futureDates = ParserUtil.filterFutureDates(groups);
        if (futureDates.isEmpty()) {
            log.error("No future dates found");
            System.exit(1);
        }

        Date selectedDate = selectDate(futureDates);
        String task = ParserUtil.extractTask(input, groups);
        saveReminder(selectedDate, task);
        

        keepAlive();
    }

    private Date selectDate(List<Date> dates) {
        if (dates.size() > 1) {
            return promptUserForDateSelection(dates);
        }
        
        return dates.get(0);
    }

    private Date promptUserForDateSelection(List<Date> dates) {
        log.info("Multiple future dates found:");
        for (int i = 0; i < dates.size(); i++) {
            log.info("  {}: {}", (i + 1), dates.get(i));
        }

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

    private void saveReminder(Date date, String task) { 
        Reminder reminder = new Reminder(task, date);
        service.saveReminder(reminder);

        log.info("Reminder created:");
        log.info("Task: {}", task);
        log.info("Due: {}", date);
    }

    private void keepAlive() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 
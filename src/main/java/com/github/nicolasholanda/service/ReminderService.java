package com.github.nicolasholanda.service;

import com.github.nicolasholanda.model.Reminder;
import com.github.nicolasholanda.repository.ReminderRepository;
import com.github.nicolasholanda.util.ParserUtil;
import com.joestelmach.natty.DateGroup;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class ReminderService {
    private final ReminderRepository repository;

    public ReminderService(ReminderRepository repository) {
        this.repository = repository;
    }

    public Reminder saveReminder(Reminder reminder) {
        repository.saveReminder(reminder);
        return reminder;
    }

    public List<Reminder> getPendingReminders() {
        return repository.getPendingReminders();
    }

    public void createReminderFrom(String input) {
        try {
            List<DateGroup> groups = ParserUtil.parseDates(input);
            if (groups.isEmpty()) {
                log.error("No dates found in request: {}", input);
                return;
            }

            List<Date> futureDates = ParserUtil.filterFutureDates(groups);
            if (futureDates.isEmpty()) {
                log.error("No future dates found in request: {}", input);
                return;
            }

            Date selectedDate = futureDates.get(0);
            if (futureDates.size() > 1) {
                log.info("Multiple dates found, using first: {}", selectedDate);
            }

            String task = ParserUtil.extractTask(input, groups);
            if (task.trim().isEmpty()) {
                log.error("No task found in request: {}", input);
                return;
            }

            Reminder reminder = new Reminder(task, selectedDate);

            saveReminder(reminder);

            log.info("Reminder created via socket. Task: {} - Due: {}", task, selectedDate);
        } catch (Exception e) {
            log.error("Error processing reminder request: {}", e.getMessage());
        }
    }
} 
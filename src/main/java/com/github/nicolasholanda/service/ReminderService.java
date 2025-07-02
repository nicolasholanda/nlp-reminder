package com.github.nicolasholanda.service;

import com.github.nicolasholanda.model.Reminder;
import com.github.nicolasholanda.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;

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
} 
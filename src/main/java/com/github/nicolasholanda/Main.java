package com.github.nicolasholanda;

import com.github.nicolasholanda.cli.ReminderCli;
import com.github.nicolasholanda.repository.ReminderRepository;
import com.github.nicolasholanda.service.ReminderMonitor;
import com.github.nicolasholanda.service.ReminderService;

public class Main {
    public static void main(String[] args) {
        ReminderRepository repository = new ReminderRepository();
        ReminderService service = new ReminderService(repository);
        ReminderMonitor monitor = new ReminderMonitor(repository);
        ReminderCli cli = new ReminderCli(service, monitor);
        cli.run(args);
    }
}
package com.github.nicolasholanda;

import com.github.nicolasholanda.repository.ReminderRepository;
import com.github.nicolasholanda.service.ReminderMonitor;
import com.github.nicolasholanda.service.ReminderService;
import com.github.nicolasholanda.socket.SocketListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        ReminderRepository repository = new ReminderRepository();
        ReminderService service = new ReminderService(repository);
        ReminderMonitor monitor = new ReminderMonitor(repository);
        SocketListener socketListener = new SocketListener(service);

        monitor.start();
        log.info("Reminder monitor started.");

        socketListener.start();
    }
}
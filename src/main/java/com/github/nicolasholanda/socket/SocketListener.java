package com.github.nicolasholanda.socket;

import com.github.nicolasholanda.service.ReminderService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class SocketListener {
    private static final String SOCKET_PATH = "/tmp/nlp-reminder.sock";

    public void start(ReminderService service) {
        Path socketPath = Path.of(SOCKET_PATH);
        try {
            Files.deleteIfExists(socketPath);
            try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX)) {
                server.bind(UnixDomainSocketAddress.of(socketPath));
                log.info("Listening for reminder requests on {}", SOCKET_PATH);
                while (true) {
                    try (SocketChannel channel = server.accept()) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(channel.socket().getInputStream(), StandardCharsets.UTF_8));
                        String line = reader.readLine();
                        if (line != null && !line.isBlank()) {
                            System.out.println("Received reminder request: " + line);
                        }
                    } catch (Exception e) {
                        log.error("Error handling socket connection: {}", e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to start socket listener: {}", e.getMessage());
        }
    }
} 
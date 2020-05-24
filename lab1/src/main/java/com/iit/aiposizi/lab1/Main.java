package com.iit.aiposizi.lab1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Instant;

@Slf4j
public class Main {

    private static final int PORT = 8081;

    public static void main(String... args) {
        try {
            var serverConnect = new ServerSocket(PORT);
            log.info("Server started. Listening for connections on port : {}", PORT);

            while (true) {
                var server = new HttpServer(serverConnect.accept());
                log.info("Connection is opened. {}", Instant.now());
                var thread = new Thread(server);
                thread.start();
            }
        } catch (IOException e) {
            log.error("Server Connection error : {}", e.getMessage());
        }
    }
}


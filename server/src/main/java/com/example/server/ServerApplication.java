package com.example.server;

import com.example.server.entity.Server;
import com.example.server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.server.enums.Status.SERVER_DOWN;
import static com.example.server.enums.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(
                new Server(null,
                    "192.168.1.168",
                    "Ubuntu Linux",
                    "16 GB",
                    "Personal PC",
                    "http://localhost:8080/server/image/server1.png", SERVER_UP));
            serverRepository.save(
                new Server(null,
                    "192.168.1.16",
                    "Ansible server",
                    "16 GB",
                    "Main server",
                    "http://localhost:8080/server/image/server2.png", SERVER_DOWN));
            serverRepository.save(
                new Server(null,
                    "192.168.1.54",
                    "Windows Server 2008",
                    "32 GB",
                    "Web server",
                    "http://localhost:8080/server/image/server3.png", SERVER_UP));
            serverRepository.save(
                new Server(null,
                    "192.168.1.01",
                    "Ubuntu Linux",
                    "32 GB",
                    "Notification server",
                    "http://localhost:8080/server/image/server4.png", SERVER_DOWN));
        };
    }
}

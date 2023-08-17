package com.example.server.controller;

import com.example.server.entity.Response;
import com.example.server.entity.Server;
import com.example.server.enums.Status;
import com.example.server.service.ServerService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", serverService.getServers(10)))
            .message("Servers retrieved!")
            .httpStatus(OK)
            .statusCode(OK.value())
            .build());
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress)
        throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", server))
            .message(server.getStatus() == Status.SERVER_UP ? "Ping success!" : "Ping failed!")
            .httpStatus(OK)
            .statusCode(OK.value())
            .build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(Response.builder()
            .timeStamp(now())
            .data(Map.of("server", serverService.create(server)))
            .message("Server created!")
            .httpStatus(CREATED)
            .statusCode(CREATED.value())
            .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
            .timeStamp(now())
            .data(Map.of("server", serverService.findById(id)))
            .message("Server retrieved!")
            .httpStatus(OK)
            .statusCode(OK.value())
            .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
            .timeStamp(now())
            .data(Map.of("server", serverService.delete(id)))
            .message("Server deleted!")
            .httpStatus(OK)
            .statusCode(OK.value())
            .build());
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(
            System.getProperty("user.home")
                + "/IdeaProjects/spring-boot-angular-project/server/images/" + fileName));
    }
}

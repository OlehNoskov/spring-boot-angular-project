package com.example.server.service.impl;

import com.example.server.entity.Server;
import com.example.server.enums.Status;
import com.example.server.repository.ServerRepository;
import com.example.server.service.ServerService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}.", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server update(Server server) {
        log.info(" Updating server: {}.", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Server findById(Long id) {
        log.info("Finding server by id: {}", id);
        return serverRepository.findById(id).orElseThrow();
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting servers by id: {}.", id);
        serverRepository.deleteById(id);
        return TRUE;
    }

    @Override
    public Collection<Server> getServers(int limit) {
        log.info("Fetching list of servers.");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server with IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(3_000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        int randomArrayIndex = new Random().nextInt(4);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/server/images/" + imageNames[randomArrayIndex]).toUriString();
    }
}

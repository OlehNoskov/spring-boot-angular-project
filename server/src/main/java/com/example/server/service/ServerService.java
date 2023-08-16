package com.example.server.service;

import com.example.server.entity.Server;
import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);

    Server update(Server server);

    Server findById(Long id);

    Boolean delete(Long id);

    Collection<Server> getServers(int limit);
    Server ping(String ipAddress) throws IOException;
}

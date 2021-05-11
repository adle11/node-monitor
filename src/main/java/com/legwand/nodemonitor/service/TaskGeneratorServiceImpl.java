package com.legwand.nodemonitor.service;

import com.legwand.nodemonitor.model.NodeStatus;
import com.legwand.nodemonitor.repository.NodesStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class TaskGeneratorServiceImpl implements TaskGeneratorService {

    private final static Logger LOG = Logger.getLogger(TaskGeneratorService.class.getName());

    private final int PING_TIMEOUT = 5000;

    @Autowired
    private NodesStatusRepository nodesStatusRepository;

    public Runnable getPingTask(String taskId, String ipAddress, int port) {

        return () -> {
            try {
                try (Socket soc = new Socket()) {
                    soc.connect(new InetSocketAddress(ipAddress, port), PING_TIMEOUT);
                }
                LOG.info(MessageFormat.format("{0} is reachable", ipAddress));
                nodesStatusRepository.saveNodeStatusResult(new NodeStatus(taskId, LocalDateTime.now(), true));
            } catch (IOException ex) {
                LOG.info(MessageFormat.format("{0} is NOT reachable", ipAddress));
                nodesStatusRepository.saveNodeStatusResult(new NodeStatus(taskId, LocalDateTime.now(), false));
            }
        };
    }


}

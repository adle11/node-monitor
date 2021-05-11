package com.legwand.nodemonitor.task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class PingTask implements Runnable {

    private final static Logger LOG = Logger.getLogger(PingTask.class.getName());
    private final int PING_TIMEOUT = 5000;

    private final String ipAddress;
    private final int port;

    public PingTask(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(ipAddress, port), PING_TIMEOUT);
            }
//            return true;
            LOG.info(MessageFormat.format("{0} is reachable", ipAddress));
        } catch (IOException ex) {
//            return false;
            LOG.info(MessageFormat.format("{0} is NOT reachable", ipAddress));
        }

    }
}

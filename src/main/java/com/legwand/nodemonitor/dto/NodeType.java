package com.legwand.nodemonitor.dto;

public enum NodeType {
    HTTP(80), HTTPS(443), FTP(21), SSH(22);

    public int getPort() {
        return port;
    }

    private final int port;

    NodeType(int port) {
        this.port = port;
    }
}

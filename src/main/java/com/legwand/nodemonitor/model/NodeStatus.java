package com.legwand.nodemonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "NodeStatuses")
public class NodeStatus {

    private String nodeId;
    private LocalDateTime date;
    private boolean reachable;

    public NodeStatus(String nodeId, LocalDateTime date, boolean reachable) {
        this.nodeId = nodeId;
        this.date = date;
        this.reachable = reachable;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }


}

package com.legwand.nodemonitor.dto;


public class NodeDataRequest {

    private String taskName;
    private String ipAddress;
    private NodeType nodeType;
    private String cron;
    private boolean nodeCheckActive;

    public NodeDataRequest(String taskName, String ipAddress, NodeType nodeType, String cron, boolean nodeCheckActive) {
        this.taskName = taskName;
        this.ipAddress = ipAddress;
        this.nodeType = nodeType;
        this.cron = cron;
        this.nodeCheckActive = nodeCheckActive;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public boolean isNodeCheckActive() {
        return nodeCheckActive;
    }

    public void setNodeCheckActive(boolean nodeCheckActive) {
        this.nodeCheckActive = nodeCheckActive;
    }

    @Override
    public String toString() {
        return "TaskDataRequest{" +
                ", taskName='" + taskName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", nodeType=" + nodeType +
                ", cron='" + cron + '\'' +
                ", isEnabled=" + nodeCheckActive +
                '}';
    }

}

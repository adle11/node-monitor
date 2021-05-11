package com.legwand.nodemonitor.model;

import com.legwand.nodemonitor.dto.NodeDataRequest;
import com.legwand.nodemonitor.dto.NodeType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Nodes")
public class Node {

    @Id
    private String id;
    private String nodeName;
    private String ipAddress;
    private NodeType nodeType;
    private String cron;
    private boolean nodeCheckActive;

    public Node(String nodeName, String ipAddress, NodeType nodeType, String cron, boolean nodeCheckActive) {
        this.nodeName = nodeName;
        this.ipAddress = ipAddress;
        this.nodeType = nodeType;
        this.cron = cron;
        this.nodeCheckActive = nodeCheckActive;
    }

    public Node(NodeDataRequest nodeDataRequest) {
        this.nodeName = nodeDataRequest.getTaskName();
        this.ipAddress = nodeDataRequest.getIpAddress();
        this.nodeType = nodeDataRequest.getNodeType();
        this.cron = nodeDataRequest.getCron();
        this.nodeCheckActive = nodeDataRequest.isNodeCheckActive();
    }

    public Node() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    public void setNodeCheckActive(boolean enabled) {
        this.nodeCheckActive = enabled;
    }

    @Override
    public String toString() {
        return "TaskDataRequest{" +
                ", id='" + id + '\'' +
                ", taskName='" + nodeName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", port=" + nodeType +
                ", cron='" + cron + '\'' +
                ", nodeCheckActive=" + nodeCheckActive +
                '}';
    }

}

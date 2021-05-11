package com.legwand.nodemonitor.service;

import com.legwand.nodemonitor.model.Node;
import com.legwand.nodemonitor.model.NodeStatus;

import java.util.List;

public interface NodesService {

    String addNode(Node node);
    Node getNode(String nodeId);
    void deleteNode(String nodeId);
    void startNodeCheck(String nodeId);
    void stopNodeCheck(String nodeId);
    void updateNode(String nodeId, Node node);
    List<Node> getAllNodes();
    List<Node> getReachableNodes();
    List<Node> getUnreachableNodes();
    Double getNodeReliability(String nodeId);
    List<NodeStatus> getNodeStatusHistory(String nodeId);
    void saveTaskResult(NodeStatus nodeStatus);

}

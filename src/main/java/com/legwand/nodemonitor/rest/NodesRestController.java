package com.legwand.nodemonitor.rest;

import com.legwand.nodemonitor.dto.NodeDataRequest;
import com.legwand.nodemonitor.model.Node;
import com.legwand.nodemonitor.model.NodeStatus;
import com.legwand.nodemonitor.service.NodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NodesRestController {

    @Autowired
    NodesService nodesService;

    @GetMapping("/nodes")
    public List<Node> getAllNodes() {
        return nodesService.getAllNodes();
    }

    @PostMapping("/nodes/add")
    public String addNode(@RequestBody NodeDataRequest nodeDataRequest) {
        Node node = new Node(nodeDataRequest);
        return nodesService.addNode(node);
    }

    @GetMapping("/nodes/{nodeId}")
    public Node geNode(@RequestBody String nodeId) {
        try {
            return nodesService.getNode(nodeId);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @PostMapping("/nodes/{nodeId}/check/start")
    public void startNodeCheck(@PathVariable String nodeId) {
        try {
            nodesService.startNodeCheck(nodeId);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @PostMapping("/nodes/{nodeId}/check/stop")
    public void stopNodeCheck(@PathVariable String nodeId) {
        try {
            nodesService.stopNodeCheck(nodeId);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @PutMapping("/nodes/{nodeId}/update")
    public void updateNode(@PathVariable String nodeId, @RequestBody NodeDataRequest nodeDataRequest) {
        try {
            nodesService.updateNode(nodeId, new Node(nodeDataRequest));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @DeleteMapping("/nodes/{nodeId}/delete")
    public void deleteNode(@PathVariable String nodeId) {
        try {
            nodesService.deleteNode(nodeId);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @GetMapping("/nodes/{nodeId}/status")
    public List<NodeStatus> getNodeStatusList(@PathVariable String nodeId) {
        try {
            return nodesService.getNodeStatusHistory(nodeId);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @GetMapping("/nodes/{nodeId}/reliability")
    public Double getNodeReliability(@PathVariable String nodeId) {
        try {
            return nodesService.getNodeReliability(nodeId);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found", ex);
        }
    }

    @GetMapping("/nodes/reachable")
    public List<Node> getReachableNodes() {
        return nodesService.getReachableNodes();
    }

    @GetMapping("/nodes/unreachable")
    public List<Node> getUnreachableNodes() {
        return nodesService.getUnreachableNodes();
    }


}

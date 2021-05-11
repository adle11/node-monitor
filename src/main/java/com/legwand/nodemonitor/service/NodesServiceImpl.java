package com.legwand.nodemonitor.service;

import com.legwand.nodemonitor.model.Node;
import com.legwand.nodemonitor.model.NodeStatus;
import com.legwand.nodemonitor.repository.NodesStatusRepository;
import com.legwand.nodemonitor.repository.NodesRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class NodesServiceImpl implements NodesService {

    private final static Logger LOG = Logger.getLogger(NodesServiceImpl.class.getName());

    private final static int POOL_SIZE = 50;

    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();

    @Autowired
    private NodesRepository nodesRepository;

    @Autowired
    private NodesStatusRepository nodesStatusRepository;

    @Autowired
    private TaskGeneratorService taskGeneratorService;

    @PostConstruct
    public void init() {
        taskScheduler.setPoolSize(POOL_SIZE);
        taskScheduler.initialize();
        LOG.info("Service initialized");
    }

    @Override
    public String addNode(Node node) {
        Node savedNode = nodesRepository.saveNode(node);
        if (savedNode.isNodeCheckActive()) {
//            PingTask ept = new PingTask(savedTask.getIpAddress(), savedTask.getHostType().getPort());
            Runnable pingTask = taskGeneratorService.getPingTask(savedNode.getId(), savedNode.getIpAddress(), savedNode.getNodeType().getPort());
            ScheduledFuture<?> sf = taskScheduler.schedule(pingTask, new CronTrigger(savedNode.getCron()));
            scheduledFutureMap.put(savedNode.getId(), sf);
        }
        LOG.info(MessageFormat.format("Task {0} added", savedNode.getId()));
        return savedNode.getId();
    }

    @Override
    public void deleteNode(String nodeId) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        if (scheduledFutureMap.containsKey(nodeId)) {
            scheduledFutureMap.get(nodeId).cancel(true);
            scheduledFutureMap.remove(nodeId);
        }
        nodesRepository.remoteNode(nodeId);
        LOG.info(MessageFormat.format("Task {0} deleted", nodeId));
    }

    @Override
    public Node getNode(String nodeId) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        return nodesRepository.findNode(nodeId);
    }

    @Override
    public void startNodeCheck(String nodeId) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        if (!scheduledFutureMap.containsKey(nodeId)) {
            Node node = nodesRepository.findNode(nodeId);
            ScheduledFuture<?> scheduledFuture = schedulePingTask(node);
            scheduledFutureMap.put(nodeId, scheduledFuture);
            node.setNodeCheckActive(true);
            LOG.info(MessageFormat.format("Task {0} started", node.getNodeName()));
        }
    }

    @Override
    public void stopNodeCheck(String nodeId) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        if (scheduledFutureMap.containsKey(nodeId)) {
            scheduledFutureMap.get(nodeId).cancel(true);
            scheduledFutureMap.remove(nodeId);
            Node node = nodesRepository.setTaskEnabled(nodeId, false);
            LOG.info(MessageFormat.format("Task {0} stopped", node.getId()));
        }
    }

    @Override
    public void updateNode(String nodeId, Node node) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        stopNodeCheck(nodeId);
        Node updatedNode = nodesRepository.updateNode(node);
        if (updatedNode.isNodeCheckActive()) {
            startNodeCheck(nodeId);
        }
        LOG.info(MessageFormat.format("Task {0} updated", updatedNode.getId()));
    }

    @Override
    public List<Node> getAllNodes() {
        return nodesRepository.getAllNodes();
    }

    @Override
    public List<Node> getReachableNodes() {
        return this.getAllNodes()
                .stream()
                .filter(node -> node.isNodeCheckActive() && nodesStatusRepository.getLatestNodeStatus(node.getId()).isReachable())
                .collect(Collectors.toList());
    }

    @Override
    public List<Node> getUnreachableNodes() {
        return this.getAllNodes()
                .stream()
                .filter(node -> node.isNodeCheckActive() && !nodesStatusRepository.getLatestNodeStatus(node.getId()).isReachable())
                .collect(Collectors.toList());
    }

    @Override
    public Double getNodeReliability(String nodeId) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        List<NodeStatus> nodeStatusList = nodesStatusRepository.getNodeStatusList(nodeId);
        long reachableStatusEntriesCount = nodeStatusList.stream().filter(NodeStatus::isReachable).count();
        return (double) reachableStatusEntriesCount / nodeStatusList.size();
    }

    @Override
    public void saveTaskResult(NodeStatus nodeStatus) {
        nodesStatusRepository.saveNodeStatusResult(nodeStatus);
    }

    @Override
    public List<NodeStatus> getNodeStatusHistory(String nodeId) {
        Validate.isTrue(nodesRepository.nodeExists(nodeId));
        List<NodeStatus> nodeStatusList = nodesStatusRepository.getNodeStatusList(nodeId);
        nodeStatusList.sort(Comparator.comparing(NodeStatus::getDate));
        return nodeStatusList;
    }

    private ScheduledFuture<?> schedulePingTask(Node node) {
//        PingTask ept = new PingTask(task.getIpAddress(), task.getHostType().getPort());
        Runnable pingTask = taskGeneratorService.getPingTask(node.getId(), node.getIpAddress(), node.getNodeType().getPort());
        return taskScheduler.schedule(pingTask, new CronTrigger(node.getCron()));
    }
}

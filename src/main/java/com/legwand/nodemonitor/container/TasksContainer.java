package com.legwand.nodemonitor.container;

import com.legwand.nodemonitor.model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksContainer {

    private final Map<Integer, Node> taskDataMap = new HashMap<>();

    private Integer lastTaskId;

    private static TasksContainer tasksContainer;

    private TasksContainer() {
        lastTaskId = 0;
    }

    public static TasksContainer getInstance() {
        if (tasksContainer == null) {
            tasksContainer = new TasksContainer();
        }
        return tasksContainer;
    }

    public int addTask(Node node) {
        int taskId = lastTaskId;
        taskDataMap.put(taskId, node);
        lastTaskId += 1;
        return taskId;
    }

    public Node getTask(int taskId) {
        return taskDataMap.get(taskId);
    }

    public void updateTask(int taskId, Node node) {
        taskDataMap.put(taskId, node);
    }

    public void updateTaskCron(int taskId, String cron) {
        taskDataMap.get(taskId).setCron(cron);
    }

    public void removeTask(int taskId) {
        taskDataMap.remove(taskId);
    }

    public List<Node> getAllTasks() {
        return new ArrayList<>(taskDataMap.values());
    }






}

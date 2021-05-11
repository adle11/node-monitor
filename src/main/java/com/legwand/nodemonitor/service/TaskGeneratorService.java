package com.legwand.nodemonitor.service;

public interface TaskGeneratorService {

    Runnable getPingTask(String taskId, String ipAddress, int port);

}

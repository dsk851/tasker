package com.ered.tasker.services;

import java.util.*;

import com.ered.tasker.domain.entities.Task;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);

    Task creatTask(UUID taskListId, Task task);

    Optional<Task> getTasks(UUID taskListId, UUID taskId);

    Task updateTask(UUID taskListId, UUID taskId, Task task);

    void deletTask(UUID taskListId, UUID taskId);
}

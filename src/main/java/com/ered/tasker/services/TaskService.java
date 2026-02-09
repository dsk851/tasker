package com.ered.tasker.services;

import java.util.*;

import com.ered.tasker.domain.entities.Task;

public interface TaskService {
    List<Task> listTasks(UUID taskListId, String username);

    Task creatTask(UUID taskListId, String username, Task task);

    Optional<Task> getTask(UUID taskListId, UUID taskId, String username);

    Task updateTask(UUID taskListId, UUID taskId, Task task, String username);
  
    void deletTask(UUID taskListId, UUID taskId, String username);
}

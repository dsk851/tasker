package com.ered.tasker.services;

import java.util.*;

import com.ered.tasker.domain.entities.TaskList;

public interface TaskListService {
    List<TaskList> getMyLists(String username);
    TaskList createTaskList(TaskList taskList, String username);
    Optional<TaskList> getTaskList(UUID id, String username);
    TaskList updateTaskList(UUID taskListid, TaskList taskList, String username);
    void deletTaskList(UUID taskListId, String username);
}

package com.ered.tasker.services;

import java.util.*;

import com.ered.tasker.domain.entities.TaskList;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    Optional<TaskList> getTaskList(UUID id);
    TaskList updateTaskList(UUID taskListid, TaskList taskList);
    void deletTaskList(UUID taskListId);
}

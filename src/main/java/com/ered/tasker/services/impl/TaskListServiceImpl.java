package com.ered.tasker.services.impl;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

import com.ered.tasker.domain.entities.TaskList;
import com.ered.tasker.domain.entities.UserEntity;
import com.ered.tasker.services.TaskListService;

import jakarta.transaction.Transactional;

import com.ered.tasker.repositories.TaskListRepository;
import com.ered.tasker.repositories.UserRepository;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;
    private final UserRepository userRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository, UserRepository userRepository) {
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskList> getMyLists(String username) {
        return taskListRepository.findByUserUsername(username);
    }

    @Transactional
    @Override
    public TaskList createTaskList(TaskList taskList, String username) {
        if (null != taskList.getId()) {
            throw new IllegalArgumentException("Task list already have an ID");
        }

        if (null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list Title must be present");
        }

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TaskList newTaskList = new TaskList();

        newTaskList.setTitle(taskList.getTitle());
        newTaskList.setDescription(taskList.getDescription());
        newTaskList.setUser(user);

        return taskListRepository.save(newTaskList);
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return taskListRepository.findByIdAndUserUsername(id, user.getUsername());
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListid, TaskList taskList, String username) {
        if (null == taskList.getId()) {
            throw new IllegalArgumentException("Task list must have an ID");
        }

        if (!Objects.equals(taskList.getId(), taskListid)) {
            throw new IllegalArgumentException("Attemting to change task list ID, this is not permitted");
        }
UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); 
        TaskList existingTaskList = taskListRepository
                .findByIdAndUserUsername(taskListid, user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription()

        );

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deletTaskList(UUID taskListId, String username) {
        taskListRepository.deleteByIdAndUserUsername(taskListId, username);
    }
}

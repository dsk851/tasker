package com.ered.tasker.services.impl;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

import com.ered.tasker.domain.entities.TaskList;
import com.ered.tasker.services.TaskListService;

import jakarta.transaction.Transactional;

import com.ered.tasker.repositories.TaskListRepository;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Transactional
    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()) {
            throw new IllegalArgumentException("Task list already have an ID");
        }

        if (null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list Title must be present");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now));
    }

    @SuppressWarnings("null")
    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListid, TaskList taskList) {
        if (null == taskList.getId()) {
            throw new IllegalArgumentException("Task list must have an ID");
        }

        if (!Objects.equals(taskList.getId(), taskListid)) {
            throw new IllegalArgumentException("Attemting to change task list ID, this is not permitted");
        }

        @SuppressWarnings("null")
        TaskList existingTaskList = taskListRepository.findById(taskListid)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @SuppressWarnings("null")
    @Override
    public void deletTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }
}

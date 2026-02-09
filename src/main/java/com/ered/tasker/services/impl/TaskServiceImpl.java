package com.ered.tasker.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ered.tasker.domain.entities.Task;
import com.ered.tasker.domain.entities.TaskList;
import com.ered.tasker.domain.entities.TaskPriority;
import com.ered.tasker.domain.entities.TaskStatus;
import com.ered.tasker.repositories.TaskRepository;
import com.ered.tasker.repositories.TaskListRepository;
import com.ered.tasker.repositories.UserRepository;
import com.ered.tasker.services.TaskService;

import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId, String username) {
        UserDetails user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return taskRepository.findAllByTaskListIdAndUsername(taskListId, user.getUsername());
    }

    @Transactional
    @Override
    public Task creatTask(UUID taskListId, String username, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already have an ID");
        }

        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list Title must be present");
        }

        if (null == taskListId) {
            throw new IllegalArgumentException("Task list ID is not specify");
        }

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must be provided");
        }

        TaskPriority taskpriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskList taskList = taskListRepository.findByIdAndUserUsername(taskListId, username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tasl List ID provided !!"));

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                taskpriority,
                taskList);

        return taskRepository.save(taskToSave);

    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId, String username) {
        if (null == taskListId || null == taskId) {
            throw new IllegalArgumentException("Id missing !!");
        }

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must be provided");
        }

        return taskRepository.findByTaskListIdAndTaskIdAndUsername(taskListId, taskId, username);

    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task, String username) {
        if (null == taskListId || null == taskId) {
            throw new IllegalArgumentException("Id missing !!");
        }

        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Attemting to change task ID, this is not permitted");
        }

        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task must have a priority");
        }

        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task must have a Status");
        }

        if(username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must be provided");
        }

        Task existingTask = taskRepository.findByTaskListIdAndTaskIdAndUsername(taskListId, taskId, username)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setDueDate(task.getDueDate()
        );

        return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deletTask(UUID taskListId, UUID taskId, String username) {
        taskRepository.deleteByTaskListIdAndIdAndUsername(taskListId, taskId, username);
    }
}

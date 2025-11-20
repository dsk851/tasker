package com.ered.tasker.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ered.tasker.domain.entities.Task;
import com.ered.tasker.domain.entities.TaskList;
import com.ered.tasker.domain.entities.TaskPriority;
import com.ered.tasker.domain.entities.TaskStatus;
import com.ered.tasker.repositories.TaskRepository;
import com.ered.tasker.repositories.TaskListRepository;
import com.ered.tasker.services.TaskService;

import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task creatTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already have an ID");
        }

        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list Title must be present");
        }

        if (null == taskListId) {
            throw new IllegalArgumentException("Task list ID is not specify");
        }

        TaskPriority taskpriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;
        LocalDateTime now = LocalDateTime.now();

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tasl List ID provided !!"));

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskpriority,
                taskList,
                now,
                now);

        return taskRepository.save(taskToSave);

    }

    @Override
    public Optional<Task> getTasks(UUID taskListId, UUID taskId) {
        if (null == taskListId || null == taskId) {
            throw new IllegalArgumentException("Id missing !!");
        }

        return taskRepository.findByTaskListIdAndId(taskListId, taskId);

    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
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

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deletTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }

}

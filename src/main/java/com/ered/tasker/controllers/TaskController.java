package com.ered.tasker.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ered.tasker.domain.dto.TaskDto;
import com.ered.tasker.domain.entities.Task;
import com.ered.tasker.mappers.TaskMapper;
import com.ered.tasker.repositories.TaskRepository;
import com.ered.tasker.services.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> ListTasks(@PathVariable("task_list_id") UUID taskListId) {

        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping()
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {

        Task task = taskService.creatTask(taskListId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(task);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID tasktId) {

        return taskService.getTasks(taskListId, tasktId).map(taskMapper::toDto);

    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID tasktId,
            @RequestBody TaskDto taskDto) {

        Task updatedTask = taskService.updateTask(taskListId, tasktId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteByTaskListAndId(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID tasktId) {
        taskService.deletTask(taskListId, tasktId);
    }

}

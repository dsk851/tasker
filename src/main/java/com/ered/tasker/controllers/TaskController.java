package com.ered.tasker.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ered.tasker.domain.dto.TaskDto;
import com.ered.tasker.domain.entities.Task;
import com.ered.tasker.domain.entities.UserEntity;
import com.ered.tasker.mappers.TaskMapper;
import com.ered.tasker.services.TaskService;
import com.ered.tasker.services.impl.CustomUserDetailsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final CustomUserDetailsService userDetailsService;

    public TaskController(TaskService taskService, TaskMapper taskMapper, CustomUserDetailsService userDetailsService) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());

        return taskService.listTasks(taskListId, user.getUsername())
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping()
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());
        Task task = taskService.creatTask(taskListId, user.getUsername(), taskMapper.fromDto(taskDto));

        return taskMapper.toDto(task);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID tasktId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());

        return taskService.getTask(taskListId, tasktId, user.getUsername()).map(taskMapper::toDto);

    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID tasktId,
            @RequestBody TaskDto taskDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());
        Task updatedTask = taskService.updateTask(taskListId, tasktId, taskMapper.fromDto(taskDto), user.getUsername());

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID tasktId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());
        taskService.deletTask(taskListId, tasktId, user.getUsername());
    }
}

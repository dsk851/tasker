package com.ered.tasker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.ered.tasker.mappers.TaskListMapper;
import com.ered.tasker.services.TaskListService;
import com.ered.tasker.services.impl.CustomUserDetailsService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ered.tasker.domain.dto.TaskListDto;
import com.ered.tasker.domain.entities.TaskList;
import com.ered.tasker.domain.entities.UserEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/task-lists")
@CrossOrigin(origins = "http://localhost:4200")

public class TaskListController {
    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;
    private CustomUserDetailsService userDetailsService;

    public TaskListController(
            TaskListService taskListService,
            TaskListMapper taskListMapper,
            CustomUserDetailsService userDetailsService) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public List<TaskListDto> getMyLists(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());

        return taskListService.getMyLists(user.getUsername())
                .stream()
                .map(taskListMapper::toDto)
                .toList();

    }

    @PostMapping()
    public TaskListDto createTaskList(
            @RequestBody TaskListDto taskListDto, @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());

        TaskList createdTaskList = taskListService.createTaskList(taskListMapper.fromDto(taskListDto),
                user.getUsername());

        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping(path = "/{task_list_id}")
    public Optional<TaskListDto> getTaskList(
            @PathVariable("task_list_id") UUID taskListId,
            @AuthenticationPrincipal UserDetails userDetails) {

        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());
        return taskListService.getTaskList(taskListId, user.getUsername()).map(taskListMapper::toDto);
    }

    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTasdList(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskListDto taskListDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());

        TaskList taskList = taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto),
                user.getUsername());

        return taskListMapper.toDto(taskList);
    }

    @DeleteMapping(path = "/{task_list_id}")
    public void deleTaskList(
            @PathVariable("task_list_id") UUID taskListId,
            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(userDetails.getUsername());
        taskListService.deletTaskList(taskListId, user.getUsername());
    }
}

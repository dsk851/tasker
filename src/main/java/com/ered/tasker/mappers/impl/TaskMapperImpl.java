package com.ered.tasker.mappers.impl;

import org.springframework.stereotype.Component;

import com.ered.tasker.domain.dto.TaskDto;
import com.ered.tasker.domain.entities.Task;
import com.ered.tasker.mappers.TaskMapper;


// mark this as a bean will inject any dependence that we declare
// and can be injected where ever we need it
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task
        (taskDto.id(),
        taskDto.title(),
        taskDto.description(),
        taskDto.dueDate(),
        taskDto.status(),
        taskDto.priority(),
        null,
        null,
        null
        );
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getPriority(),
            task.getStatus()
        );
    }

}

package com.ered.tasker.mappers.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ered.tasker.domain.dto.TaskDto;
import com.ered.tasker.domain.dto.TaskListDto;
import com.ered.tasker.domain.entities.Task;
import com.ered.tasker.domain.entities.TaskList;
import com.ered.tasker.domain.entities.TaskStatus;
import com.ered.tasker.mappers.TaskListMapper;
import com.ered.tasker.mappers.TaskMapper;

@Component
public class TaskListDtoImpl implements TaskListMapper {

    private final TaskMapper taskMapper;


    public TaskListDtoImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }
    
    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
       return new TaskList(
         taskListDto.id(),
         taskListDto.title(),
         taskListDto.description(),
         Optional.ofNullable(taskListDto.tasks())
            .map(tasks -> tasks.stream()
                .map(taskMapper::fromDto)
                    .toList()
                    ).orElse(null),
                    null,
                    null
       );
    }

    private Double CalculateTaskListProgress(List<Task> tasks){
        if(tasks == null){
            return null;
        }

        long closedTaskCount = tasks.stream().filter(task -> task.getStatus() == TaskStatus.CLOSED
        ).count();

        return (double) closedTaskCount /tasks.size();
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
            taskList.getId(),
            taskList.getTitle(),
            taskList.getDescription(),
            Optional.ofNullable(taskList.getTasks())
                .map(list -> list.size())
                .orElse(0),
            this.CalculateTaskListProgress(taskList.getTasks()),
            Optional.ofNullable(taskList.getTasks())
            .map(tasks -> tasks.stream()
                .map(taskMapper::toDto)
                    .toList())
                    .orElse(null)
        );
    }

}

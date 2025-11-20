package com.ered.tasker.mappers;

import com.ered.tasker.domain.dto.TaskDto;
import com.ered.tasker.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);

}

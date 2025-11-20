package com.ered.tasker.mappers;

import com.ered.tasker.domain.dto.TaskListDto;
import com.ered.tasker.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}

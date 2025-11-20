package com.ered.tasker.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ered.tasker.domain.entities.TaskPriority;
import com.ered.tasker.domain.entities.TaskStatus;

public record TaskDto(
    UUID id, 
    String title,
    String description,
    LocalDateTime dueDate,
    TaskPriority priority,
    TaskStatus status
) {

}

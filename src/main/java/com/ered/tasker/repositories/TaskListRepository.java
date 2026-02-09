package com.ered.tasker.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ered.tasker.domain.entities.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
    List<TaskList> findByUserId(UUID userId);


    List<TaskList> findByUserUsername(String username);
    Optional<TaskList> findByIdAndUserUsername(UUID id, String username);
    void deleteByIdAndUserUsername(UUID id, String username);
}

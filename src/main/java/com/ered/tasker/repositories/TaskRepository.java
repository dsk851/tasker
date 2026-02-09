package com.ered.tasker.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ered.tasker.domain.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("""
    SELECT t FROM Task t
    WHERE t.taskList.id = :taskListId
    AND t.taskList.user.username = :username
    """)
    List<Task> findAllByTaskListIdAndUsername(
            @Param("taskListId") UUID taskListId,
            @Param("username") String username);

    @Query("""
    SELECT t FROM Task t
    WHERE t.id = :taskId
    AND t.taskList.id = :taskListId
    AND t.taskList.user.username = :username
    """)
    Optional<Task> findByTaskListIdAndTaskIdAndUsername(
            @Param("taskListId") UUID taskListId,
            @Param("taskId") UUID taskId,
            @Param("username") String username);

    @Modifying
    @Query("""
    DELETE FROM Task t
    WHERE t.id = :taskId
    AND t.taskList.id = :taskListId
    AND t.taskList.user.username = :username
    """)
    void deleteByTaskListIdAndIdAndUsername(
            @Param("taskListId") UUID taskListId,
            @Param("taskId") UUID taskId,
            @Param("username") String username);
}

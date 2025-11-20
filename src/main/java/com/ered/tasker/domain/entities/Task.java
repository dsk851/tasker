package com.ered.tasker.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", updatable = true, nullable = false)
    private String title;

    // by default nullable is true and updatable true also
    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    public Task() {
    }

    public Task(UUID id, String title, String description, LocalDateTime dueDate, TaskStatus status,
            TaskPriority priority, TaskList taskList, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.taskList = taskList;
        this.created = created;
        this.updated = updated;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return this.priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskList gettaskList() {
        return this.taskList;
    }

    public void settaskList(TaskList taskTist) {
        this.taskList = taskList;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Task id(UUID id) {
        setId(id);
        return this;
    }

    public Task title(String title) {
        setTitle(title);
        return this;
    }

    public Task description(String description) {
        setDescription(description);
        return this;
    }

    public Task dueDate(LocalDateTime dueDate) {
        setDueDate(dueDate);
        return this;
    }

    public Task status(TaskStatus status) {
        setStatus(status);
        return this;
    }

    public Task priority(TaskPriority priority) {
        setPriority(priority);
        return this;
    }

    public Task taskList(TaskList taskList) {
        settaskList(taskList);
        return this;
    }

    public Task created(LocalDateTime created) {
        setCreated(created);
        return this;
    }

    public Task updated(LocalDateTime updated) {
        setUpdated(updated);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", title='" + getTitle() + "'" +
                ", description='" + getDescription() + "'" +
                ", dueDate='" + getDueDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", priority='" + getPriority() + "'" +
                ", taskList='" + gettaskList() + "'" +
                ", created='" + getCreated() + "'" +
                ", updated='" + getUpdated() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title)
                && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate)
                && Objects.equals(status, task.status) && Objects.equals(priority, task.priority)
                && Objects.equals(taskList, task.taskList) && Objects.equals(created, task.created)
                && Objects.equals(updated, task.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, status, priority, taskList, created, updated);
    }

}

package com.ered.tasker.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name= "task_list")
public class TaskList {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "taskList", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Task> tasks;

    @Column(name = "description")
    private String description; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public TaskList() {
    }

    public TaskList(UUID id, String title, String description, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tasks = tasks;
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

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TaskList id(UUID id) {
        setId(id);
        return this;
    }

    public TaskList title(String title) {
        setTitle(title);
        return this;
    }

    public TaskList tasks(List<Task> tasks) {
        setTasks(tasks);
        return this;
    }

    public TaskList description(String description) {
        setDescription(description);
        return this;
    }

    public TaskList createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public TaskList updatedAt(LocalDateTime updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TaskList)) {
            return false;
        }
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id) && Objects.equals(title, taskList.title) && Objects.equals(tasks, taskList.tasks) && Objects.equals(description, taskList.description) && Objects.equals(createdAt, taskList.createdAt) && Objects.equals(updatedAt, taskList.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tasks, description, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", tasks='" + getTasks() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
    
    
}

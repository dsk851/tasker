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

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    

    public TaskList() {
    }

    public TaskList(UUID id, String title, String description, List<Task> tasks, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tasks = tasks;
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

    public TaskList created(LocalDateTime created) {
        setCreated(created);
        return this;
    }

    public TaskList updated(LocalDateTime updated) {
        setUpdated(updated);
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
        return Objects.equals(id, taskList.id) && Objects.equals(title, taskList.title) && Objects.equals(tasks, taskList.tasks) && Objects.equals(description, taskList.description) && Objects.equals(created, taskList.created) && Objects.equals(updated, taskList.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tasks, description, created, updated);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", tasks='" + getTasks() + "'" +
            ", description='" + getDescription() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
    
    
}

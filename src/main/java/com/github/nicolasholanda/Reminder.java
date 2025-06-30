package com.github.nicolasholanda;

import java.util.Date;

public class Reminder {
    private Long id;
    private String task;
    private Date dueDate;
    private Date createdAt;
    private boolean completed;

    public Reminder(String task, Date dueDate) {
        this.task = task;
        this.dueDate = dueDate;
        this.createdAt = new Date();
        this.completed = false;
    }

    public Reminder(Long id, String task, Date dueDate, Date createdAt, boolean completed) {
        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                '}';
    }
} 
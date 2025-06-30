package com.github.nicolasholanda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
} 
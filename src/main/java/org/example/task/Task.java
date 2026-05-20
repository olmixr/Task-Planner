package org.example.task;

import java.time.LocalDate;

public class Task {

    private final int id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate deadline;

    public Task(int id, String title, String description, boolean completed, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

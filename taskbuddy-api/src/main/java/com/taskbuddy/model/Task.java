package com.taskbuddy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Task — MongoDB document model.
 *
 * Annotations:
 *  @Document(collection = "tasks")
 *      → Maps this class to the "tasks" collection in MongoDB.
 *
 *  @Id
 *      → Marks `id` as MongoDB's primary key (_id field).
 *        Spring Data automatically assigns a unique ObjectId if left null.
 */
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;          // MongoDB ObjectId (auto-generated)

    private String title;       // Short name of the task

    private String description; // Detailed description

    /**
     * priority — Urgency level of the task.
     * Accepted values: "LOW" | "MEDIUM" | "HIGH"
     */
    private String priority;

    /**
     * status — Current state of the task.
     * Accepted values: "TODO" | "IN_PROGRESS" | "DONE"
     */
    private String status;

    // ──────────────────────────────── Constructors ────────────────────────────────

    public Task() {
    }

    public Task(String title, String description, String priority, String status) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    // ────────────────────────────────── Getters ─────────────────────────────────

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    // ────────────────────────────────── Setters ─────────────────────────────────

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ──────────────────────────── Object Methods ──────────────────────────────

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id != null && id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

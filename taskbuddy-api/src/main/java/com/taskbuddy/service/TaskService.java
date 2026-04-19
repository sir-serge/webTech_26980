package com.taskbuddy.service;

import com.taskbuddy.model.Task;
import com.taskbuddy.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TaskService — Business logic layer.
 *
 * @Service → Marks this class as a Spring-managed service bean.
 *
 * Responsibilities:
 *  - Intercepts calls from the controller
 *  - Applies business rules before touching the database
 *  - Delegates data access to TaskRepository
 *
 * Why a Service layer?
 *  Controllers should only handle HTTP concerns (request / response).
 *  Business logic (validation, transformation) belongs here, keeping
 *  each layer focused and easy to test independently.
 */
@Service
public class TaskService {

    /**
     * @Autowired → Spring injects the TaskRepository bean automatically.
     * No need to call `new TaskRepository()` — Spring manages the lifecycle.
     */
    @Autowired
    private TaskRepository taskRepository;

    // ─────────────────────────────────────────────────────────────────────
    // READ — Retrieve tasks
    // ─────────────────────────────────────────────────────────────────────

    /**
     * getAllTasks — Returns every task stored in MongoDB.
     *
     * @return List<Task> — all documents in the "tasks" collection
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * getTaskById — Finds a single task by its MongoDB ObjectId.
     *
     * @param id — the task's unique identifier
     * @return Optional<Task> — present if found, empty if not
     *
     * Using Optional forces the controller to handle the "not found" case
     * explicitly, preventing NullPointerExceptions.
     */
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // ─────────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * createTask — Persists a new task to MongoDB.
     *
     * @param task — Task data received from the controller (deserialized JSON)
     * @return Task — the saved document, now including the auto-generated id
     *
     * repository.save() performs an INSERT when task.id is null,
     * or an UPSERT when task.id already exists.
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // ─────────────────────────────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * updateTask — Replaces an existing task's fields with new values.
     *
     * Strategy:
     *  1. Find the existing document by id (throw exception if missing)
     *  2. Overwrite only the editable fields (title, description, priority, status)
     *  3. Save the updated document back to MongoDB
     *
     * @param id          — id of the task to update
     * @param updatedTask — new field values sent from the client
     * @return Task       — the updated, persisted document
     * @throws RuntimeException if no task with the given id exists
     */
    public Task updateTask(String id, Task updatedTask) {
        // Fetch the existing task or throw a descriptive error
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        // Apply updates — only mutable fields; id is never changed
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setPriority(updatedTask.getPriority());
        existing.setStatus(updatedTask.getStatus());

        // Persist and return the updated document
        return taskRepository.save(existing);
    }

    // ─────────────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * deleteTask — Removes a task from MongoDB by its id.
     *
     * @param id — id of the task to delete
     * @throws RuntimeException if the task does not exist (prevents silent failures)
     */
    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}

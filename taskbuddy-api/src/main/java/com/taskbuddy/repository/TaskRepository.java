package com.taskbuddy.repository;

import com.taskbuddy.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TaskRepository — Data access layer for the Task collection.
 *
 * Extends MongoRepository<Task, String>:
 *   - Task   → the document type this repository manages
 *   - String → the type of the @Id field (MongoDB ObjectId stored as String)
 *
 * By extending MongoRepository, Spring Data automatically provides:
 *   ┌─────────────────────────────────┬──────────────────────────────────┐
 *   │ Method                          │ Description                      │
 *   ├─────────────────────────────────┼──────────────────────────────────┤
 *   │ findAll()                       │ Retrieve all tasks               │
 *   │ findById(id)                    │ Find task by ID                  │
 *   │ save(task)                      │ Insert or update a task          │
 *   │ deleteById(id)                  │ Delete task by ID                │
 *   │ existsById(id)                  │ Check if task exists             │
 *   │ count()                         │ Count all tasks                  │
 *   └─────────────────────────────────┴──────────────────────────────────┘
 *
 * No SQL or MongoDB query code is needed — Spring generates it automatically.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    // Custom query methods can be added here when needed.
    // Example: List<Task> findByStatus(String status);
}

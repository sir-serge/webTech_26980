package com.taskbuddy.controller;

import com.taskbuddy.model.Task;
import com.taskbuddy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TaskController — REST API layer (HTTP request handler).
 *
 * Annotations:
 *  @RestController
 *      → Combines @Controller + @ResponseBody.
 *        Every method return value is automatically serialized to JSON.
 *
 *  @RequestMapping("/api/tasks")
 *      → All endpoints in this class are prefixed with /api/tasks
 *
 *  @CrossOrigin(origins = "*")
 *      → Allows requests from any origin (e.g., Vue.js dev server on :5173).
 *        In production, replace "*" with your actual frontend domain.
 *
 * ─────────────────────────────────────────────────────────────────────────
 *  Endpoint Summary
 * ─────────────────────────────────────────────────────────────────────────
 *  GET    /api/tasks          → Retrieve all tasks
 *  GET    /api/tasks/{id}     → Retrieve a single task by ID
 *  POST   /api/tasks          → Create a new task
 *  PUT    /api/tasks/{id}     → Update an existing task
 *  DELETE /api/tasks/{id}     → Delete a task
 * ─────────────────────────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    /**
     * Spring injects the TaskService bean automatically.
     * The controller delegates ALL business logic to the service layer.
     */
    @Autowired
    private TaskService taskService;

    // ─────────────────────────────────────────────────────────────────────
    // GET /api/tasks  →  Retrieve ALL tasks
    // ─────────────────────────────────────────────────────────────────────

    /**
     * getAllTasks
     *
     * HTTP Method : GET
     * Path        : /api/tasks
     * Response    : 200 OK — JSON array of all tasks
     *
     * Example response:
     * [
     *   { "id": "663...", "title": "Study Spring", "status": "TODO", ... },
     *   { "id": "664...", "title": "Build Vue UI", "status": "IN_PROGRESS", ... }
     * ]
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks); // 200 OK
    }

    // ─────────────────────────────────────────────────────────────────────
    // GET /api/tasks/{id}  →  Retrieve ONE task
    // ─────────────────────────────────────────────────────────────────────

    /**
     * getTaskById
     *
     * HTTP Method : GET
     * Path        : /api/tasks/{id}
     * Response    : 200 OK  — task JSON if found
     *               404 Not Found — if no task matches the id
     *
     * @PathVariable → extracts {id} from the URL path
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)                        // 200 OK  if present
                .orElse(ResponseEntity.notFound().build());     // 404 Not Found
    }

    // ─────────────────────────────────────────────────────────────────────
    // POST /api/tasks  →  Create a new task
    // ─────────────────────────────────────────────────────────────────────

    /**
     * createTask
     *
     * HTTP Method : POST
     * Path        : /api/tasks
     * Body        : JSON object representing the new task (no id needed)
     * Response    : 201 Created — the saved task including its generated id
     *
     * @RequestBody → Spring deserializes the incoming JSON body into a Task object.
     *
     * Example request body:
     * {
     *   "title": "Study Spring Boot",
     *   "description": "Learn REST API design",
     *   "priority": "HIGH",
     *   "status": "TODO"
     * }
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask); // 201 Created
    }

    // ─────────────────────────────────────────────────────────────────────
    // PUT /api/tasks/{id}  →  Update an existing task
    // ─────────────────────────────────────────────────────────────────────

    /**
     * updateTask
     *
     * HTTP Method : PUT
     * Path        : /api/tasks/{id}
     * Body        : JSON with updated field values
     * Response    : 200 OK  — the updated task
     *               404 Not Found — if task id does not exist
     *
     * PUT replaces the full resource (send all fields, not just the changed ones).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id,
                                           @RequestBody Task updatedTask) {
        try {
            Task result = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(result); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // DELETE /api/tasks/{id}  →  Delete a task
    // ─────────────────────────────────────────────────────────────────────

    /**
     * deleteTask
     *
     * HTTP Method : DELETE
     * Path        : /api/tasks/{id}
     * Response    : 204 No Content — successful deletion (no body returned)
     *               404 Not Found  — if task id does not exist
     *
     * 204 No Content is the REST convention for a successful DELETE:
     * the resource is gone, so there is nothing to return in the body.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }
}

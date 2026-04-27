# TaskBuddy

TaskBuddy is a full-stack task management app built with Vue 3, Pinia, Vue Router, Spring Boot, and MongoDB.
It supports task CRUD, protected routes, theme switching, profile state, and Swagger-based API testing.

## What We Built

- A Vue 3 single-page app with pages for home, login, register, dashboard, add task, tasks, and profile.
- A Pinia state layer for tasks, auth, theme, toast messages, and user profile data.
- A Spring Boot REST API for task storage and CRUD operations.
- MongoDB persistence for task data.
- Route protection for authenticated pages.
- A responsive UI with dark mode support and accessibility details like a skip link and focus states.
- Swagger UI documentation so the API can be tested quickly from the browser.

## Tech Stack

- Front end: Vue 3, Vue Router, Pinia, Axios
- Back end: Spring Boot, Spring Web, Spring Data MongoDB, Springdoc OpenAPI
- Database: MongoDB
- Tooling: Vue CLI, Maven

## Project Structure

```text
webTech_26980/
  src/                # Vue front end
  public/             # Static assets
  taskbuddy-api/      # Spring Boot backend
```

## Requirements

- Node.js and npm
- Java 17 or newer
- Maven
- MongoDB running locally on `mongodb://localhost:27017`

## Run The Project

Start the backend and frontend in two terminals.

### 1. Start the backend

From the project root:

```bat
cd taskbuddy-api
"C:\Program Files\apache-maven-3.8.9\bin\mvn.cmd" spring-boot:run
```

The API runs on `http://localhost:3001`.

### 2. Start the frontend

Open a second terminal in the project root:

```bat
npm install
npm run serve
```

The app runs on the Vue development server, usually `http://localhost:8080`.

## Swagger Test

We verified the API through Swagger UI:

- Swagger UI: `http://localhost:3001/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:3001/v3/api-docs`

A real task id that was used during testing:

```text
69e512389c8b522ad5c79e7f
```

## Main Features

- Task CRUD:
  - create tasks
  - edit tasks
  - delete tasks
  - load all tasks from the API
- Task filtering by status:
  - `TODO`
  - `IN_PROGRESS`
  - `DONE`
- Theme toggle with light and dark modes
- Local profile information stored in `localStorage`
- Toast feedback for user actions
- Protected routes for dashboard and task pages

## API Layer

The front end talks to the backend through Axios:

```js
import axios from 'axios'

const API = axios.create({
  baseURL: 'http://localhost:3001/api'
})

export const getTasks = () => API.get('/tasks')
export const createTask = (task) => API.post('/tasks', task)
export const updateTask = (id, task) => API.put(`/tasks/${id}`, task)
export const deleteTask = (id) => API.delete(`/tasks/${id}`)
```

## Protected Routes

Authenticated pages are guarded in the router:

```js
router.beforeEach((to, _from, next) => {
  const requiresAuth = to.matched.some(r => r.meta.requiresAuth)
  if (requiresAuth && pinia) {
    const auth = useAuthStore(pinia)
    if (!auth.isAuthenticated) {
      next({ name: 'Login' })
      return
    }
  }
  next()
})
```

## Backend API

The Spring Boot backend exposes REST endpoints under `/api/tasks`:

- `GET /api/tasks` - get all tasks
- `GET /api/tasks/{id}` - get one task
- `POST /api/tasks` - create a task
- `PUT /api/tasks/{id}` - update a task
- `DELETE /api/tasks/{id}` - delete a task

Example controller method:

```java
@GetMapping
public ResponseEntity<List<Task>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks());
}
```

## Data Model

Tasks are stored in MongoDB with this document model:

```java
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String priority;
    private String status;
}
```

## What Was Fixed

- Removed the BOM encoding problem in `Task.java`.
- Replaced Lombok-generated getters/setters with plain Java methods to avoid IDE symbol warnings.
- Simplified `TaskController` constructor injection so `taskService` is initialized explicitly.
- Cleaned `jsconfig.json` so VS Code alias resolution is simpler.

## Testing Screenshots

The following screenshots show API testing in Swagger UI.

### GET task by id

![Swagger GET test](./img%201.png)

### PUT update task

![Swagger PUT test](./img%202.png)

### DELETE task

![Swagger DELETE test](./img%203.png)

## Configuration

Backend settings are in `taskbuddy-api/src/main/resources/application.properties`:

```properties
server.port=3001
spring.data.mongodb.uri=mongodb://localhost:27017/task-buddy
```

## Notes

- The front end expects the backend to run on port `3001`.
- If MongoDB is not running, task loading and saving will fail.
- Swagger is the fastest way to verify the API before submission.

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TaskBuddy</title>
    <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }

        #app {
            max-width: 900px;
            margin: 0 auto;
            background: #f0fdf4;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }

        h1 {
            color: #7c3aed;
            font-size: 2.5rem;
            margin-bottom: 30px;
            text-align: left;
        }

        .input-section {
            display: flex;
            gap: 10px;
            margin-bottom: 30px;
        }

        input[type="text"] {
            flex: 1;
            padding: 15px;
            border: 2px solid #bbf7d0;
            border-radius: 8px;
            font-size: 1rem;
            background: white;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus {
            outline: none;
            border-color: #4ade80;
        }

        .btn {
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-add {
            background: #4ade80;
            color: white;
        }

        .btn-add:hover {
            background: #22c55e;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(74, 222, 128, 0.4);
        }

        .task-list {
            margin-bottom: 30px;
        }

        .task-item {
            background: white;
            padding: 20px;
            margin-bottom: 12px;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-left: 4px solid #4ade80;
            transition: all 0.3s;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .task-item:hover {
            transform: translateX(5px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .task-item.completed {
            opacity: 0.6;
            border-left-color: #94a3b8;
        }

        .task-item.completed .task-text {
            text-decoration: line-through;
            color: #94a3b8;
        }

        .task-text {
            font-size: 1.1rem;
            color: #1e293b;
            flex: 1;
        }

        .task-actions {
            display: flex;
            gap: 10px;
        }

        .btn-complete {
            background: #4ade80;
            color: white;
            padding: 8px 20px;
            font-size: 0.9rem;
        }

        .btn-complete:hover {
            background: #22c55e;
        }

        .btn-delete {
            background: #ef4444;
            color: white;
            padding: 8px 20px;
            font-size: 0.9rem;
        }

        .btn-delete:hover {
            background: #dc2626;
        }

        .counter-section {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .count-display {
            font-size: 1.2rem;
            color: #1e293b;
            font-weight: 600;
        }

        .btn-increase {
            background: #4ade80;
            color: white;
            padding: 12px 24px;
        }

        .btn-increase:hover {
            background: #22c55e;
        }

        .empty-state {
            text-align: center;
            padding: 40px;
            color: #64748b;
            font-size: 1.1rem;
        }

        .stats {
            display: flex;
            gap: 20px;
            margin-top: 20px;
        }

        .stat-card {
            flex: 1;
            background: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .stat-number {
            font-size: 2rem;
            font-weight: bold;
            color: #7c3aed;
        }

        .stat-label {
            color: #64748b;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div id="app">
        <h1>TaskBuddy</h1>
        
        <div class="input-section">
            <input 
                type="text" 
                v-model="newTask" 
                @keyup.enter="addTask"
                placeholder="New Task"
            >
            <button class="btn btn-add" @click="addTask">Add Task</button>
        </div>

        <div class="task-list" v-if="tasks.length > 0">
            <div 
                v-for="task in tasks" 
                :key="task.id"
                class="task-item"
                :class="{ completed: task.completed }"
            >
                <span class="task-text">{{ task.text }}</span>
                <div class="task-actions">
                    <button 
                        class="btn btn-complete" 
                        @click="toggleComplete(task.id)"
                    >
                        {{ task.completed ? 'Undo' : 'Complete' }}
                    </button>
                    <button 
                        class="btn btn-delete" 
                        @click="deleteTask(task.id)"
                    >
                        Delete
                    </button>
                </div>
            </div>
        </div>

        <div class="empty-state" v-else>
            No tasks yet. Add your first task above! 🎯
        </div>

        <div class="counter-section">
            <span class="count-display">Count: {{ count }}</span>
            <button class="btn btn-increase" @click="increaseCount">Increase +</button>
        </div>

        <div class="stats">
            <div class="stat-card">
                <div class="stat-number">{{ totalTasks }}</div>
                <div class="stat-label">Total Tasks</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">{{ completedTasks }}</div>
                <div class="stat-label">Completed</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">{{ activeTasks }}</div>
                <div class="stat-label">Active</div>
            </div>
        </div>
    </div>

    <script>
        const { createApp } = Vue;

        createApp({
            data() {
                return {
                    newTask: '',
                    tasks: [
                        { id: 1, text: 'Learn Vue3', completed: false },
                        { id: 2, text: 'Build TaskBuddy', completed: false },
                        { id: 3, text: 'Deploy', completed: false }
                    ],
                    count: 0,
                    nextId: 4
                }
            },
            computed: {
                totalTasks() {
                    return this.tasks.length;
                },
                completedTasks() {
                    return this.tasks.filter(task => task.completed).length;
                },
                activeTasks() {
                    return this.tasks.filter(task => !task.completed).length;
                }
            },
            methods: {
                addTask() {
                    if (this.newTask.trim()) {
                        this.tasks.push({
                            id: this.nextId++,
                            text: this.newTask,
                            completed: false
                        });
                        this.newTask = '';
                    }
                },
                deleteTask(id) {
                    this.tasks = this.tasks.filter(task => task.id !== id);
                },
                toggleComplete(id) {
                    const task = this.tasks.find(task => task.id === id);
                    if (task) {
                        task.completed = !task.completed;
                    }
                },
                increaseCount() {
                    this.count++;
                }
            }
        }).mount('#app');
    </script>
</body>
</html>

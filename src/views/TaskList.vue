<template>
  <div class="taskListContainer">
    <h2 class="heading">My Tasks</h2>
    <TaskInput @add-task="addTask" />
    
    <div v-if="items.length === 0" class="emptyState">
      <p>📋 No tasks yet. Add one to get started!</p>
    </div>
    
    <div v-else class="tasksList">
      <div v-if="upcomingTasks.length > 0" class="taskSection">
        <h3 class="sectionTitle">Upcoming Tasks</h3>
        <TaskItem
          v-for="(task, index) in upcomingTasks"
          :key="`upcoming-${index}`"
          :task="task"
          @complete="completeTask(getOriginalIndex(task))"
          @delete="removeTask(getOriginalIndex(task))"
        />
      </div>

      <div v-if="completedTasks.length > 0" class="taskSection">
        <h3 class="sectionTitle">Completed Tasks</h3>
        <TaskItem
          v-for="(task, index) in completedTasks"
          :key="`completed-${index}`"
          :task="task"
          @complete="completeTask(getOriginalIndex(task))"
          @delete="removeTask(getOriginalIndex(task))"
        />
      </div>
    </div>

    <div v-if="items.length > 0" class="taskStats">
      <div class="statCard">
        <span class="statLabel">Total Tasks:</span>
        <span class="statValue">{{ items.length }}</span>
      </div>
      <div class="statCard">
        <span class="statLabel">Completed:</span>
        <span class="statValue">{{ completedCount }}</span>
      </div>
      <div class="statCard">
        <span class="statLabel">Remaining:</span>
        <span class="statValue">{{ items.length - completedCount }}</span>
      </div>
      <div class="statCard">
        <span class="statLabel">Completion Rate:</span>
        <span class="statValue">{{ completionRate }}%</span>
      </div>
    </div>
  </div>
</template>

<script>
import TaskInput from '../components/TaskInput.vue'
import TaskItem from '../components/TaskItem.vue'

export default {
  name: 'TaskListView',
  components: {
    TaskInput,
    TaskItem
  },
  data() {
    return {
      items: []
    }
  },
  computed: {
    completedCount() {
      return this.items.filter(task => task.completed).length
    },
    completionRate() {
      return this.items.length === 0 ? 0 : Math.round((this.completedCount / this.items.length) * 100)
    },
    upcomingTasks() {
      return this.items.filter(task => !task.completed)
    },
    completedTasks() {
      return this.items.filter(task => task.completed)
    }
  },
  methods: {
    addTask(taskData) {
      const newTask = {
        name: taskData.name,
        type: taskData.type,
        urgency: taskData.urgency,
        dueDate: taskData.dueDate,
        dueTime: taskData.dueTime,
        description: taskData.description,
        completed: false,
        createdAt: new Date().toISOString()
      }
      this.items.push(newTask)
      console.log('Task Added:', taskData.name)
    },
    completeTask(index) {
      this.items[index].completed = !this.items[index].completed
    },
    removeTask(index) {
      console.log('Task Removed:', this.items[index].name)
      this.items.splice(index, 1)
    },
    getOriginalIndex(task) {
      return this.items.findIndex(t => t === task)
    }
  }
}
</script>

<style scoped>
.taskListContainer {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.heading {
  font-size: 2rem;
  color: #2647e8;
  margin-bottom: 30px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.tasksList {
  display: flex;
  flex-direction: column;
}

.taskSection {
  margin-bottom: 30px;
}

.sectionTitle {
  font-size: 1.2rem;
  color: #475569;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #e2e8f0;
}

.emptyState {
  text-align: center;
  padding: 60px 40px;
  background-color: #f8fafc;
  border-radius: 12px;
  color: #64748b;
  font-size: 1.2rem;
  border: 2px dashed #cbd5e1;
}

.taskStats {
  margin-top: 40px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 15px;
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
}

.statCard {
  background: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.statLabel {
  color: #64748b;
  font-weight: 600;
  font-size: 0.9rem;
  margin-bottom: 5px;
}

.statValue {
  color: #2647e8;
  font-weight: 700;
  font-size: 1.8rem;
}
</style>

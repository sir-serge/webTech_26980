<template>
  <div class="taskCard" :class="{ completed: task.completed }">
    <div class="taskHeader">
      <div class="taskTitle">
        <input
          type="checkbox"
          :checked="task.completed"
          @change="$emit('complete')"
          class="checkbox"
        />
        <span class="taskName" :class="{ completed: task.completed }">
          {{ task.name }}
        </span>
        <span class="urgencyBadge" :class="urgencyClass">
          {{ task.urgency }}
        </span>
      </div>
      <span class="taskType">{{ task.type }}</span>
    </div>

    <div class="taskDetails">
      <div class="detailItem">
        <span class="label">Due Date:</span>
        <span class="value">{{ formatDate(task.dueDate) }}</span>
        <span v-if="task.dueTime" class="value">{{ task.dueTime }}</span>
      </div>
      <div v-if="task.description" class="detailItem">
        <span class="label">Description:</span>
        <span class="value">{{ task.description }}</span>
      </div>
    </div>

    <div class="taskActions">
      <button class="deleteButton" @click="$emit('delete')">Delete</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskItem',
  props: {
    task: {
      type: Object,
      required: true
    }
  },
  computed: {
    urgencyClass() {
      return `urgency-${this.task.urgency.toLowerCase()}`
    }
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'short', day: 'numeric' }
      return new Date(dateString).toLocaleDateString(undefined, options)
    }
  },
  emits: ['complete', 'delete']
}
</script>

<style scoped>
.taskCard {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 12px;
  transition: all 0.3s;
}

.taskCard:hover {
  border-color: #2647e8;
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.1);
}

.taskCard.completed {
  background-color: #f0fdf4;
  border-color: #bbf7d0;
}

.taskHeader {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.taskTitle {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 1;
}

.checkbox {
  width: 20px;
  height: 20px;
  cursor: pointer;
  accent-color: #4ade80;
}

.taskName {
  font-size: 1.1rem;
  color: #1e293b;
  font-weight: 600;
}

.taskName.completed {
  color: #4ade80;
  text-decoration: line-through;
  opacity: 0.7;
}

.urgencyBadge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  color: white;
}

.urgency-low {
  background-color: #10b981;
}

.urgency-medium {
  background-color: #f59e0b;
}

.urgency-high {
  background-color: #ef4444;
}

.urgency-critical {
  background-color: #8b5cf6;
}

.taskType {
  background-color: #dbeafe;
  color: #2647e8;
  padding: 4px 12px;
  border-radius: 8px;
  font-size: 0.85rem;
  font-weight: 600;
}

.taskDetails {
  background-color: #f8fafc;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 12px;
  font-size: 0.9rem;
}

.detailItem {
  display: flex;
  gap: 8px;
  align-items: flex-start;
  margin-bottom: 8px;
}

.detailItem:last-child {
  margin-bottom: 0;
}

.label {
  font-weight: 600;
  color: #64748b;
  min-width: 100px;
}

.value {
  color: #1e293b;
}

.taskActions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.deleteButton {
  padding: 6px 16px;
  background-color: #ef4444;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s;
}

.deleteButton:hover {
  background-color: #dc2626;
  transform: scale(1.05);
}
</style>

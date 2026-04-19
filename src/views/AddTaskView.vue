<template>
  <div class="addContainer">
    <div class="addHeader">
      <router-link to="/dashboard" class="backBtn" aria-label="Back to dashboard">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M19 12H5M12 5l-7 7 7 7"/></svg>
      </router-link>
      <div>
        <h2 class="heading">Add New Task</h2>
        <p class="subheading">Fill in the details below to create a task.</p>
      </div>
    </div>

    <div class="formCard">
      <TaskInput @add-task="handleAddTask" />
    </div>

    <div v-if="recentlyAdded.length > 0" class="recentSection">
      <h3 class="recentHeading">Just added</h3>
      <ul class="recentList">
        <li v-for="t in recentlyAdded" :key="t.id" class="recentItem">
          <span class="dot" :class="`pri-${(t.priority||'LOW').toLowerCase()}`" aria-hidden="true" />
          <span class="recentTitle">{{ t.title }}</span>
          <span class="recentStatus">{{ statusLabel(t.status) }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useTasksStore } from '../stores/tasks'
import { useToastStore  } from '../stores/toast'
import TaskInput from '../components/TaskInput.vue'

export default {
  name: 'AddTaskView',
  components: { TaskInput },
  setup() {
    const store = useTasksStore()
    const toast = useToastStore()
    const recentlyAdded = ref([])

    async function handleAddTask(taskData) {
      try {
        const created = await store.addTask(taskData)
        toast.success(`"${created.title}" added!`)
        recentlyAdded.value.unshift(created)
        if (recentlyAdded.value.length > 3) recentlyAdded.value.pop()
      } catch {
        toast.error('Failed to create task. Is the backend running?')
      }
    }

    function statusLabel(status) {
      const map = { TODO: 'To Do', IN_PROGRESS: 'In Progress', DONE: 'Done' }
      return map[status] || status
    }

    return { recentlyAdded, handleAddTask, statusLabel }
  }
}
</script>

<style scoped>
.addContainer {
  max-width: 680px;
  margin: 0 auto;
  padding: 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 28px;
}

.addHeader {
  display: flex;
  align-items: flex-start;
  gap: 16px;
}

.backBtn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 10px;
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  color: var(--text, #1e293b);
  text-decoration: none;
  transition: all 0.2s;
  flex-shrink: 0;
  margin-top: 4px;
}
.backBtn:hover {
  border-color: var(--primary, #2647e8);
  color: var(--primary, #2647e8);
  transform: translateX(-2px);
}
.backBtn svg { width: 18px; height: 18px; }

.heading {
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--primary, #2647e8);
  font-family: 'Segoe UI', sans-serif;
  margin-bottom: 4px;
}

.subheading {
  color: var(--muted, #64748b);
  font-size: 0.95rem;
}

.formCard {
  background: transparent;
}

/* Recently added */
.recentSection {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 12px;
  padding: 20px;
}

.recentHeading {
  font-size: 0.95rem;
  font-weight: 700;
  color: var(--muted, #64748b);
  margin-bottom: 14px;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.recentList {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.recentItem {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 14px;
  background: var(--detail-bg, #f8fafc);
  border-radius: 8px;
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-6px); }
  to   { opacity: 1; transform: translateY(0); }
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;
}
.pri-high   { background: #ef4444; }
.pri-medium { background: #f59e0b; }
.pri-low    { background: #10b981; }

.recentTitle {
  flex: 1;
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text, #1e293b);
}

.recentStatus {
  font-size: 0.78rem;
  color: var(--muted, #64748b);
  font-weight: 500;
}
</style>

<template>
  <div class="taskListContainer">
    <h2 class="heading">My Tasks</h2>

    <div class="filterTabs">
      <router-link to="/dashboard" class="tab" active-class="tabActive" exact-active-class="tabActive">
        All
      </router-link>
      <router-link to="/dashboard/todo" class="tab" active-class="tabActive">
        To Do
      </router-link>
      <router-link to="/dashboard/completed" class="tab" active-class="tabActive">
        Completed
      </router-link>
    </div>

    <div class="searchWrap">
      <label for="taskSearch" class="searchLabel">Search tasks</label>
      <input
        id="taskSearch"
        v-model="searchQuery"
        type="search"
        placeholder="Type to filter by name or description..."
        class="searchInput"
        autocomplete="off"
        aria-describedby="searchHint"
      />
      <span id="searchHint" class="searchHint">Press / to focus search</span>
    </div>

    <TaskInput @add-task="handleAddTask" />

    <div v-if="filteredDisplayTasks.length === 0" class="emptyState">
      <p>📋 {{ searchQuery ? 'No tasks match your search.' : `No tasks in this view. ${emptyMessage}` }}</p>
    </div>

    <div v-else class="tasksList">
      <TransitionGroup name="task" tag="ul" class="taskListGroup">
        <li
          v-for="(task, index) in filteredDisplayTasks"
          :key="taskKey(task, index)"
          class="taskListItem"
        >
          <TaskCard
            :task="task"
            :stagger-index="index"
            @complete="handleComplete(task)"
            @delete="handleDelete(task)"
          />
        </li>
      </TransitionGroup>
    </div>

    <div v-if="tasks.length > 0" class="taskStats">
      <div class="progressWrap">
        <div class="progressBar">
          <div
            class="progressFill"
            :style="{ width: completionRate + '%' }"
          />
        </div>
        <span class="progressLabel">{{ completionRate }}% complete</span>
      </div>
      <div class="statGrid">
        <div class="statCard">
          <span class="statLabel">Total</span>
          <span class="statValue">{{ tasks.length }}</span>
        </div>
        <div class="statCard">
          <span class="statLabel">Done</span>
          <span class="statValue">{{ completedCount }}</span>
        </div>
        <div class="statCard">
          <span class="statLabel">Left</span>
          <span class="statValue">{{ tasks.length - completedCount }}</span>
        </div>
      </div>
    </div>

    <div v-if="hasCompletedTasks" class="clearSection">
      <button type="button" class="clearButton" @click="handleClearCompleted">
        Clear completed tasks
      </button>
    </div>
  </div>
</template>

<script>
import { computed, ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { useTasksStore } from '../stores/tasks'
import { useToastStore } from '../stores/toast'
import TaskInput from '../components/TaskInput.vue'
import TaskCard from '../components/TaskCard.vue'

export default {
  name: 'DashboardView',
  components: { TaskInput, TaskCard },
  setup() {
    const route = useRoute()
    const store = useTasksStore()
    const toast = useToastStore()
    const searchQuery = ref('')

    const filter = computed(() => route.params.filter || 'all')
    const tasks = computed(() => store.tasks)
    const completedCount = computed(() => store.completedCount)
    const hasCompletedTasks = computed(() => store.hasCompletedTasks)

    const displayTasks = computed(() => {
      if (filter.value === 'completed') return store.completedTasks
      if (filter.value === 'todo') return store.todoTasks
      return store.tasks
    })

    const filteredDisplayTasks = computed(() => {
      const q = (searchQuery.value || '').trim().toLowerCase()
      if (!q) return displayTasks.value
      return displayTasks.value.filter(
        t =>
          (t.name && t.name.toLowerCase().includes(q)) ||
          (t.description && t.description.toLowerCase().includes(q))
      )
    })

    const completionRate = computed(() => {
      const total = tasks.value.length
      return total === 0 ? 0 : Math.round((completedCount.value / total) * 100)
    })

    const emptyMessage = computed(() => {
      if (filter.value === 'completed') return 'Complete some tasks to see them here.'
      if (filter.value === 'todo') return 'Add a task or mark some as incomplete.'
      return 'Add one to get started!'
    })

    function taskKey(task, index) {
      return task.createdAt ? `${task.createdAt}-${index}` : `task-${index}`
    }

    function handleAddTask(taskData) {
      store.addTask(taskData)
      toast.success(`Added "${taskData.name}"`)
    }

    function handleComplete(task) {
      const idx = store.getIndex(task)
      if (idx !== -1) {
        store.toggleComplete(idx)
        toast.success(task.completed ? 'Marked incomplete' : 'Task completed!')
      }
    }

    function handleDelete(task) {
      const name = task.name
      const idx = store.getIndex(task)
      if (idx !== -1) {
        store.removeTask(idx)
        toast.info(`Removed "${name}"`)
      }
    }

    function handleClearCompleted() {
      const count = store.completedCount
      store.clearCompleted()
      toast.info(`Cleared ${count} completed task${count !== 1 ? 's' : ''}`)
    }

    function onKey(e) {
      if (e.key === '/' && document.activeElement?.id !== 'taskSearch') {
        e.preventDefault()
        document.getElementById('taskSearch')?.focus()
      }
    }
    onMounted(() => {
      document.addEventListener('keydown', onKey)
    })
    onBeforeUnmount(() => {
      document.removeEventListener('keydown', onKey)
    })

    return {
      searchQuery,
      tasks,
      completedCount,
      hasCompletedTasks,
      filteredDisplayTasks,
      completionRate,
      emptyMessage,
      taskKey,
      handleAddTask,
      handleComplete,
      handleDelete,
      handleClearCompleted
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
  color: var(--primary, #2647e8);
  margin-bottom: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.filterTabs {
  display: flex;
  gap: 8px;
  margin-bottom: 20px;
}

.tab {
  padding: 10px 20px;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
  color: var(--muted, #64748b);
  background: var(--card-bg, #f8fafc);
  transition: all 0.2s;
}

.tab:hover {
  color: var(--primary, #2647e8);
  background: var(--badge-bg, #dbeafe);
  transform: translateY(-1px);
}

.tabActive {
  color: white;
  background: var(--primary, #2647e8);
  transform: translateY(-1px);
}

.searchWrap {
  margin-bottom: 20px;
}

.searchLabel {
  display: block;
  font-weight: 600;
  color: var(--muted, #64748b);
  font-size: 0.9rem;
  margin-bottom: 6px;
}

.searchInput {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 10px;
  font-size: 1rem;
  background: var(--input-bg, white);
  color: var(--text, #1e293b);
  transition: border-color 0.2s, box-shadow 0.2s;
}

.searchInput:focus {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 0 0 3px rgba(38, 71, 232, 0.15);
}

.searchInput::placeholder {
  color: var(--muted, #94a3b8);
}

.searchHint {
  display: block;
  font-size: 0.8rem;
  color: var(--muted, #64748b);
  margin-top: 6px;
}

.tasksList {
  margin-bottom: 24px;
}

.taskListGroup {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
  position: relative;
}

.taskListItem {
  transition: transform 0.35s ease;
}

.emptyState {
  text-align: center;
  padding: 60px 40px;
  background-color: var(--detail-bg, #f8fafc);
  border-radius: 12px;
  color: var(--muted, #64748b);
  font-size: 1.2rem;
  border: 2px dashed var(--card-border, #cbd5e1);
}

/* List transitions */
.task-enter-active {
  transition: all 0.4s ease-out;
}

.task-leave-active {
  transition: all 0.3s ease-in;
  position: absolute;
  width: 100%;
}

.task-enter-from {
  opacity: 0;
  transform: translateX(-24px);
}

.task-leave-to {
  opacity: 0;
  transform: translateX(24px);
}

.task-move {
  transition: transform 0.4s ease;
}

.taskStats {
  margin-top: 32px;
  padding: 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.35);
}

.progressWrap {
  margin-bottom: 20px;
}

.progressBar {
  height: 12px;
  background: rgba(255, 255, 255, 0.3);
  border-radius: 999px;
  overflow: hidden;
  margin-bottom: 8px;
}

.progressFill {
  height: 100%;
  background: linear-gradient(90deg, #4ade80, #22c55e);
  border-radius: 999px;
  transition: width 0.6s ease-out;
}

.progressLabel {
  font-size: 0.9rem;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.95);
}

.statGrid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}

.statCard {
  background: rgba(255, 255, 255, 0.95);
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  transition: transform 0.2s;
}

.statCard:hover {
  transform: scale(1.03);
}

.statLabel {
  color: var(--muted, #64748b);
  font-weight: 600;
  font-size: 0.85rem;
  margin-bottom: 4px;
}

.statValue {
  color: var(--primary, #2647e8);
  font-weight: 700;
  font-size: 1.75rem;
}

.clearSection {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

.clearButton {
  padding: 10px 20px;
  background: var(--danger-bg, #fef2f2);
  color: #b91c1c;
  border: 2px solid #fecaca;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.clearButton:hover {
  background: #ef4444;
  color: white;
  border-color: #ef4444;
  transform: translateY(-2px);
}

.clearButton:focus-visible {
  outline: 2px solid #b91c1c;
  outline-offset: 2px;
}
</style>

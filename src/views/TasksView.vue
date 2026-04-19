<template>
  <div class="tasksContainer">
    <div class="pageHeader">
      <h2 class="heading">All Tasks</h2>
      <router-link to="/add-task" class="addBtn">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        New Task
      </router-link>
    </div>

    <!-- Filters row -->
    <div class="filtersRow">
      <!-- Status tabs -->
      <div class="statusTabs" role="tablist" aria-label="Filter by status">
        <button
          v-for="s in statusOptions"
          :key="s.value"
          class="statusTab"
          :class="{ active: activeStatus === s.value }"
          role="tab"
          :aria-selected="activeStatus === s.value"
          @click="activeStatus = s.value"
        >
          {{ s.label }}
          <span class="tabCount">{{ countFor(s.value) }}</span>
        </button>
      </div>

      <!-- Priority filter -->
      <div class="priorityFilter">
        <label for="priFilter" class="filterLabel">Priority</label>
        <select id="priFilter" v-model="activePriority" class="priSelect">
          <option value="ALL">All</option>
          <option value="HIGH">HIGH</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="LOW">LOW</option>
        </select>
      </div>
    </div>

    <!-- Search -->
    <div class="searchWrap">
      <div class="searchBox">
        <svg class="searchIcon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
        <input
          id="taskSearch"
          v-model="searchQuery"
          type="search"
          placeholder="Search by title or description…"
          class="searchInput"
          autocomplete="off"
          aria-label="Search tasks"
        />
        <button v-if="searchQuery" type="button" class="clearSearch" aria-label="Clear search" @click="searchQuery = ''">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M18 6 6 18M6 6l12 12"/></svg>
        </button>
      </div>
    </div>

    <!-- Loading -->
    <div v-if="loading" class="statusMsg" aria-live="polite">
      <span class="spinner" aria-hidden="true" /> Loading tasks…
    </div>

    <!-- Error -->
    <div v-else-if="error" class="errorBanner" role="alert">
      ⚠️ {{ error }}
      <button type="button" class="retryBtn" @click="store.fetchTasks()">Retry</button>
    </div>

    <!-- Results info -->
    <div v-else class="resultsInfo">
      <span>{{ filteredTasks.length }} task{{ filteredTasks.length !== 1 ? 's' : '' }}</span>
      <button v-if="hasActiveFilters" type="button" class="clearFilters" @click="clearFilters">
        Clear filters
      </button>
    </div>

    <!-- Empty -->
    <div v-if="!loading && !error && filteredTasks.length === 0" class="emptyState">
      <div class="emptyIcon">📋</div>
      <p>{{ emptyMessage }}</p>
      <router-link v-if="!hasActiveFilters" to="/add-task" class="emptyLink">Add your first task →</router-link>
    </div>

    <!-- Task list -->
    <div v-else-if="!loading && !error" class="tasksList">
      <TransitionGroup name="task" tag="ul" class="taskListGroup">
        <li v-for="task in filteredTasks" :key="task.id" class="taskListItem">
          <TaskCard
            :task="task"
            :updating="updatingId === task.id"
            :deleting="deletingId === task.id"
            @update-status="s => handleUpdateStatus(task, s)"
            @delete="handleDelete(task)"
          />
        </li>
      </TransitionGroup>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { useTasksStore } from '../stores/tasks'
import { useToastStore } from '../stores/toast'
import TaskCard from '../components/TaskCard.vue'

export default {
  name: 'TasksView',
  components: { TaskCard },
  setup() {
    const store = useTasksStore()
    const toast = useToastStore()

    const searchQuery   = ref('')
    const activeStatus  = ref('ALL')
    const activePriority = ref('ALL')
    const updatingId    = ref(null)
    const deletingId    = ref(null)

    const loading = computed(() => store.loading)
    const error   = computed(() => store.error)

    const statusOptions = [
      { value: 'ALL',        label: 'All'         },
      { value: 'TODO',       label: 'To Do'        },
      { value: 'IN_PROGRESS',label: 'In Progress'  },
      { value: 'DONE',       label: 'Done'         }
    ]

    function countFor(status) {
      if (status === 'ALL') return store.tasks.length
      return store.tasks.filter(t => t.status === status).length
    }

    const hasActiveFilters = computed(() =>
      activeStatus.value !== 'ALL' || activePriority.value !== 'ALL' || searchQuery.value.trim() !== ''
    )

    const filteredTasks = computed(() => {
      let list = store.tasks

      if (activeStatus.value !== 'ALL') {
        list = list.filter(t => t.status === activeStatus.value)
      }
      if (activePriority.value !== 'ALL') {
        list = list.filter(t => t.priority === activePriority.value)
      }
      const q = searchQuery.value.trim().toLowerCase()
      if (q) {
        list = list.filter(t =>
          (t.title       && t.title.toLowerCase().includes(q)) ||
          (t.description && t.description.toLowerCase().includes(q))
        )
      }
      return list
    })

    const emptyMessage = computed(() => {
      if (hasActiveFilters.value) return 'No tasks match your filters.'
      return 'No tasks yet.'
    })

    function clearFilters() {
      activeStatus.value   = 'ALL'
      activePriority.value = 'ALL'
      searchQuery.value    = ''
    }

    async function handleUpdateStatus(task, newStatus) {
      if (task.status === newStatus) return
      updatingId.value = task.id
      try {
        await store.editTask(task.id, { ...task, status: newStatus })
        toast.success(`Status updated to ${newStatus}`)
      } catch {
        toast.error('Failed to update task.')
      } finally {
        updatingId.value = null
      }
    }

    async function handleDelete(task) {
      deletingId.value = task.id
      try {
        await store.removeTask(task.id)
        toast.info(`Deleted "${task.title}"`)
      } catch {
        toast.error('Failed to delete task.')
      } finally {
        deletingId.value = null
      }
    }

    function onKey(e) {
      if (e.key === '/' && document.activeElement?.id !== 'taskSearch') {
        e.preventDefault()
        document.getElementById('taskSearch')?.focus()
      }
    }

    onMounted(() => {
      if (store.tasks.length === 0) store.fetchTasks()
      document.addEventListener('keydown', onKey)
    })
    onBeforeUnmount(() => document.removeEventListener('keydown', onKey))

    return {
      store, searchQuery, activeStatus, activePriority,
      loading, error, statusOptions, filteredTasks, hasActiveFilters,
      emptyMessage, updatingId, deletingId,
      countFor, clearFilters, handleUpdateStatus, handleDelete
    }
  }
}
</script>

<style scoped>
.tasksContainer {
  max-width: 900px;
  margin: 0 auto;
  padding: 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.pageHeader {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.heading {
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--primary, #2647e8);
  font-family: 'Segoe UI', sans-serif;
}

.addBtn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 9px 18px;
  background: var(--primary, #2647e8);
  color: white;
  border-radius: 10px;
  text-decoration: none;
  font-weight: 600;
  font-size: 0.9rem;
  transition: all 0.2s;
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.2);
}
.addBtn:hover { opacity: 0.9; transform: translateY(-1px); }
.addBtn svg   { width: 16px; height: 16px; }

/* Filters row */
.filtersRow {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

/* Status tabs */
.statusTabs {
  display: flex;
  gap: 4px;
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 10px;
  padding: 4px;
}

.statusTab {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 7px 14px;
  border-radius: 7px;
  border: none;
  background: transparent;
  color: var(--muted, #64748b);
  font-weight: 600;
  font-size: 0.85rem;
  cursor: pointer;
  transition: all 0.2s;
  font-family: inherit;
}
.statusTab:hover   { color: var(--primary, #2647e8); background: var(--badge-bg, #dbeafe); }
.statusTab.active  { background: var(--primary, #2647e8); color: white; }

.tabCount {
  background: rgba(255,255,255,0.25);
  padding: 1px 7px;
  border-radius: 999px;
  font-size: 0.75rem;
  font-weight: 700;
  line-height: 1.4;
}

.statusTab:not(.active) .tabCount {
  background: var(--detail-bg, #f1f5f9);
  color: var(--muted, #64748b);
}

/* Priority filter */
.priorityFilter {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filterLabel {
  font-size: 0.85rem;
  font-weight: 600;
  color: var(--muted, #64748b);
  white-space: nowrap;
}

.priSelect {
  padding: 8px 12px;
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 8px;
  font-size: 0.85rem;
  font-family: inherit;
  background: var(--input-bg, white);
  color: var(--text, #1e293b);
  cursor: pointer;
  transition: border-color 0.2s;
}
.priSelect:focus { outline: none; border-color: var(--primary, #2647e8); }

/* Search */
.searchWrap { position: relative; }

.searchBox {
  position: relative;
  display: flex;
  align-items: center;
}

.searchIcon {
  position: absolute;
  left: 14px;
  width: 18px;
  height: 18px;
  color: var(--muted, #94a3b8);
  pointer-events: none;
}

.searchInput {
  width: 100%;
  padding: 12px 42px;
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 10px;
  font-size: 0.95rem;
  background: var(--input-bg, white);
  color: var(--text, #1e293b);
  font-family: inherit;
  transition: border-color 0.2s, box-shadow 0.2s;
}
.searchInput:focus {
  outline: none;
  border-color: var(--primary, #2647e8);
  box-shadow: 0 0 0 3px rgba(38, 71, 232, 0.1);
}

.clearSearch {
  position: absolute;
  right: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  border: none;
  background: var(--muted, #94a3b8);
  color: white;
  border-radius: 50%;
  cursor: pointer;
  transition: background 0.2s;
  padding: 0;
}
.clearSearch:hover { background: var(--text, #64748b); }
.clearSearch svg   { width: 12px; height: 12px; }

/* Status messages */
.statusMsg {
  display: flex;
  align-items: center;
  gap: 10px;
  color: var(--muted, #64748b);
  font-size: 0.95rem;
  padding: 10px 0;
}

.spinner {
  display: inline-block;
  width: 16px;
  height: 16px;
  border: 2.5px solid var(--card-border, #e2e8f0);
  border-top-color: var(--primary, #2647e8);
  border-radius: 50%;
  animation: spin 0.7s linear infinite;
  flex-shrink: 0;
}
@keyframes spin { to { transform: rotate(360deg); } }

.errorBanner {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px 20px;
  background: var(--danger-bg, #fef2f2);
  border: 2px solid #fecaca;
  border-radius: 10px;
  color: #b91c1c;
  font-weight: 500;
}

.retryBtn {
  margin-left: auto;
  padding: 6px 14px;
  border: 2px solid #b91c1c;
  border-radius: 6px;
  background: transparent;
  color: #b91c1c;
  font-weight: 600;
  cursor: pointer;
  font-size: 0.85rem;
  font-family: inherit;
  transition: all 0.2s;
}
.retryBtn:hover { background: #b91c1c; color: white; }

/* Results info */
.resultsInfo {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 0.85rem;
  color: var(--muted, #64748b);
}

.clearFilters {
  border: none;
  background: none;
  color: var(--primary, #2647e8);
  font-weight: 600;
  font-size: 0.85rem;
  cursor: pointer;
  font-family: inherit;
  padding: 0;
  text-decoration: underline;
}

/* Empty */
.emptyState {
  text-align: center;
  padding: 60px 20px;
  background: var(--detail-bg, #f8fafc);
  border-radius: 14px;
  border: 2px dashed var(--card-border, #cbd5e1);
}

.emptyIcon { font-size: 3rem; margin-bottom: 12px; }

.emptyState p {
  color: var(--muted, #64748b);
  font-size: 1.05rem;
  margin-bottom: 16px;
}

.emptyLink {
  color: var(--primary, #2647e8);
  font-weight: 600;
  text-decoration: none;
}
.emptyLink:hover { text-decoration: underline; }

/* Task list */
.tasksList { }

.taskListGroup {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
  position: relative;
}

.taskListItem { transition: transform 0.3s; }

.task-enter-active { transition: all 0.35s ease-out; }
.task-leave-active { transition: all 0.25s ease-in; position: absolute; width: 100%; }
.task-enter-from   { opacity: 0; transform: translateX(-18px); }
.task-leave-to     { opacity: 0; transform: translateX(18px); }
.task-move         { transition: transform 0.35s ease; }

@media (max-width: 600px) {
  .filtersRow  { flex-direction: column; align-items: stretch; }
  .statusTabs  { overflow-x: auto; }
}
</style>

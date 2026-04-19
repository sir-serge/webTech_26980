<template>
  <article
    class="taskCard"
    :class="statusClass"
    role="listitem"
  >
    <div class="taskHeader">
      <div class="taskMeta">
        <span class="priorityBadge" :class="priorityClass">{{ task.priority }}</span>
        <span class="statusBadge" :class="statusBadgeClass">{{ statusLabel }}</span>
      </div>
    </div>

    <h3 class="taskTitle">{{ task.title }}</h3>

    <p v-if="task.description" class="taskDescription">{{ task.description }}</p>

    <div class="taskActions">
      <select
        class="statusSelect"
        :value="task.status"
        :aria-label="`Change status of ${task.title}`"
        @change="onStatusChange"
        :disabled="updating"
      >
        <option value="TODO">TODO</option>
        <option value="IN_PROGRESS">IN_PROGRESS</option>
        <option value="DONE">DONE</option>
      </select>

      <button
        type="button"
        class="deleteButton"
        :aria-label="`Delete task: ${task.title}`"
        :disabled="deleting"
        @click="$emit('delete')"
      >
        {{ deleting ? '...' : 'Delete' }}
      </button>
    </div>
  </article>
</template>

<script>
export default {
  name: 'TaskCard',
  props: {
    task: { type: Object, required: true },
    updating: { type: Boolean, default: false },
    deleting: { type: Boolean, default: false }
  },
  emits: ['update-status', 'delete'],
  computed: {
    statusClass() {
      return `card-${(this.task.status || 'TODO').toLowerCase()}`
    },
    statusBadgeClass() {
      return `status-${(this.task.status || 'TODO').toLowerCase()}`
    },
    priorityClass() {
      return `priority-${(this.task.priority || 'LOW').toLowerCase()}`
    },
    statusLabel() {
      const map = { TODO: 'To Do', IN_PROGRESS: 'In Progress', DONE: 'Done' }
      return map[this.task.status] || this.task.status
    }
  },
  methods: {
    onStatusChange(e) {
      this.$emit('update-status', e.target.value)
    }
  }
}
</script>

<style scoped>
.taskCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 12px;
  padding: 18px;
  transition: transform 0.25s ease, box-shadow 0.25s ease, border-color 0.25s ease;
  animation: cardIn 0.35s ease-out backwards;
  border-left-width: 4px;
}

.taskCard:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

/* Left-border accent by status */
.card-todo        { border-left-color: #64748b; }
.card-in_progress { border-left-color: #f59e0b; }
.card-done        { border-left-color: #22c55e; background-color: var(--detail-bg, #f0fdf4); }

@keyframes cardIn {
  from { opacity: 0; transform: translateY(10px); }
  to   { opacity: 1; transform: translateY(0); }
}

.taskHeader {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}

.taskMeta {
  display: flex;
  gap: 8px;
  align-items: center;
}

/* Priority badges */
.priorityBadge {
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 700;
  color: white;
  letter-spacing: 0.03em;
}
.priority-low    { background: #10b981; }
.priority-medium { background: #f59e0b; }
.priority-high   { background: #ef4444; }

/* Status badges */
.statusBadge {
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}
.status-todo        { background: #e2e8f0; color: #475569; }
.status-in_progress { background: #fef3c7; color: #92400e; }
.status-done        { background: #dcfce7; color: #15803d; }

.taskTitle {
  font-size: 1.05rem;
  font-weight: 700;
  color: var(--text, #1e293b);
  margin-bottom: 8px;
  line-height: 1.4;
}

.card-done .taskTitle {
  text-decoration: line-through;
  opacity: 0.65;
}

.taskDescription {
  font-size: 0.9rem;
  color: var(--muted, #64748b);
  margin-bottom: 14px;
  line-height: 1.5;
}

.taskActions {
  display: flex;
  gap: 10px;
  align-items: center;
  justify-content: flex-end;
}

.statusSelect {
  padding: 7px 10px;
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 8px;
  font-size: 0.85rem;
  font-family: inherit;
  background: var(--input-bg, white);
  color: var(--text, #1e293b);
  cursor: pointer;
  transition: border-color 0.2s;
}

.statusSelect:focus {
  outline: none;
  border-color: var(--primary, #2647e8);
}

.statusSelect:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.deleteButton {
  padding: 7px 16px;
  background: #ef4444;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  font-size: 0.85rem;
  cursor: pointer;
  transition: all 0.2s;
}

.deleteButton:hover:not(:disabled) {
  background: #dc2626;
  transform: scale(1.04);
}

.deleteButton:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.deleteButton:focus-visible {
  outline: 2px solid #b91c1c;
  outline-offset: 2px;
}
</style>

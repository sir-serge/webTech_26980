<template>
  <article
    class="taskCard"
    :class="{ completed: task.completed }"
    :style="staggerStyle"
    role="listitem"
  >
    <div class="taskHeader">
      <div class="taskTitle">
        <label class="checkboxWrap">
          <input
            type="checkbox"
            :checked="task.completed"
            :aria-label="`Mark ${task.name} as ${task.completed ? 'incomplete' : 'complete'}`"
            class="checkbox"
            @change="$emit('complete')"
          />
          <span class="checkmark" aria-hidden="true" />
        </label>
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
      <button
        type="button"
        class="deleteButton"
        aria-label="Delete task"
        @click="$emit('delete')"
      >
        Delete
      </button>
    </div>
  </article>
</template>

<script>
export default {
  name: 'TaskCard',
  props: {
    task: {
      type: Object,
      required: true
    },
    staggerIndex: {
      type: Number,
      default: 0
    }
  },
  computed: {
    urgencyClass() {
      return `urgency-${(this.task.urgency || '').toLowerCase()}`
    },
    staggerStyle() {
      return {
        animationDelay: `${this.staggerIndex * 0.06}s`
      }
    }
  },
  methods: {
    formatDate(dateString) {
      if (!dateString) return '—'
      const options = { year: 'numeric', month: 'short', day: 'numeric' }
      return new Date(dateString).toLocaleDateString(undefined, options)
    }
  },
  emits: ['complete', 'delete']
}
</script>

<style scoped>
.taskCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 12px;
  padding: 16px;
  transition: transform 0.25s ease, box-shadow 0.25s ease, border-color 0.25s ease, background 0.3s;
  animation: cardIn 0.4s ease-out backwards;
}

.taskCard:hover {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 8px 24px rgba(38, 71, 232, 0.12);
  transform: translateY(-2px);
}

.taskCard.completed {
  background-color: var(--completed-bg, #f0fdf4);
  border-color: var(--completed-border, #bbf7d0);
}

.taskCard.completed .checkmark {
  background: #22c55e;
  border-color: #22c55e;
}

.taskCard.completed .checkmark::after {
  opacity: 1;
}

@keyframes cardIn {
  from {
    opacity: 0;
    transform: translateY(12px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
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

.checkboxWrap {
  position: relative;
  display: inline-flex;
  cursor: pointer;
  flex-shrink: 0;
}

.checkbox {
  position: absolute;
  opacity: 0;
  width: 22px;
  height: 22px;
  cursor: pointer;
  margin: 0;
}

.checkmark {
  width: 22px;
  height: 22px;
  border: 2px solid var(--card-border, #cbd5e1);
  border-radius: 6px;
  background: var(--card-bg, white);
  transition: all 0.25s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.checkmark::after {
  content: '';
  width: 6px;
  height: 11px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg) scale(0.8);
  opacity: 0;
  transition: opacity 0.2s ease;
  margin-bottom: 3px;
}

.checkbox:focus-visible + .checkmark {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}

.checkbox:checked + .checkmark {
  background: #22c55e;
  border-color: #22c55e;
}

.checkbox:checked + .checkmark::after {
  opacity: 1;
}

.taskName {
  font-size: 1.1rem;
  color: var(--text, #1e293b);
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

.urgency-low { background-color: #10b981; }
.urgency-medium { background-color: #f59e0b; }
.urgency-high { background-color: #ef4444; }
.urgency-critical { background-color: #8b5cf6; }

.taskType {
  background-color: var(--badge-bg, #dbeafe);
  color: var(--primary, #2647e8);
  padding: 4px 12px;
  border-radius: 8px;
  font-size: 0.85rem;
  font-weight: 600;
}

.taskDetails {
  background-color: var(--detail-bg, #f8fafc);
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

.detailItem:last-child { margin-bottom: 0; }

.label {
  font-weight: 600;
  color: var(--muted, #64748b);
  min-width: 100px;
}

.value { color: var(--text, #1e293b); }

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

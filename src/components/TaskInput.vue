<template>
  <form @submit.prevent="submitTask" class="taskForm" novalidate>
    <div class="formGroup">
      <label for="taskTitle">Task Title *</label>
      <input
        id="taskTitle"
        v-model="form.title"
        type="text"
        placeholder="Enter task title..."
        required
        autocomplete="off"
      />
      <span v-if="errors.title" class="errorText" role="alert">{{ errors.title }}</span>
    </div>

    <div class="formGroup">
      <label for="taskDescription">Description</label>
      <textarea
        id="taskDescription"
        v-model="form.description"
        placeholder="Add task details (optional)..."
        rows="3"
      />
    </div>

    <div class="formRow">
      <div class="formGroup">
        <label for="taskPriority">Priority *</label>
        <select id="taskPriority" v-model="form.priority" required>
          <option value="">Select priority</option>
          <option value="LOW">LOW</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="HIGH">HIGH</option>
        </select>
        <span v-if="errors.priority" class="errorText" role="alert">{{ errors.priority }}</span>
      </div>

      <div class="formGroup">
        <label for="taskStatus">Status *</label>
        <select id="taskStatus" v-model="form.status" required>
          <option value="TODO">TODO</option>
          <option value="IN_PROGRESS">IN_PROGRESS</option>
          <option value="DONE">DONE</option>
        </select>
      </div>
    </div>

    <button type="submit" class="addButton" :disabled="submitting">
      {{ submitting ? 'Adding...' : 'Add Task' }}
    </button>
  </form>
</template>

<script>
export default {
  name: 'TaskInput',
  emits: ['add-task'],
  data() {
    return {
      form: {
        title: '',
        description: '',
        priority: '',
        status: 'TODO'
      },
      errors: {},
      submitting: false
    }
  },
  methods: {
    validate() {
      const e = {}
      if (!this.form.title.trim())  e.title    = 'Title is required.'
      if (!this.form.priority)      e.priority = 'Priority is required.'
      this.errors = e
      return Object.keys(e).length === 0
    },
    async submitTask() {
      if (!this.validate()) return
      this.submitting = true
      try {
        await this.$emit('add-task', {
          title:       this.form.title.trim(),
          description: this.form.description.trim(),
          priority:    this.form.priority,
          status:      this.form.status
        })
        this.resetForm()
      } finally {
        this.submitting = false
      }
    },
    resetForm() {
      this.form   = { title: '', description: '', priority: '', status: 'TODO' }
      this.errors = {}
    }
  }
}
</script>

<style scoped>
.taskForm {
  background: var(--card-bg, white);
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  margin-bottom: 28px;
  border: 2px solid var(--card-border, #e2e8f0);
  transition: border-color 0.2s;
}

.taskForm:focus-within {
  border-color: var(--primary, #2647e8);
}

.formGroup {
  margin-bottom: 16px;
  display: flex;
  flex-direction: column;
}

.formRow {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
}

label {
  font-weight: 600;
  color: var(--primary, #2647e8);
  margin-bottom: 6px;
  font-size: 0.9rem;
}

input[type="text"],
select,
textarea {
  padding: 10px 12px;
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 8px;
  font-size: 0.95rem;
  background: var(--input-bg, white);
  color: var(--text, #1e293b);
  font-family: inherit;
  transition: border-color 0.2s, box-shadow 0.2s;
}

input[type="text"]:focus,
select:focus,
textarea:focus {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 0 0 3px rgba(38, 71, 232, 0.12);
  outline: none;
}

textarea {
  resize: vertical;
  min-height: 72px;
}

.errorText {
  margin-top: 4px;
  font-size: 0.82rem;
  color: #b91c1c;
}

.addButton {
  width: 100%;
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  background: var(--primary, #2647e8);
  color: white;
  transition: all 0.2s;
}

.addButton:hover:not(:disabled) {
  opacity: 0.9;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.25);
}

.addButton:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.addButton:focus-visible {
  outline: 2px solid white;
  outline-offset: 2px;
}
</style>

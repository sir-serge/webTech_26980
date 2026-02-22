<template>
  <form @submit.prevent="addTask" class="taskForm">
    <div class="formGroup">
      <label for="taskName">Task Name *</label>
      <input
        id="taskName"
        v-model="form.name"
        type="text"
        placeholder="Enter task name..."
        required
      />
    </div>

    <div class="formRow">
      <div class="formGroup">
        <label for="taskType">Task Type *</label>
        <select id="taskType" v-model="form.type" required>
          <option value="">Select type</option>
          <option value="Work">Work</option>
          <option value="Personal">Personal</option>
          <option value="Shopping">Shopping</option>
          <option value="Health">Health</option>
          <option value="Other">Other</option>
        </select>
      </div>

      <div class="formGroup">
        <label for="taskUrgency">Urgency *</label>
        <select id="taskUrgency" v-model="form.urgency" required>
          <option value="">Select urgency</option>
          <option value="Low">Low</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
          <option value="Critical">Critical</option>
        </select>
      </div>
    </div>

    <div class="formRow">
      <div class="formGroup">
        <label for="dueDate">Due Date *</label>
        <input
          id="dueDate"
          v-model="form.dueDate"
          type="date"
          required
        />
      </div>

      <div class="formGroup">
        <label for="dueTime">Due Time</label>
        <input
          id="dueTime"
          v-model="form.dueTime"
          type="time"
        />
      </div>
    </div>

    <div class="formGroup">
      <label for="taskDescription">Description</label>
      <textarea
        id="taskDescription"
        v-model="form.description"
        placeholder="Add task details (optional)..."
        rows="3"
      ></textarea>
    </div>

    <button type="submit" class="addButton">Add Task</button>
  </form>
</template>

<script>
export default {
  name: 'TaskInput',
  data() {
    return {
      form: {
        name: '',
        type: '',
        urgency: '',
        dueDate: '',
        dueTime: '',
        description: ''
      }
    }
  },
  methods: {
    addTask() {
      if (this.form.name.trim() && this.form.type && this.form.urgency && this.form.dueDate) {
        this.$emit('add-task', { ...this.form })
        this.resetForm()
      }
    },
    resetForm() {
      this.form = {
        name: '',
        type: '',
        urgency: '',
        dueDate: '',
        dueTime: '',
        description: ''
      }
    }
  },
  emits: ['add-task']
}
</script>

<style scoped>
.taskForm {
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
}

.formGroup {
  margin-bottom: 18px;
  display: flex;
  flex-direction: column;
}

.formRow {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 15px;
}

label {
  font-weight: 600;
  color: #2647e8;
  margin-bottom: 8px;
  font-size: 0.95rem;
}

input[type="text"],
input[type="date"],
input[type="time"],
select,
textarea {
  padding: 10px 12px;
  border: 2px solid #bbf7d0;
  border-radius: 8px;
  font-size: 0.95rem;
  background: white;
  transition: all 0.3s;
  font-family: inherit;
}

input[type="text"]:focus,
input[type="date"]:focus,
input[type="time"]:focus,
select:focus,
textarea:focus {
  outline: none;
  border-color: #7c3aed;
  box-shadow: 0 0 0 3px rgba(124, 58, 237, 0.1);
}

textarea {
  resize: vertical;
  min-height: 80px;
}

.addButton {
  width: 100%;
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  background-color: #7c3aed;
  color: white;
  transition: all 0.3s;
}

.addButton:hover {
  background-color: #6d28d9;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(124, 58, 237, 0.3);
}

.addButton:active {
  transform: translateY(0);
}
</style>

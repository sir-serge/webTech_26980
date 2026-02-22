import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useTasksStore = defineStore('tasks', () => {
  // State
  const tasks = ref([])

  // Getters
  const completedTasks = computed(() => tasks.value.filter(t => t.completed))
  const todoTasks = computed(() => tasks.value.filter(t => !t.completed))
  const completedCount = computed(() => completedTasks.value.length)
  const hasCompletedTasks = computed(() => completedCount.value > 0)

  const filteredTasks = computed(() => {
    return (filter) => {
      if (filter === 'completed') return completedTasks.value
      if (filter === 'todo') return todoTasks.value
      return tasks.value
    }
  })

  // Actions
  function addTask(taskData) {
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
    tasks.value.push(newTask)
  }

  function toggleComplete(index) {
    if (index >= 0 && index < tasks.value.length) {
      tasks.value[index].completed = !tasks.value[index].completed
    }
  }

  function removeTask(index) {
    if (index >= 0 && index < tasks.value.length) {
      tasks.value.splice(index, 1)
    }
  }

  function clearCompleted() {
    tasks.value = tasks.value.filter(t => !t.completed)
  }

  function getIndex(task) {
    return tasks.value.findIndex(t => t === task)
  }

  return {
    tasks,
    completedTasks,
    todoTasks,
    completedCount,
    hasCompletedTasks,
    filteredTasks,
    addTask,
    toggleComplete,
    removeTask,
    clearCompleted,
    getIndex
  }
})

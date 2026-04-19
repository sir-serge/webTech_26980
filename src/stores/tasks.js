import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getTasks, createTask, updateTask, deleteTask } from '../services/api'

export const useTasksStore = defineStore('tasks', () => {
  // State
  const tasks   = ref([])
  const loading = ref(false)
  const error   = ref(null)

  // Getters
  const todoTasks       = computed(() => tasks.value.filter(t => t.status === 'TODO'))
  const inProgressTasks = computed(() => tasks.value.filter(t => t.status === 'IN_PROGRESS'))
  const doneTasks       = computed(() => tasks.value.filter(t => t.status === 'DONE'))

  function filteredTasks(filter) {
    if (filter === 'TODO')        return todoTasks.value
    if (filter === 'IN_PROGRESS') return inProgressTasks.value
    if (filter === 'DONE')        return doneTasks.value
    return tasks.value
  }

  // Actions
  async function fetchTasks() {
    loading.value = true
    error.value   = null
    try {
      const res   = await getTasks()
      tasks.value = Array.isArray(res.data) ? res.data : []
    } catch (e) {
      error.value = e?.response?.data?.message || 'Failed to load tasks.'
    } finally {
      loading.value = false
    }
  }

  async function addTask(taskData) {
    const res = await createTask(taskData)
    tasks.value.push(res.data)
    return res.data
  }

  async function editTask(id, taskData) {
    const res = await updateTask(id, taskData)
    const idx = tasks.value.findIndex(t => t.id === id)
    if (idx !== -1) tasks.value[idx] = res.data
    return res.data
  }

  async function removeTask(id) {
    await deleteTask(id)
    tasks.value = tasks.value.filter(t => t.id !== id)
  }

  return {
    tasks,
    loading,
    error,
    todoTasks,
    inProgressTasks,
    doneTasks,
    filteredTasks,
    fetchTasks,
    addTask,
    editTask,
    removeTask
  }
})

import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useToastStore = defineStore('toast', () => {
  const toasts = ref([])
  let nextId = 1
  const timers = new Map()

  function add(message, type = 'success', duration = 3500) {
    const id = nextId++
    toasts.value.push({ id, message, type })
    if (duration > 0) {
      const timer = setTimeout(() => remove(id), duration)
      timers.set(id, timer)
    }
    return id
  }

  function remove(id) {
    const t = timers.get(id)
    if (t) {
      clearTimeout(t)
      timers.delete(id)
    }
    const index = toasts.value.findIndex(t => t.id === id)
    if (index !== -1) toasts.value.splice(index, 1)
  }

  function success(msg) {
    return add(msg, 'success')
  }

  function info(msg) {
    return add(msg, 'info')
  }

  function error(msg) {
    return add(msg, 'error', 5000)
  }

  return { toasts, add, remove, success, info, error }
})

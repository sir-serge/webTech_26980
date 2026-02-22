import { defineStore } from 'pinia'
import { ref, watch } from 'vue'

const STORAGE_KEY = 'taskbuddy_auth'

const defaultUser = {
  displayName: '',
  email: '',
  avatarUrl: '', // URL to profile picture; empty = use placeholder
  bio: ''
}

function loadFromStorage() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    if (raw) {
      const parsed = JSON.parse(raw)
      return { ...defaultUser, ...parsed }
    }
  } catch (_) {
    // ignore
  }
  return { ...defaultUser }
}

function saveToStorage(user) {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(user))
  } catch (_) {
    // ignore
  }
}

export const useAuthStore = defineStore('auth', () => {
  const user = ref(loadFromStorage())
  const isAuthenticated = ref(true) // For demo; set false when implementing real JWT login

  watch(user, (val) => {
    saveToStorage(val)
  }, { deep: true, immediate: true })

  function updateProfile(updates) {
    user.value = { ...user.value, ...updates }
  }

  function setBio(value) {
    user.value = { ...user.value, bio: value }
  }

  function setAvatar(url) {
    user.value = { ...user.value, avatarUrl: url || '' }
  }

  return {
    user,
    isAuthenticated,
    updateProfile,
    setBio,
    setAvatar
  }
})

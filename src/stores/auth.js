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
  const isAuthenticated = ref(false)

  watch(user, (val) => {
    saveToStorage(val)
  }, { deep: true, immediate: true })

  function login(payload = {}) {
    isAuthenticated.value = true
    user.value = { ...user.value, ...payload }
  }

  function logout() {
    isAuthenticated.value = false
    user.value = { ...defaultUser }
  }

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
    login,
    logout,
    updateProfile,
    setBio,
    setAvatar
  }
})

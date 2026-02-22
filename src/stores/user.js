import { defineStore } from 'pinia'
import { ref, watch } from 'vue'

const BIO_KEY = 'taskbuddy_user_bio'

export const useUserStore = defineStore('user', () => {
  const bio = ref(localStorage.getItem(BIO_KEY) || '')

  watch(bio, (val) => {
    localStorage.setItem(BIO_KEY, val)
  }, { immediate: true })

  function setBio(value) {
    bio.value = value
  }

  return { bio, setBio }
})

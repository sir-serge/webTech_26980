<template>
  <div class="authContainer">
    <h2 class="heading">Log in</h2>
    <p class="subtitle">Welcome back! Enter your details to access TaskBuddy.</p>

    <form class="authForm" @submit.prevent="handleLogin" novalidate>
      <div class="formGroup">
        <label for="loginEmail">Email</label>
        <input
          id="loginEmail"
          v-model="email"
          type="email"
          required
          autocomplete="email"
          placeholder="you@example.com"
        />
      </div>

      <div class="formGroup">
        <label for="loginPassword">Password</label>
        <input
          id="loginPassword"
          v-model="password"
          type="password"
          required
          autocomplete="current-password"
          placeholder="••••••••"
        />
      </div>

      <button type="submit" class="authButton">Log in</button>
    </form>

    <p class="authFooter">
      Don't have an account?
      <router-link to="/register" class="authLink">Sign up</router-link>
    </p>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'

export default {
  name: 'LoginView',
  setup() {
    const toast = useToastStore()
    const router = useRouter()
    const auth = useAuthStore()
    const email = ref('')
    const password = ref('')

    function handleLogin() {
      if (!email.value || !password.value) return
      auth.login({ email: email.value })
      toast.info('Logged in (demo mode).')
      router.push({ name: 'Dashboard' })
    }

    return { email, password, handleLogin }
  }
}
</script>

<style scoped>
.authContainer {
  max-width: 420px;
  margin: 0 auto;
  padding: 24px 20px;
}

.heading {
  font-size: 2rem;
  color: var(--primary, #2647e8);
  margin-bottom: 8px;
}

.subtitle {
  color: var(--muted, #64748b);
  margin-bottom: 24px;
}

.authForm {
  background: var(--card-bg, #fff);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 12px;
  padding: 24px 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
}

.formGroup {
  margin-bottom: 18px;
}

label {
  display: block;
  margin-bottom: 6px;
  font-weight: 600;
  color: var(--primary, #2647e8);
}

input {
  width: 100%;
  padding: 10px 12px;
  border-radius: 8px;
  border: 2px solid var(--card-border, #e2e8f0);
  font-size: 0.95rem;
  background: var(--input-bg, #fff);
  color: var(--text, #1e293b);
}

input:focus {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 0 0 3px rgba(38, 71, 232, 0.18);
}

.authButton {
  width: 100%;
  padding: 12px 18px;
  border-radius: 10px;
  border: none;
  background: var(--primary, #2647e8);
  color: #fff;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.authButton:hover {
  opacity: 0.95;
  transform: translateY(-1px);
}

.authFooter {
  text-align: center;
  margin-top: 24px;
  color: var(--muted, #64748b);
  font-size: 0.95rem;
}

.authLink {
  color: var(--primary, #2647e8);
  font-weight: 600;
  text-decoration: none;
  margin-left: 4px;
}

.authLink:hover {
  text-decoration: underline;
}
</style>


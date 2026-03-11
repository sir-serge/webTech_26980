<template>
  <div id="app">
    <a href="#main-content" class="skipLink">Skip to main content</a>
    <header class="appHeader">
      <h1 id="logo">TaskBuddy</h1>
      <div class="headerRight" v-if="isAuthenticated">
        <ProfileHeader />
      </div>
      <button
        type="button"
        class="themeToggle"
        :aria-label="isDark ? 'Switch to light mode' : 'Switch to dark mode'"
        :title="isDark ? 'Light mode' : 'Dark mode'"
        @click="toggleDark"
      >
        <span class="themeIcon" :class="{ dark: isDark }">{{ isDark ? '☀️' : '🌙' }}</span>
      </button>
    </header>

    <NavBar />

    <main id="main-content" class="appMain" role="main" tabindex="-1">
      <router-view v-slot="{ Component }">
        <Transition name="page" mode="out-in">
          <component :is="Component" />
        </Transition>
      </router-view>
    </main>

    <ToastList />
  </div>
</template>

<script>
import { storeToRefs } from 'pinia'
import NavBar from './components/Navigation.vue'
import ToastList from './components/ToastList.vue'
import ProfileHeader from './components/ProfileHeader.vue'
import { useThemeStore } from './stores/theme'
import { useAuthStore } from './stores/auth'

export default {
  name: 'App',
  components: { NavBar, ToastList, ProfileHeader },
  setup() {
    const theme = useThemeStore()
    const auth = useAuthStore()
    const { isDark } = storeToRefs(theme)
    const { isAuthenticated } = storeToRefs(auth)
    return { isDark, isAuthenticated, toggleDark: theme.toggleDark }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

:root {
  --primary: #2647e8;
  --text: #1e293b;
  --muted: #64748b;
  --card-bg: #ffffff;
  --card-border: #e2e8f0;
  --detail-bg: #f8fafc;
  --badge-bg: #dbeafe;
  --input-bg: #ffffff;
  --danger-bg: #fef2f2;
}

html.dark {
  --primary: #818cf8;
  --text: #e2e8f0;
  --muted: #94a3b8;
  --card-bg: #1e3a5f;
  --card-border: #334155;
  --detail-bg: #2c5282;
  --badge-bg: #334155;
  --input-bg: #1e3a5f;
  --danger-bg: #450a0a;
}

body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  background-color: #ffffff;
  color: var(--text);
}

html.dark body {
  background: linear-gradient(135deg, #1e3a5f 0%, #2c5282 100%);
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

/* Enhanced transitions and micro-interactions */
.appHeader {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  background: var(--card-bg);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  gap: 20px;
  transition: all 0.3s ease;
}

.appHeader:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.headerLeft {
  flex: 0 0 auto;
}

.headerRight {
  flex: 0 0 auto;
  order: 3;
}

#logo {
  font-size: 1.8rem;
  color: var(--primary);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 700;
  text-align: center;
  flex: 1;
  margin: 0;
  transition: all 0.3s ease;
  cursor: pointer;
}

#logo:hover {
  transform: scale(1.03);
  filter: brightness(1.1);
}

.themeToggle {
  flex: 0 0 auto;
  width: 36px;
  height: 36px;
  border: none;
  border-radius: 10px;
  background: var(--detail-bg);
  font-size: 1.2rem;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  order: 4;
}

.themeToggle::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  background: var(--badge-bg);
  border-radius: 50%;
  transform: translate(-50%, -50%);
  transition: all 0.3s ease;
}

.themeToggle:hover::before {
  width: 100%;
  height: 100%;
}

.themeToggle:hover {
  transform: translateY(-2px) scale(1.05);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.skipLink {
  position: absolute;
  left: -9999px;
  z-index: 999;
  padding: 12px 16px;
  background: var(--primary);
  color: white;
  font-weight: 600;
  text-decoration: none;
  border-radius: 0 0 8px 0;
  transition: all 0.2s ease;
}

.skipLink:focus {
  left: 0;
  outline: 2px solid white;
  outline-offset: 2px;
  transform: translateX(0);
}

.appMain {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
  padding-bottom: 40px;
  transition: all 0.3s ease;
}

.appMain:focus {
  outline: none;
}

.themeToggle:focus-visible {
  outline: 2px solid var(--primary);
  outline-offset: 2px;
}

.themeIcon {
  display: inline-block;
  transition: all 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
  position: relative;
  z-index: 2;
}

.themeToggle:hover .themeIcon {
  transform: scale(1.2) rotate(180deg);
}

.themeIcon.dark {
  animation: sunRotate 0.6s ease-in-out;
}

@keyframes sunRotate {
  0% { transform: rotate(0deg) scale(1); }
  50% { transform: rotate(180deg) scale(1.2); }
  100% { transform: rotate(360deg) scale(1); }
}

/* Route transition */
.page-enter-active,
.page-leave-active {
  transition: opacity 0.25s ease, transform 0.25s ease;
}

.page-enter-from {
  opacity: 0;
  transform: translateY(8px);
}

.page-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}
</style>

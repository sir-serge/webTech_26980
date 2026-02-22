<template>
  <div id="app">
    <a href="#main-content" class="skipLink">Skip to main content</a>
    <header class="appHeader">
      <h1 id="logo">TaskBuddy</h1>
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
import { useThemeStore } from './stores/theme'

export default {
  name: 'App',
  components: { NavBar, ToastList },
  setup() {
    const theme = useThemeStore()
    const { isDark } = storeToRefs(theme)
    return { isDark, toggleDark: theme.toggleDark }
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
  --card-bg: #1e293b;
  --card-border: #334155;
  --detail-bg: #0f172a;
  --badge-bg: #334155;
  --input-bg: #1e293b;
  --danger-bg: #450a0a;
}

body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  background-color: #ffffff;
  color: var(--text);
}

html.dark body {
  background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.appHeader {
  position: relative;
  text-align: center;
  padding: 30px 20px;
  background: var(--card-bg);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

#logo {
  font-size: 2.5rem;
  color: var(--primary);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 700;
}

.themeToggle {
  position: absolute;
  top: 50%;
  right: 20px;
  transform: translateY(-50%);
  width: 44px;
  height: 44px;
  border: none;
  border-radius: 8px;
  background: var(--detail-bg);
  font-size: 1.4rem;
  cursor: pointer;
  transition: all 0.2s;
}

.themeToggle:hover {
  background: var(--badge-bg);
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
}
.skipLink:focus {
  left: 0;
  outline: 2px solid white;
  outline-offset: 2px;
}

.appMain {
  max-width: 1200px;
  margin: 30px auto;
  padding: 0 20px;
  padding-bottom: 60px;
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
  transition: transform 0.3s ease;
}

.themeToggle:hover .themeIcon {
  transform: scale(1.15) rotate(12deg);
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

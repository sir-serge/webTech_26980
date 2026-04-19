<template>
  <nav class="navbar" aria-label="Main navigation" v-if="!isAuthPage">
    <router-link to="/dashboard" class="navLink" active-class="navLinkActive">
      <!-- Dashboard -->
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <rect x="3" y="3" width="7" height="7" rx="1"/>
        <rect x="14" y="3" width="7" height="7" rx="1"/>
        <rect x="14" y="14" width="7" height="7" rx="1"/>
        <rect x="3" y="14" width="7" height="7" rx="1"/>
      </svg>
      <span>Dashboard</span>
    </router-link>

    <router-link to="/add-task" class="navLink" active-class="navLinkActive">
      <!-- Add Task -->
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <circle cx="12" cy="12" r="10"/>
        <path d="M12 8v8M8 12h8"/>
      </svg>
      <span>Add Task</span>
    </router-link>

    <router-link to="/tasks" class="navLink" active-class="navLinkActive">
      <!-- All Tasks -->
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path d="M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2"/>
        <rect x="9" y="3" width="6" height="4" rx="1"/>
        <path d="M9 12h6M9 16h4"/>
      </svg>
      <span>All Tasks</span>
    </router-link>

    <router-link to="/profile" class="navLink" active-class="navLinkActive">
      <!-- Profile -->
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
        <circle cx="12" cy="7" r="4"/>
      </svg>
      <span>Profile</span>
    </router-link>
  </nav>
</template>

<script>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'NavBar',
  setup() {
    const route = useRoute()
    const isAuthPage = computed(() =>
      ['Login', 'Register', 'Home'].includes(route.name)
    )
    return { isAuthPage }
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  gap: 4px;
  padding: 8px 12px;
  background: var(--card-bg, white);
  border-bottom: 1px solid var(--card-border, #e2e8f0);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  overflow-x: auto;
}

.navLink {
  display: flex;
  align-items: center;
  gap: 7px;
  text-decoration: none;
  color: var(--muted, #64748b);
  font-weight: 500;
  padding: 9px 16px;
  border-radius: 9px;
  transition: all 0.2s ease;
  font-size: 0.9rem;
  white-space: nowrap;
}

.navLink:hover {
  background: var(--badge-bg, #dbeafe);
  color: var(--primary, #2647e8);
}

.navLink.navLinkActive,
.navLink.router-link-active {
  color: white;
  background: var(--primary, #2647e8);
  box-shadow: 0 3px 10px rgba(38, 71, 232, 0.25);
}

.navLink svg {
  width: 17px;
  height: 17px;
  flex-shrink: 0;
  transition: transform 0.2s;
}
.navLink:hover svg { transform: scale(1.1); }

.navLink:focus-visible {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}

@media (max-width: 520px) {
  .navLink span { display: none; }
  .navLink svg  { width: 20px; height: 20px; }
  .navLink { padding: 10px 14px; }
}
</style>

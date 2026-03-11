<template>
  <nav class="navbar" aria-label="Main navigation" v-if="!isAuthPage">
    <router-link to="/dashboard" class="navLink" active-class="navLinkActive">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <rect x="3" y="3" width="7" height="7"/>
        <rect x="14" y="3" width="7" height="7"/>
        <rect x="14" y="14" width="7" height="7"/>
        <rect x="3" y="14" width="7" height="7"/>
      </svg>
      Dashboard
    </router-link>
    <router-link to="/profile" class="navLink" active-class="navLinkActive">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
        <circle cx="12" cy="7" r="4"/>
      </svg>
      Profile
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
    
    const isAuthPage = computed(() => {
      return route.name === 'Login' || route.name === 'Register' || route.name === 'Home'
    })
    
    return {
      isAuthPage
    }
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  gap: 6px;
  margin-bottom: 20px;
  padding: 12px;
  background: var(--card-bg, white);
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  border: 1px solid var(--card-border, #e2e8f0);
}

.navLink {
  display: flex;
  align-items: center;
  gap: 6px;
  text-decoration: none;
  color: var(--muted, #64748b);
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 10px;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  font-size: 0.9rem;
}

.navLink::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--primary, #2647e8);
  opacity: 0;
  transition: opacity 0.3s ease;
  border-radius: 10px;
}

.navLink:hover {
  background: var(--badge-bg, #dbeafe);
  color: var(--primary, #2647e8);
  transform: translateY(-1px);
}

.navLink.router-link-active,
.navLink.navLinkActive {
  color: white;
  background: var(--primary, #2647e8);
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.3);
}

.navLink svg {
  width: 16px;
  height: 16px;
  flex-shrink: 0;
  transition: transform 0.2s ease;
}

.navLink:hover svg {
  transform: scale(1.1);
}

.navLink.router-link-active svg,
.navLink.navLinkActive svg {
  transform: scale(1.1);
}

.navLink:focus-visible {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}

/* Responsive design */
@media (max-width: 768px) {
  .navbar {
    justify-content: center;
    gap: 8px;
  }
  
  .navLink {
    padding: 8px 12px;
    font-size: 0.8rem;
  }
  
  .navLink span {
    display: none;
  }
  
  .navLink svg {
    width: 18px;
    height: 18px;
  }
}
</style>

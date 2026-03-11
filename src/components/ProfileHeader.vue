<template>
  <div class="profileHeader">
    <button
      class="profileButton"
      @click="toggleDropdown"
      aria-label="Profile menu"
      aria-expanded="isDropdownOpen"
      aria-haspopup="true"
    >
      <img
        :src="avatarDisplay"
        :alt="user.displayName ? `Profile picture of ${user.displayName}` : 'Profile picture'"
        class="avatar"
        width="40"
        height="40"
      />
      <span class="profileName">{{ user.displayName || 'Guest' }}</span>
      <svg class="dropdownIcon" :class="{ open: isDropdownOpen }" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path d="M6 9l6 6 6-6"/>
      </svg>
    </button>

    <Transition name="dropdown">
      <div v-if="isDropdownOpen" class="dropdownMenu">
        <div class="dropdownHeader">
          <img
            :src="avatarDisplay"
            :alt="user.displayName ? `Profile picture of ${user.displayName}` : 'Profile picture'"
            class="dropdownAvatar"
            width="48"
            height="48"
          />
          <div class="dropdownUserInfo">
            <div class="dropdownName">{{ user.displayName || 'Guest User' }}</div>
            <div class="dropdownEmail">{{ user.email || 'No email set' }}</div>
          </div>
        </div>
        
        <div class="dropdownActions">
          <router-link to="/profile" class="dropdownItem" @click="closeDropdown">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
              <circle cx="12" cy="7" r="4"/>
            </svg>
            Edit Profile
          </router-link>
          <router-link to="/dashboard" class="dropdownItem" @click="closeDropdown">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="3" y="3" width="7" height="7"/>
              <rect x="14" y="3" width="7" height="7"/>
              <rect x="14" y="14" width="7" height="7"/>
              <rect x="3" y="14" width="7" height="7"/>
            </svg>
            Dashboard
          </router-link>
          <button v-if="isAuthenticated" @click="handleLogout" class="dropdownItem logout">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
              <polyline points="16,17 21,12 16,7"/>
              <line x1="21" y1="12" x2="9" y2="12"/>
            </svg>
            Logout
          </button>
          <router-link v-else to="/login" class="dropdownItem" @click="closeDropdown">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
              <polyline points="10,17 15,12 10,7"/>
              <line x1="15" y1="12" x2="3" y2="12"/>
            </svg>
            Login
          </router-link>
        </div>
      </div>
    </Transition>

    <!-- Overlay to close dropdown when clicking outside -->
    <div v-if="isDropdownOpen" class="overlay" @click="closeDropdown"></div>
  </div>
</template>

<script>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

// Inline SVG placeholder (same as ProfileView)
const DEFAULT_AVATAR = 'data:image/svg+xml,' + encodeURIComponent(
  '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" fill="%2364748b"><circle cx="50" cy="50" r="50"/><circle cx="50" cy="38" r="18"/><path d="M50 62c-15 0-26 10-26 22v6h52v-6c0-12-11-22-26-22z"/></svg>'
)

export default {
  name: 'ProfileHeader',
  setup() {
    const authStore = useAuthStore()
    const router = useRouter()
    const { user, isAuthenticated } = storeToRefs(authStore)
    const isDropdownOpen = ref(false)

    const avatarDisplay = computed(() => {
      const url = (user.value && user.value.avatarUrl) || ''
      if (url && (url.startsWith('http://') || url.startsWith('https://'))) {
        return url
      }
      return DEFAULT_AVATAR
    })

    const toggleDropdown = () => {
      isDropdownOpen.value = !isDropdownOpen.value
    }

    const closeDropdown = () => {
      isDropdownOpen.value = false
    }

    const handleLogout = () => {
      authStore.logout()
      closeDropdown()
      router.push('/login')
    }

    // Close dropdown on Escape key
    const handleKeydown = (event) => {
      if (event.key === 'Escape') {
        closeDropdown()
      }
    }

    onMounted(() => {
      document.addEventListener('keydown', handleKeydown)
    })

    onUnmounted(() => {
      document.removeEventListener('keydown', handleKeydown)
    })

    return {
      user,
      isAuthenticated,
      isDropdownOpen,
      avatarDisplay,
      toggleDropdown,
      closeDropdown,
      handleLogout
    }
  }
}
</script>

<style scoped>
.profileHeader {
  position: relative;
  display: inline-block;
}

.profileButton {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 10px;
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-family: inherit;
  color: var(--text, #1e293b);
}

.profileButton:hover {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 2px 8px rgba(38, 71, 232, 0.15);
  transform: translateY(-1px);
}

.profileButton:focus-visible {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid var(--primary, #2647e8);
}

.profileName {
  font-weight: 600;
  font-size: 0.85rem;
  color: var(--text, #1e293b);
}

.dropdownIcon {
  width: 16px;
  height: 16px;
  transition: transform 0.2s ease;
  color: var(--muted, #64748b);
}

.dropdownIcon.open {
  transform: rotate(180deg);
}

.dropdownMenu {
  position: absolute;
  top: calc(100% + 12px);
  right: 0;
  min-width: 320px;
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 16px;
  box-shadow: 0 15px 50px rgba(0, 0, 0, 0.15);
  z-index: 1000;
  overflow: hidden;
  backdrop-filter: blur(10px);
}

.dropdownHeader {
  padding: 20px;
  border-bottom: 1px solid var(--card-border, #e2e8f0);
  display: flex;
  align-items: center;
  gap: 16px;
  background: linear-gradient(135deg, var(--detail-bg, #f8fafc) 0%, var(--card-bg, white) 100%);
}

.dropdownAvatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid var(--primary, #2647e8);
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.2);
}

.dropdownUserInfo {
  flex: 1;
  min-width: 0;
}

.dropdownName {
  font-weight: 600;
  color: var(--text, #1e293b);
  font-size: 1.1rem;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dropdownEmail {
  font-size: 0.9rem;
  color: var(--muted, #64748b);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dropdownActions {
  padding: 12px 0;
}

.dropdownItem {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 20px;
  color: var(--text, #1e293b);
  text-decoration: none;
  border: none;
  background: none;
  width: 100%;
  text-align: left;
  font-family: inherit;
  font-size: 0.95rem;
  cursor: pointer;
  transition: all 0.2s ease;
  position: relative;
}

.dropdownItem::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 3px;
  background: var(--primary, #2647e8);
  transform: scaleY(0);
  transition: transform 0.2s ease;
}

.dropdownItem:hover {
  background: var(--detail-bg, #f8fafc);
  transform: translateX(4px);
}

.dropdownItem:hover::before {
  transform: scaleY(1);
}

.dropdownItem.logout {
  color: #dc2626;
}

.dropdownItem.logout::before {
  background: #dc2626;
}

.dropdownItem.logout:hover {
  background: #fef2f2;
}

.dropdownItem svg {
  width: 20px;
  height: 20px;
  flex-shrink: 0;
  transition: transform 0.2s ease;
}

.dropdownItem:hover svg {
  transform: scale(1.1);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 999;
  background: transparent;
}

/* Dropdown transition */
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.2s ease;
}

.dropdown-enter-from {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}

.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}
</style>

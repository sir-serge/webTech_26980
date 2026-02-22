<template>
  <div class="profileContainer">
    <h2 id="profile-heading" class="heading">Profile</h2>
    <section class="profileCard" aria-labelledby="profile-heading">
      <div class="profileHeader">
        <div class="avatarWrap">
          <img
            :src="avatarDisplay"
            :alt="user.displayName ? `Profile picture of ${user.displayName}` : 'Profile picture'"
            class="avatar"
            width="120"
            height="120"
          />
        </div>
        <div class="profileMeta">
          <p class="displayName">{{ user.displayName || 'No name set' }}</p>
          <p class="email">{{ user.email || 'No email set' }}</p>
        </div>
      </div>

      <form class="profileForm" @submit.prevent="saveProfile" novalidate>
        <div class="formGroup">
          <label for="displayName">Display name</label>
          <input
            id="displayName"
            v-model="user.displayName"
            type="text"
            autocomplete="name"
            placeholder="Your name"
            class="textInput"
            aria-describedby="displayName-hint"
          />
          <span id="displayName-hint" class="hint">Shown across the app</span>
        </div>
        <div class="formGroup">
          <label for="email">Email</label>
          <input
            id="email"
            v-model="user.email"
            type="email"
            autocomplete="email"
            placeholder="you@example.com"
            class="textInput"
            aria-describedby="email-hint"
          />
          <span id="email-hint" class="hint">We never share your email</span>
        </div>
        <div class="formGroup">
          <label for="avatarUrl">Profile picture URL</label>
          <input
            id="avatarUrl"
            v-model="user.avatarUrl"
            type="url"
            placeholder="https://example.com/your-photo.jpg"
            class="textInput"
            aria-describedby="avatarUrl-hint"
          />
          <span id="avatarUrl-hint" class="hint">Enter a direct image link. Leave empty for default avatar.</span>
        </div>
        <div class="formGroup">
          <label for="userBio">Bio</label>
          <textarea
            id="userBio"
            v-model="user.bio"
            placeholder="Tell us a bit about yourself..."
            rows="5"
            class="bioInput"
            aria-describedby="userBio-hint"
          />
          <span id="userBio-hint" class="hint">Your bio is saved automatically.</span>
        </div>
        <button type="submit" class="saveButton">Save profile</button>
      </form>

      <div v-if="user.bio" class="previewSection">
        <h3 class="previewLabel">Bio preview</h3>
        <p class="previewText">{{ user.bio }}</p>
      </div>
    </section>
  </div>
</template>

<script>
import { computed } from 'vue'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'

// Inline SVG placeholder (no external request); user can set avatarUrl for a real photo
const DEFAULT_AVATAR = 'data:image/svg+xml,' + encodeURIComponent(
  '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" fill="%2364748b"><circle cx="50" cy="50" r="50"/><circle cx="50" cy="38" r="18"/><path d="M50 62c-15 0-26 10-26 22v6h52v-6c0-12-11-22-26-22z"/></svg>'
)

export default {
  name: 'ProfileView',
  setup() {
    const authStore = useAuthStore()
    const toast = useToastStore()
    const { user } = storeToRefs(authStore)

    const avatarDisplay = computed(() => {
      const url = (user.value && user.value.avatarUrl) || ''
      if (url && (url.startsWith('http://') || url.startsWith('https://'))) {
        return url
      }
      return DEFAULT_AVATAR
    })

    function saveProfile() {
      authStore.updateProfile({
        displayName: user.value.displayName,
        email: user.value.email,
        avatarUrl: user.value.avatarUrl,
        bio: user.value.bio
      })
      toast.success('Profile saved!')
    }

    return { user, avatarDisplay, saveProfile }
  }
}
</script>

<style scoped>
.profileContainer {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.heading {
  font-size: 2rem;
  color: var(--primary, #2647e8);
  margin-bottom: 24px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.profileCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.profileHeader {
  display: flex;
  align-items: center;
  gap: 24px;
  margin-bottom: 28px;
  padding-bottom: 24px;
  border-bottom: 1px solid var(--card-border, #e2e8f0);
}

.avatarWrap {
  flex-shrink: 0;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid var(--primary, #2647e8);
}

.profileMeta {
  flex: 1;
  min-width: 0;
}

.displayName {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--text, #1e293b);
  margin-bottom: 4px;
}

.email {
  font-size: 1rem;
  color: var(--muted, #64748b);
}

.profileForm {
  margin-bottom: 20px;
}

.formGroup {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: 600;
  color: var(--primary, #2647e8);
  margin-bottom: 8px;
  font-size: 0.95rem;
}

.textInput,
.bioInput {
  width: 100%;
  padding: 12px;
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 8px;
  font-size: 1rem;
  font-family: inherit;
  background: var(--input-bg, white);
  color: var(--text, #1e293b);
}

.textInput:focus,
.bioInput:focus {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 0 0 3px rgba(38, 71, 232, 0.2);
}

.textInput:focus-visible,
.bioInput:focus-visible {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}

.bioInput {
  resize: vertical;
  min-height: 100px;
}

.hint {
  display: block;
  font-size: 0.85rem;
  color: var(--muted, #64748b);
  margin-top: 6px;
}

.saveButton {
  padding: 12px 24px;
  background: var(--primary, #2647e8);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.saveButton:hover {
  opacity: 0.9;
}

.saveButton:focus-visible {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}

.previewSection {
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid var(--card-border, #e2e8f0);
}

.previewLabel {
  font-size: 1rem;
  font-weight: 600;
  color: var(--muted, #64748b);
  margin-bottom: 8px;
}

.previewText {
  color: var(--text, #1e293b);
  white-space: pre-wrap;
}
</style>

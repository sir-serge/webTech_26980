<template>
  <div class="toastContainer" aria-live="polite" aria-atomic="true">
    <TransitionGroup name="toast" tag="ul" class="toastList">
      <li
        v-for="t in toasts"
        :key="t.id"
        class="toast"
        :class="t.type"
        role="status"
      >
        <span class="toastIcon">{{ iconFor(t.type) }}</span>
        <span class="toastMessage">{{ t.message }}</span>
        <button
          type="button"
          class="toastClose"
          aria-label="Dismiss"
          @click="remove(t.id)"
        >
          ×
        </button>
      </li>
    </TransitionGroup>
  </div>
</template>

<script>
import { storeToRefs } from 'pinia'
import { useToastStore } from '../stores/toast'

export default {
  name: 'ToastList',
  setup() {
    const store = useToastStore()
    const { toasts } = storeToRefs(store)
    return {
      toasts,
      remove: store.remove,
      iconFor(type) {
        return { success: '✓', info: 'ℹ', error: '!' }[type] || '•'
      }
    }
  }
}
</script>

<style scoped>
.toastContainer {
  position: fixed;
  bottom: 24px;
  right: 24px;
  z-index: 1000;
  max-width: 360px;
  pointer-events: none;
}

.toastList {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
  pointer-events: auto;
}

.toast {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 16px;
  border-radius: 10px;
  background: var(--card-bg, #fff);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  border-left: 4px solid var(--primary, #2647e8);
  animation: toastIn 0.35s ease-out;
}

.toast.success {
  border-left-color: #22c55e;
}

.toast.success .toastIcon { color: #22c55e; }

.toast.info {
  border-left-color: #3b82f6;
}

.toast.info .toastIcon { color: #3b82f6; }

.toast.error {
  border-left-color: #ef4444;
}

.toast.error .toastIcon { color: #ef4444; }

.toastIcon {
  font-size: 1.2rem;
  font-weight: 700;
  flex-shrink: 0;
}

.toastMessage {
  flex: 1;
  font-size: 0.95rem;
  color: var(--text, #1e293b);
}

.toastClose {
  flex-shrink: 0;
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 6px;
  background: transparent;
  color: var(--muted, #64748b);
  font-size: 1.25rem;
  line-height: 1;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}

.toastClose:hover {
  background: rgba(0, 0, 0, 0.08);
  color: var(--text, #1e293b);
}

.toastClose:focus-visible {
  outline: 2px solid var(--primary);
  outline-offset: 2px;
}

@keyframes toastIn {
  from {
    opacity: 0;
    transform: translateX(100%);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

/* TransitionGroup */
.toast-enter-active {
  animation: toastIn 0.35s ease-out;
}

.toast-leave-active {
  animation: toastOut 0.3s ease-in forwards;
}

.toast-move {
  transition: transform 0.35s ease;
}

@keyframes toastOut {
  from {
    opacity: 1;
    transform: translateX(0);
  }
  to {
    opacity: 0;
    transform: translateX(100%);
  }
}
</style>

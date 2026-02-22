<template>
  <div class="homeContainer">
    <h1 class="title">Welcome to TaskBuddy</h1>
    <p class="description">
      A powerful task management application to keep track of your work and stay productive.
    </p>
    <div class="features">
      <div class="featureCard">
        <div class="cardIcon">📝</div>
        <h3>Task Management</h3>
        <p>Create tasks with detailed information including time, type, and urgency levels.</p>
        <p class="subtext">Organize your work efficiently with comprehensive task details.</p>
        <router-link to="/dashboard" class="linkButton pulse">
          Go to Dashboard
        </router-link>
      </div>
      <div class="featureCard">
        <div class="cardIcon">👤</div>
        <h3>Your Profile</h3>
        <p>Add a photo, display name, and bio. Your profile is saved locally and stays with you.</p>
        <p class="subtext">Customize how you appear and keep notes about yourself.</p>
        <router-link to="/profile" class="linkButton secondary">
          Edit Profile
        </router-link>
      </div>
    </div>
    <p class="keyboardHint">
      <kbd>/</kbd> focuses search on the Dashboard
    </p>
  </div>
</template>

<script>
export default {
  name: 'HomePage'
}
</script>

<style scoped>
.homeContainer {
  text-align: center;
  padding: 40px 20px;
}

.title {
  font-size: 2.5rem;
  color: var(--primary, #2647e8);
  margin-bottom: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.description {
  font-size: 1.2rem;
  color: var(--muted, #64748b);
  margin-bottom: 40px;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  margin-top: 40px;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.featureCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 16px;
  padding: 28px;
  transition: all 0.35s ease;
  text-align: left;
}

.featureCard:hover {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 12px 32px rgba(38, 71, 232, 0.15);
  transform: translateY(-6px);
}

.cardIcon {
  font-size: 2.5rem;
  margin-bottom: 12px;
  display: inline-block;
  transition: transform 0.3s ease;
}

.featureCard:hover .cardIcon {
  transform: scale(1.15);
}

.featureCard h3 {
  color: var(--primary, #2647e8);
  font-size: 1.35rem;
  margin-bottom: 12px;
}

.featureCard p {
  color: var(--muted, #64748b);
  margin-bottom: 16px;
  font-size: 0.95rem;
}

.subtext {
  font-size: 0.9rem !important;
  opacity: 0.9;
}

.linkButton {
  display: inline-block;
  padding: 12px 24px;
  border-radius: 10px;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.25s ease;
  border: 2px solid transparent;
}

.linkButton.pulse {
  background: linear-gradient(135deg, #7c3aed 0%, #5b21b6 100%);
  color: white;
  box-shadow: 0 4px 14px rgba(124, 58, 237, 0.4);
}

.linkButton.pulse:hover {
  transform: translateY(-3px) scale(1.02);
  box-shadow: 0 8px 24px rgba(124, 58, 237, 0.45);
}

.linkButton.secondary {
  background: var(--detail-bg, #f1f5f9);
  color: var(--primary, #2647e8);
  border-color: var(--primary, #2647e8);
}

.linkButton.secondary:hover {
  background: var(--badge-bg, #dbeafe);
  transform: translateY(-2px);
}

@keyframes pulse {
  0%, 100% { box-shadow: 0 4px 14px rgba(124, 58, 237, 0.4); }
  50% { box-shadow: 0 4px 20px rgba(124, 58, 237, 0.6); }
}

.linkButton.pulse {
  animation: pulse 2.5s ease-in-out infinite;
}

.keyboardHint {
  margin-top: 32px;
  font-size: 0.9rem;
  color: var(--muted, #64748b);
}

kbd {
  display: inline-block;
  padding: 4px 8px;
  font-family: inherit;
  font-size: 0.85em;
  background: var(--detail-bg, #e2e8f0);
  border: 1px solid var(--card-border, #cbd5e1);
  border-radius: 6px;
  box-shadow: 0 1px 0 var(--card-border, #cbd5e1);
}
</style>

<template>
  <nav class="navbar" aria-label="Main navigation">
    <router-link to="/" class="navLink">Home</router-link>
    <router-link to="/dashboard" class="navLink" active-class="navLinkActive">Dashboard</router-link>
    <router-link to="/profile" class="navLink" active-class="navLinkActive">Profile</router-link>
  </nav>
</template>

<script>
export default {
  name: 'NavBar'
}
</script>

<style scoped>
.navbar {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
  padding: 15px;
  background-color: var(--detail-bg, #f3f4f6);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.navLink {
  text-decoration: none;
  color: var(--primary, #2647e8);
  font-weight: 600;
  padding: 8px 16px;
  border-radius: 6px;
  transition: all 0.3s;
}

.navLink:hover {
  background-color: var(--badge-bg, #dbeafe);
  color: #1e40af;
}

.navLink.router-link-active,
.navLinkActive {
  background-color: var(--primary, #2647e8);
  color: white;
}

.navLink:focus-visible {
  outline: 2px solid var(--primary, #2647e8);
  outline-offset: 2px;
}
</style>

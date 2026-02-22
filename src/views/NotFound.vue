<template>
  <div class="notFoundContainer">
    <h1 class="code">404</h1>
    <h2 class="title">Page not found</h2>
    <p class="message">
      The page you're looking for doesn't exist or has been moved.
    </p>
    <router-link to="/" class="homeLink">Back to Home</router-link>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>
.notFoundContainer {
  text-align: center;
  padding: 60px 20px;
  max-width: 500px;
  margin: 0 auto;
}

.code {
  font-size: 5rem;
  font-weight: 800;
  color: var(--primary, #2647e8);
  line-height: 1;
  margin-bottom: 16px;
}

.title {
  font-size: 1.75rem;
  color: var(--text, #1e293b);
  margin-bottom: 12px;
}

.message {
  color: var(--muted, #64748b);
  margin-bottom: 32px;
  font-size: 1.1rem;
}

.homeLink {
  display: inline-block;
  padding: 12px 24px;
  background: var(--primary, #2647e8);
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 600;
  transition: all 0.2s;
}

.homeLink:hover {
  opacity: 0.9;
  transform: translateY(-2px);
}

.homeLink:focus-visible {
  outline: 2px solid white;
  outline-offset: 2px;
}
</style>

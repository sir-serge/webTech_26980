<template>
  <div class="homeContainer">
    <h1 class="title">Welcome to TaskBuddy</h1>
    <p class="description">
      A powerful task management application to keep track of your work and stay productive.
    </p>
    <div class="features">
      <div class="featureCard">
        <h3>📝 Task Management</h3>
        <p>Create tasks with detailed information including time, type, and urgency levels.</p>
        <p class="subtext">Organize your work efficiently with comprehensive task details.</p>
        <router-link to="/tasks" class="linkButton">Go to Tasks</router-link>
      </div>
    </div>
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
  color: #2647e8;
  margin-bottom: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.description {
  font-size: 1.2rem;
  color: #64748b;
  margin-bottom: 40px;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 40px;
}

.featureCard {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  padding: 30px;
  transition: all 0.3s;
}

.featureCard:hover {
  border-color: #2647e8;
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.1);
  transform: translateY(-5px);
}

.featureCard h3 {
  color: #2647e8;
  font-size: 1.5rem;
  margin-bottom: 15px;
}

.featureCard p {
  color: #64748b;
  margin-bottom: 20px;
}

.linkButton {
  display: inline-block;
  padding: 10px 20px;
  background-color: #7c3aed;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  transition: all 0.3s;
  font-weight: 600;
}

.linkButton:hover {
  background-color: #6d28d9;
  transform: scale(1.05);
}
</style>

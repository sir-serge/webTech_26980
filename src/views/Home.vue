<template>
  <div class="homeContainer">
    <!-- Hero Section -->
    <section class="heroSection">
      <div class="heroContent">
        <h1 class="title">Welcome to TaskBuddy</h1>
        <p class="description">
          Your intelligent task management companion that helps you stay organized, boost productivity, and achieve your goals with ease.
        </p>
        
        <div class="heroActions">
          <router-link to="/register" class="heroButton primary">
            <span>Get Started</span>
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14"/>
              <path d="m12 5 7 7-7 7"/>
            </svg>
          </router-link>
          <router-link to="/login" class="heroButton secondary">
            Already have an account? Log in
          </router-link>
        </div>
      </div>
      
      <div class="heroVisual">
        <div class="floatingCards">
          <div class="card taskCard">
            <div class="cardIcon">📋</div>
            <div class="cardContent">
              <div class="cardTitle">Complete Project</div>
              <div class="cardStatus">In Progress</div>
            </div>
          </div>
          <div class="card completedCard">
            <div class="cardIcon">✅</div>
            <div class="cardContent">
              <div class="cardTitle">Team Meeting</div>
              <div class="cardStatus">Completed</div>
            </div>
          </div>
          <div class="card urgentCard">
            <div class="cardIcon">🔥</div>
            <div class="cardContent">
              <div class="cardTitle">Bug Fix</div>
              <div class="cardStatus">High Priority</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="featuresSection">
      <h2 class="sectionTitle">Everything You Need to Stay Productive</h2>
      
      <div class="features">
        <div class="featureCard">
          <div class="featureIcon">📝</div>
          <h3>Smart Task Management</h3>
          <p>Create, organize, and track tasks with detailed information including time estimates, categories, and priority levels.</p>
          <ul class="featureList">
            <li>Set due dates and reminders</li>
            <li>Categorize by project or type</li>
            <li>Track time and progress</li>
          </ul>
          <router-link to="/dashboard" class="featureLink">
            Explore Tasks
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14"/>
              <path d="m12 5 7 7-7 7"/>
            </svg>
          </router-link>
        </div>
        
        <div class="featureCard">
          <div class="featureIcon">👤</div>
          <h3>Personalized Profile</h3>
          <p>Customize your experience with a personal profile, avatar, and preferences that make TaskBuddy uniquely yours.</p>
          <ul class="featureList">
            <li>Add profile photo and bio</li>
            <li>Set display preferences</li>
            <li>Track your productivity stats</li>
          </ul>
          <router-link to="/profile" class="featureLink">
            Customize Profile
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14"/>
              <path d="m12 5 7 7-7 7"/>
            </svg>
          </router-link>
        </div>
        
        <div class="featureCard">
          <div class="featureIcon">📊</div>
          <h3>Progress Analytics</h3>
          <p>Visualize your productivity with comprehensive statistics, charts, and insights about your task completion patterns.</p>
          <ul class="featureList">
            <li>Completion rate tracking</li>
            <li>Productivity trends</li>
            <li>Performance insights</li>
          </ul>
          <router-link to="/dashboard" class="featureLink">
            View Analytics
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14"/>
              <path d="m12 5 7 7-7 7"/>
            </svg>
          </router-link>
        </div>
        
        <div class="featureCard">
          <div class="featureIcon">🎯</div>
          <h3>Goal Setting</h3>
          <p>Set meaningful goals and break them down into actionable tasks. Track your progress and celebrate achievements.</p>
          <ul class="featureList">
            <li>Create milestone goals</li>
            <li>Track achievement progress</li>
            <li>Earn completion badges</li>
          </ul>
          <router-link to="/dashboard" class="featureLink">
            Set Goals
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14"/>
              <path d="m12 5 7 7-7 7"/>
            </svg>
          </router-link>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="ctaSection">
      <div class="ctaContent">
        <h2>Ready to Transform Your Productivity?</h2>
        <p>Join thousands of users who have already discovered the power of organized task management.</p>
        <router-link to="/register" class="ctaButton">
          Start Your Free Journey
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M5 12h14"/>
            <path d="m12 5 7 7-7 7"/>
          </svg>
        </router-link>
      </div>
    </section>

    <!-- Keyboard Hint -->
    <div class="keyboardHint">
      <kbd>/</kbd> focuses search on the Dashboard
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
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
  background: linear-gradient(135deg, rgba(255,255,255,0.9) 0%, rgba(248,250,252,0.9) 100%);
  border-radius: 20px;
  backdrop-filter: blur(10px);
}

/* Hero Section */
.heroSection {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
  align-items: center;
  margin-bottom: 50px;
  min-height: 350px;
  position: relative;
}

.heroSection::before {
  content: '';
  position: absolute;
  top: -20px;
  right: -20px;
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(38, 71, 232, 0.1) 0%, transparent 70%);
  border-radius: 50%;
  animation: float 8s ease-in-out infinite;
}

.heroSection::after {
  content: '';
  position: absolute;
  bottom: -30px;
  left: -30px;
  width: 150px;
  height: 150px;
  background: radial-gradient(circle, rgba(124, 58, 237, 0.08) 0%, transparent 70%);
  border-radius: 50%;
  animation: float 6s ease-in-out infinite reverse;
}

.heroContent {
  z-index: 2;
  position: relative;
}

.title {
  font-size: 2.8rem;
  color: var(--primary, #2647e8);
  margin-bottom: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 700;
  line-height: 1.2;
  background: linear-gradient(135deg, var(--primary, #2647e8) 0%, #7c3aed 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  position: relative;
}

.title::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 60px;
  height: 3px;
  background: linear-gradient(90deg, var(--primary, #2647e8), #7c3aed);
  border-radius: 2px;
  animation: shimmer 3s ease-in-out infinite;
}

@keyframes shimmer {
  0%, 100% { opacity: 0.6; transform: scaleX(0.8); }
  50% { opacity: 1; transform: scaleX(1); }
}

.description {
  font-size: 1.1rem;
  color: var(--muted, #64748b);
  margin-bottom: 30px;
  line-height: 1.5;
}

.heroActions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  align-items: center;
}

.heroButton {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 12px 24px;
  border-radius: 10px;
  font-weight: 600;
  text-decoration: none;
  border: 2px solid transparent;
  transition: all 0.3s ease;
  font-size: 1rem;
}

.heroButton.primary {
  background: linear-gradient(135deg, #22c55e 0%, #16a34a 100%);
  color: white;
  box-shadow: 0 6px 20px rgba(34, 197, 94, 0.3);
}

.heroButton.primary:hover {
  transform: translateY(-2px) scale(1.02);
  box-shadow: 0 8px 25px rgba(34, 197, 94, 0.4);
}

.heroButton.secondary {
  background: var(--card-bg, white);
  color: var(--primary, #2647e8);
  border-color: var(--primary, #2647e8);
}

.heroButton.secondary:hover {
  background: var(--badge-bg, #dbeafe);
  transform: translateY(-2px);
}

.heroButton svg {
  width: 18px;
  height: 18px;
  transition: transform 0.2s ease;
}

.heroButton:hover svg {
  transform: translateX(3px);
}

/* Hero Visual */
.heroVisual {
  position: relative;
  height: 280px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.floatingCards {
  position: relative;
  width: 100%;
  height: 100%;
}

.card {
  position: absolute;
  background: var(--card-bg, white);
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: center;
  gap: 12px;
  border: 2px solid var(--card-border, #e2e8f0);
  transition: all 0.3s ease;
  animation: float 6s ease-in-out infinite;
}

.card:hover {
  transform: translateY(-3px) scale(1.02);
  box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
}

.taskCard {
  top: 15%;
  left: 5%;
  animation-delay: 0s;
  border-left: 3px solid #3b82f6;
}

.completedCard {
  top: 45%;
  right: 5%;
  animation-delay: 2s;
  border-left: 3px solid #22c55e;
}

.urgentCard {
  bottom: 15%;
  left: 20%;
  animation-delay: 4s;
  border-left: 3px solid #ef4444;
}

.cardIcon {
  font-size: 1.5rem;
  flex-shrink: 0;
}

.cardContent {
  flex: 1;
}

.cardTitle {
  font-weight: 600;
  color: var(--text, #1e293b);
  margin-bottom: 2px;
  font-size: 0.9rem;
}

.cardStatus {
  font-size: 0.8rem;
  color: var(--muted, #64748b);
}

@keyframes float {
  0%, 100% { transform: translateY(0px); }
  50% { transform: translateY(-15px); }
}

/* Features Section */
.featuresSection {
  margin-bottom: 50px;
}

.sectionTitle {
  text-align: center;
  font-size: 2rem;
  color: var(--primary, #2647e8);
  margin-bottom: 40px;
  font-weight: 700;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
}

.featureCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 16px;
  padding: 24px;
  transition: all 0.4s ease;
  text-align: left;
  position: relative;
  overflow: hidden;
  backdrop-filter: blur(5px);
}

.featureCard::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, var(--primary, #2647e8), #7c3aed);
  transform: scaleX(0);
  transition: transform 0.3s ease;
}

.featureCard::after {
  content: '';
  position: absolute;
  top: -50%;
  right: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(38, 71, 232, 0.03) 0%, transparent 70%);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.featureCard:hover::before {
  transform: scaleX(1);
}

.featureCard:hover::after {
  opacity: 1;
}

.featureCard:hover {
  border-color: var(--primary, #2647e8);
  box-shadow: 0 15px 40px rgba(38, 71, 232, 0.12);
  transform: translateY(-6px);
  background: linear-gradient(135deg, rgba(255,255,255,0.95) 0%, rgba(248,250,252,0.95) 100%);
}

.featureIcon {
  font-size: 2.5rem;
  margin-bottom: 16px;
  display: inline-block;
  transition: transform 0.3s ease;
}

.featureCard:hover .featureIcon {
  transform: scale(1.1) rotate(3deg);
}

.featureCard h3 {
  color: var(--primary, #2647e8);
  font-size: 1.3rem;
  margin-bottom: 12px;
  font-weight: 600;
}

.featureCard p {
  color: var(--muted, #64748b);
  margin-bottom: 16px;
  font-size: 0.95rem;
  line-height: 1.5;
}

.featureList {
  list-style: none;
  padding: 0;
  margin-bottom: 20px;
}

.featureList li {
  padding: 6px 0;
  color: var(--text, #1e293b);
  position: relative;
  padding-left: 20px;
  font-size: 0.9rem;
}

.featureList li::before {
  content: '✓';
  position: absolute;
  left: 0;
  color: #22c55e;
  font-weight: bold;
}

.featureLink {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--primary, #2647e8);
  text-decoration: none;
  font-weight: 600;
  transition: all 0.2s ease;
  font-size: 0.9rem;
}

.featureLink:hover {
  color: #1e40af;
  transform: translateX(3px);
}

.featureLink svg {
  width: 14px;
  height: 14px;
  transition: transform 0.2s ease;
}

.featureLink:hover svg {
  transform: translateX(2px);
}

/* CTA Section */
.ctaSection {
  background: linear-gradient(135deg, var(--primary, #2647e8) 0%, #7c3aed 100%);
  border-radius: 20px;
  padding: 40px 30px;
  text-align: center;
  margin-bottom: 40px;
  position: relative;
  overflow: hidden;
}

.ctaSection::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
  animation: pulse 4s ease-in-out infinite;
}

.ctaContent {
  position: relative;
  z-index: 2;
}

.ctaSection h2 {
  color: white;
  font-size: 2rem;
  margin-bottom: 16px;
  font-weight: 700;
}

.ctaSection p {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
  margin-bottom: 24px;
  max-width: 500px;
  margin-left: auto;
  margin-right: auto;
}

.ctaButton {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: white;
  color: var(--primary, #2647e8);
  padding: 14px 28px;
  border-radius: 10px;
  text-decoration: none;
  font-weight: 700;
  font-size: 1rem;
  transition: all 0.3s ease;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.ctaButton:hover {
  transform: translateY(-2px) scale(1.02);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
}

.ctaButton svg {
  width: 18px;
  height: 18px;
  transition: transform 0.2s ease;
}

.ctaButton:hover svg {
  transform: translateX(3px);
}

@keyframes pulse {
  0%, 100% { opacity: 0.5; }
  50% { opacity: 0.8; }
}

/* Keyboard Hint */
.keyboardHint {
  text-align: center;
  margin-top: 30px;
  font-size: 0.85rem;
  color: var(--muted, #64748b);
}

kbd {
  display: inline-block;
  padding: 4px 8px;
  font-family: inherit;
  font-size: 0.8em;
  background: var(--detail-bg, #e2e8f0);
  border: 1px solid var(--card-border, #cbd5e1);
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

/* Responsive Design */
@media (max-width: 768px) {
  .heroSection {
    grid-template-columns: 1fr;
    gap: 30px;
    min-height: auto;
  }
  
  .heroVisual {
    height: 220px;
  }
  
  .title {
    font-size: 2.2rem;
  }
  
  .description {
    font-size: 1rem;
  }
  
  .features {
    grid-template-columns: 1fr;
  }
  
  .ctaSection {
    padding: 30px 20px;
  }
  
  .ctaSection h2 {
    font-size: 1.6rem;
  }
}
</style>

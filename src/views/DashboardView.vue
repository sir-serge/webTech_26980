<template>
  <div class="dashContainer">
    <!-- Greeting -->
    <div class="greeting">
      <div>
        <h2 class="greetHeading">{{ greetingText }}, {{ displayName }} 👋</h2>
        <p class="greetSub">Here's what's going on with your tasks today.</p>
      </div>
      <router-link to="/add-task" class="quickAddBtn">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Add Task
      </router-link>
    </div>

    <!-- Loading -->
    <div v-if="loading" class="loadingRow" aria-live="polite">
      <span class="spinner" aria-hidden="true" /> Fetching tasks…
    </div>

    <!-- Stat cards -->
    <div class="statGrid">
      <div class="statCard total">
        <div class="statIcon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>
        </div>
        <div class="statInfo">
          <span class="statNum">{{ tasks.length }}</span>
          <span class="statLabel">Total Tasks</span>
        </div>
      </div>

      <div class="statCard todo">
        <div class="statIcon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/></svg>
        </div>
        <div class="statInfo">
          <span class="statNum">{{ todoCount }}</span>
          <span class="statLabel">To Do</span>
        </div>
      </div>

      <div class="statCard inprogress">
        <div class="statIcon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83"/></svg>
        </div>
        <div class="statInfo">
          <span class="statNum">{{ inProgressCount }}</span>
          <span class="statLabel">In Progress</span>
        </div>
      </div>

      <div class="statCard done">
        <div class="statIcon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22,4 12,14.01 9,11.01"/></svg>
        </div>
        <div class="statInfo">
          <span class="statNum">{{ doneCount }}</span>
          <span class="statLabel">Done</span>
        </div>
      </div>
    </div>

    <!-- Progress -->
    <div v-if="tasks.length > 0" class="progressCard">
      <div class="progressHeader">
        <span class="progressTitle">Completion Progress</span>
        <span class="progressPct">{{ completionRate }}%</span>
      </div>
      <div class="progressBar" role="progressbar" :aria-valuenow="completionRate" aria-valuemin="0" aria-valuemax="100">
        <div class="progressFill" :style="{ width: completionRate + '%' }" />
      </div>
      <div class="progressHints">
        <span>{{ doneCount }} done</span>
        <span>{{ tasks.length - doneCount }} remaining</span>
      </div>
    </div>

    <!-- Bottom row -->
    <div class="bottomRow">
      <!-- Priority breakdown -->
      <div v-if="tasks.length > 0" class="priorityCard">
        <h3 class="cardHeading">Priority Breakdown</h3>
        <div class="priorityRows">
          <div class="priorityRow" v-for="p in priorities" :key="p.key">
            <span class="priorityLabel" :class="`pri-${p.key.toLowerCase()}`">{{ p.key }}</span>
            <div class="priorityBarBg">
              <div class="priorityBarFill" :class="`pfill-${p.key.toLowerCase()}`" :style="{ width: pctOf(p.count) + '%' }" />
            </div>
            <span class="priorityCnt">{{ p.count }}</span>
          </div>
        </div>
      </div>

      <!-- Recent tasks -->
      <div class="recentCard">
        <div class="cardHeadingRow">
          <h3 class="cardHeading">Recent Tasks</h3>
          <router-link to="/tasks" class="seeAll">See all →</router-link>
        </div>
        <div v-if="tasks.length === 0" class="noTasks">
          No tasks yet.
          <router-link to="/add-task" class="inlineLink">Add your first task →</router-link>
        </div>
        <ul v-else class="recentList">
          <li v-for="task in recentTasks" :key="task.id" class="recentItem">
            <span class="recentDot" :class="`dot-${(task.status || 'TODO').toLowerCase()}`" aria-hidden="true" />
            <span class="recentTitle">{{ task.title }}</span>
            <span class="recentBadge" :class="`pri-${(task.priority || 'LOW').toLowerCase()}`">{{ task.priority }}</span>
          </li>
        </ul>
      </div>
    </div>

    <!-- Quick actions -->
    <div class="quickActions">
      <router-link to="/add-task" class="actionBtn primary">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Add New Task
      </router-link>
      <router-link to="/tasks" class="actionBtn secondary">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2"/><rect x="9" y="3" width="6" height="4" rx="1"/></svg>
        View All Tasks
      </router-link>
    </div>
  </div>
</template>

<script>
import { computed, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useTasksStore } from '../stores/tasks'
import { useAuthStore } from '../stores/auth'

export default {
  name: 'DashboardView',
  setup() {
    const store = useTasksStore()
    const auth  = useAuthStore()

    const { tasks, loading } = storeToRefs(store)
    const { user }           = storeToRefs(auth)

    const displayName = computed(() => user.value.displayName || 'there')

    const greetingText = computed(() => {
      const h = new Date().getHours()
      if (h < 12) return 'Good morning'
      if (h < 18) return 'Good afternoon'
      return 'Good evening'
    })

    const todoCount       = computed(() => store.todoTasks.length)
    const inProgressCount = computed(() => store.inProgressTasks.length)
    const doneCount       = computed(() => store.doneTasks.length)

    const completionRate = computed(() => {
      const total = tasks.value.length
      return total === 0 ? 0 : Math.round((doneCount.value / total) * 100)
    })

    const priorities = computed(() => {
      const low  = tasks.value.filter(t => t.priority === 'LOW').length
      const med  = tasks.value.filter(t => t.priority === 'MEDIUM').length
      const high = tasks.value.filter(t => t.priority === 'HIGH').length
      return [
        { key: 'HIGH',   count: high },
        { key: 'MEDIUM', count: med  },
        { key: 'LOW',    count: low  }
      ]
    })

    function pctOf(count) {
      return tasks.value.length === 0 ? 0 : Math.round((count / tasks.value.length) * 100)
    }

    const recentTasks = computed(() => [...tasks.value].reverse().slice(0, 5))

    onMounted(() => {
      if (tasks.value.length === 0) store.fetchTasks()
    })

    return {
      tasks, loading, displayName, greetingText,
      todoCount, inProgressCount, doneCount,
      completionRate, priorities, pctOf, recentTasks
    }
  }
}
</script>

<style scoped>
.dashContainer {
  max-width: 1000px;
  margin: 0 auto;
  padding: 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

/* Greeting */
.greeting {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 12px;
}

.greetHeading {
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--text, #1e293b);
  font-family: 'Segoe UI', sans-serif;
  margin-bottom: 4px;
}

.greetSub {
  color: var(--muted, #64748b);
  font-size: 0.95rem;
}

.quickAddBtn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 10px 20px;
  background: var(--primary, #2647e8);
  color: white;
  border-radius: 10px;
  text-decoration: none;
  font-weight: 600;
  font-size: 0.95rem;
  transition: all 0.2s;
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.25);
}

.quickAddBtn:hover {
  opacity: 0.9;
  transform: translateY(-1px);
  box-shadow: 0 6px 18px rgba(38, 71, 232, 0.3);
}

.quickAddBtn svg { width: 18px; height: 18px; }

/* Loading */
.loadingRow {
  display: flex;
  align-items: center;
  gap: 10px;
  color: var(--muted, #64748b);
  font-size: 0.95rem;
}

.spinner {
  display: inline-block;
  width: 16px;
  height: 16px;
  border: 2.5px solid var(--card-border, #e2e8f0);
  border-top-color: var(--primary, #2647e8);
  border-radius: 50%;
  animation: spin 0.7s linear infinite;
  flex-shrink: 0;
}
@keyframes spin { to { transform: rotate(360deg); } }

/* Stat Cards */
.statGrid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.statCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 14px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  transition: transform 0.2s, box-shadow 0.2s;
  position: relative;
  overflow: hidden;
}

.statCard::before {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0;
  height: 3px;
  border-radius: 14px 14px 0 0;
}

.statCard.total::before  { background: #6366f1; }
.statCard.todo::before   { background: #64748b; }
.statCard.inprogress::before { background: #f59e0b; }
.statCard.done::before   { background: #22c55e; }

.statCard:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.statIcon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.statCard.total    .statIcon { background: #ede9fe; color: #6366f1; }
.statCard.todo     .statIcon { background: #f1f5f9; color: #64748b; }
.statCard.inprogress .statIcon { background: #fef3c7; color: #d97706; }
.statCard.done     .statIcon { background: #dcfce7; color: #16a34a; }

.statIcon svg { width: 24px; height: 24px; }

.statInfo {
  display: flex;
  flex-direction: column;
}

.statNum {
  font-size: 2rem;
  font-weight: 800;
  color: var(--text, #1e293b);
  line-height: 1;
}

.statLabel {
  font-size: 0.8rem;
  color: var(--muted, #64748b);
  font-weight: 500;
  margin-top: 4px;
}

/* Progress card */
.progressCard {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  padding: 24px 28px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
}

.progressHeader {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 14px;
}

.progressTitle {
  color: rgba(255,255,255,0.9);
  font-weight: 600;
  font-size: 1rem;
}

.progressPct {
  color: white;
  font-weight: 800;
  font-size: 1.5rem;
}

.progressBar {
  height: 10px;
  background: rgba(255,255,255,0.25);
  border-radius: 999px;
  overflow: hidden;
  margin-bottom: 10px;
}

.progressFill {
  height: 100%;
  background: linear-gradient(90deg, #4ade80, #22c55e);
  border-radius: 999px;
  transition: width 0.7s ease-out;
}

.progressHints {
  display: flex;
  justify-content: space-between;
  color: rgba(255,255,255,0.75);
  font-size: 0.82rem;
}

/* Bottom row */
.bottomRow {
  display: grid;
  grid-template-columns: 1fr 1.5fr;
  gap: 20px;
}

.priorityCard,
.recentCard {
  background: var(--card-bg, white);
  border: 2px solid var(--card-border, #e2e8f0);
  border-radius: 14px;
  padding: 20px;
}

.cardHeadingRow {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.cardHeading {
  font-size: 1rem;
  font-weight: 700;
  color: var(--text, #1e293b);
  margin-bottom: 16px;
}

.seeAll {
  color: var(--primary, #2647e8);
  font-size: 0.85rem;
  font-weight: 600;
  text-decoration: none;
}
.seeAll:hover { text-decoration: underline; }

/* Priority rows */
.priorityRows { display: flex; flex-direction: column; gap: 14px; }

.priorityRow {
  display: flex;
  align-items: center;
  gap: 10px;
}

.priorityLabel {
  font-size: 0.75rem;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: 20px;
  color: white;
  min-width: 60px;
  text-align: center;
}

.pri-high   { background: #ef4444; }
.pri-medium { background: #f59e0b; }
.pri-low    { background: #10b981; }

.priorityBarBg {
  flex: 1;
  height: 8px;
  background: var(--detail-bg, #f1f5f9);
  border-radius: 999px;
  overflow: hidden;
}

.priorityBarFill {
  height: 100%;
  border-radius: 999px;
  transition: width 0.6s ease-out;
}

.pfill-high   { background: #ef4444; }
.pfill-medium { background: #f59e0b; }
.pfill-low    { background: #10b981; }

.priorityCnt {
  font-weight: 700;
  color: var(--text, #1e293b);
  font-size: 0.9rem;
  min-width: 20px;
  text-align: right;
}

/* Recent tasks */
.noTasks {
  color: var(--muted, #64748b);
  font-size: 0.9rem;
  padding: 16px 0;
}

.inlineLink {
  color: var(--primary, #2647e8);
  font-weight: 600;
  margin-left: 4px;
  text-decoration: none;
}
.inlineLink:hover { text-decoration: underline; }

.recentList {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.recentItem {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  background: var(--detail-bg, #f8fafc);
  border-radius: 8px;
  transition: background 0.15s;
}
.recentItem:hover { background: var(--badge-bg, #dbeafe); }

.recentDot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;
}
.dot-todo        { background: #94a3b8; }
.dot-in_progress { background: #f59e0b; }
.dot-done        { background: #22c55e; }

.recentTitle {
  flex: 1;
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text, #1e293b);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.recentBadge {
  font-size: 0.7rem;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: 20px;
  color: white;
  flex-shrink: 0;
}

/* Quick actions */
.quickActions {
  display: flex;
  gap: 14px;
  flex-wrap: wrap;
}

.actionBtn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: 10px;
  text-decoration: none;
  font-weight: 600;
  font-size: 0.95rem;
  transition: all 0.2s;
}

.actionBtn svg { width: 18px; height: 18px; }

.actionBtn.primary {
  background: var(--primary, #2647e8);
  color: white;
  box-shadow: 0 4px 12px rgba(38, 71, 232, 0.25);
}
.actionBtn.primary:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

.actionBtn.secondary {
  background: var(--card-bg, white);
  color: var(--primary, #2647e8);
  border: 2px solid var(--primary, #2647e8);
}
.actionBtn.secondary:hover {
  background: var(--badge-bg, #dbeafe);
  transform: translateY(-1px);
}

/* Responsive */
@media (max-width: 768px) {
  .statGrid { grid-template-columns: repeat(2, 1fr); }
  .bottomRow { grid-template-columns: 1fr; }
}
@media (max-width: 480px) {
  .statGrid { grid-template-columns: repeat(2, 1fr); }
  .greetHeading { font-size: 1.4rem; }
}
</style>

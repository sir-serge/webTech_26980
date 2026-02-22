<template>
  <div class="counterContainer">
    <h2 class="heading">Counter</h2>
    
    <div class="counterDisplay">
      <p class="counterValue">{{ count }}</p>
    </div>

    <div class="buttonGroup">
      <button class="counterButton decrementBtn" @click="decreaseCount">-</button>
      <button class="counterButton resetBtn" @click="resetCount">Reset</button>
      <button class="counterButton incrementBtn" @click="increaseCount">+</button>
    </div>

    <div class="history">
      <h3>History</h3>
      <div v-if="history.length === 0" class="emptyHistory">
        <p>No history yet</p>
      </div>
      <div v-else class="historyList">
        <div v-for="(item, index) in history" :key="index" class="historyItem">
          {{ item }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CounterApp',
  data() {
    return {
      count: 0,
      history: []
    }
  },
  methods: {
    increaseCount() {
      this.count++
      this.addToHistory(`Increased to ${this.count}`)
    },
    decreaseCount() {
      this.count--
      this.addToHistory(`Decreased to ${this.count}`)
    },
    resetCount() {
      this.count = 0
      this.addToHistory('Reset to 0')
    },
    addToHistory(action) {
      const timestamp = new Date().toLocaleTimeString()
      this.history.unshift(`${timestamp} - ${action}`)
      if (this.history.length > 10) {
        this.history.pop()
      }
    }
  }
}
</script>

<style scoped>
.counterContainer {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.heading {
  font-size: 2rem;
  color: #2647e8;
  margin-bottom: 30px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.counterDisplay {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 15px;
  padding: 60px 20px;
  margin-bottom: 30px;
  box-shadow: 0 8px 16px rgba(38, 71, 232, 0.2);
}

.counterValue {
  font-size: 5rem;
  color: white;
  margin: 0;
  font-weight: bold;
}

.buttonGroup {
  display: flex;
  gap: 15px;
  justify-content: center;
  margin-bottom: 40px;
}

.counterButton {
  padding: 15px 30px;
  font-size: 1.5rem;
  font-weight: 600;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  color: white;
}

.incrementBtn {
  background-color: #4ade80;
  min-width: 80px;
}

.incrementBtn:hover {
  background-color: #22c55e;
  transform: scale(1.1);
}

.decrementBtn {
  background-color: #ef4444;
  min-width: 80px;
}

.decrementBtn:hover {
  background-color: #dc2626;
  transform: scale(1.1);
}

.resetBtn {
  background-color: #f59e0b;
  min-width: 100px;
}

.resetBtn:hover {
  background-color: #d97706;
  transform: scale(1.1);
}

.history {
  text-align: left;
  background-color: #f3f4f6;
  border-radius: 8px;
  padding: 20px;
}

.history h3 {
  color: #2647e8;
  margin-top: 0;
}

.emptyHistory {
  text-align: center;
  color: #9ca3af;
  padding: 20px;
}

.historyList {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.historyItem {
  background: white;
  padding: 10px 15px;
  border-left: 3px solid #2647e8;
  border-radius: 4px;
  color: #64748b;
  font-size: 0.9rem;
}
</style>

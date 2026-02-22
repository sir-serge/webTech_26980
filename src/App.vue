<template>
  <div id="app">
    <header class="appHeader">
      <h1 id="logo">TaskBuddy</h1>
    </header>
    
    <NavBar />
    
    <main class="appMain">
      <router-view />
    </main>
  </div>
</template>

<script>
import NavBar from './components/Navigation.vue'

export default {
  name: 'App',
  components: {
    NavBar
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  background-color: #ffffff;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.appHeader {
  text-align: center;
  padding: 30px 20px;
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

#logo {
  font-size: 2.5rem;
  color: #2647e8;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 700;
}

.appMain {
  max-width: 1200px;
  margin: 30px auto;
  padding: 0 20px;
  padding-bottom: 60px;
}
</style>

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router, { setupRouterGuard } from './router'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
setupRouterGuard(pinia)
app.use(router)
app.mount('#app')

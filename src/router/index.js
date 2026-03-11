import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import DashboardView from '../views/DashboardView.vue'
import ProfileView from '../views/ProfileView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import NotFound from '../views/NotFound.vue'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView
  },
  {
    path: '/tasks',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard/:filter?',
    name: 'Dashboard',
    component: DashboardView,
    props: true,
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: ProfileView,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export function setupRouterGuard(pinia) {
  router.beforeEach((to, _from, next) => {
    const requiresAuth = to.matched.some(r => r.meta.requiresAuth)
    if (requiresAuth && pinia) {
      const auth = useAuthStore(pinia)
      if (!auth.isAuthenticated) {
        next({ name: 'Login' })
        return
      }
    }
    next()
  })
}

export default router


import { createApp } from 'vue'
import App from './App.vue'
import Login from './pages/Login.vue'
import Home from './pages/Home.vue'
import { createWebHistory, createRouter } from 'vue-router'
import Usuarios from './pages/Usuarios.vue'
import Ambulancias from './pages/Ambulancias.vue'
import Motoristas from './pages/Motoristas.vue'
import HealthAgent from './pages/HealthAgent.vue'
import HealthUnit from './pages/HealthUnit.vue'
import Roles from './pages/Roles.vue'
import Areas from './pages/Areas.vue'
import Territorio from './pages/Territorio.vue'
import Vulnerabilidades from './pages/Vulnerabilidades.vue'
import HealthService from './pages/HealthService.vue'
import RoutePlan from './pages/RoutePlan.vue'
import { createPinia } from 'pinia'

const routes = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        component: Login,
    },
    {
        path: '/home',
        component: Home,
    },
    {
        path: '/usuarios',
        component: Usuarios,
    },
    {
        path: '/ambulancias',
        component: Ambulancias,
    },
    {
        path: '/motoristas',
        component: Motoristas,
    },
    {
        path: '/motoritas',
        redirect: '/motoristas',
    },
    {
        path: '/agentes',
        component: HealthAgent,
    },
    {
        path: '/unidades',
        component: HealthUnit,
    },
    {
        path: '/papeis',
        component: Roles,
    },
    {
        path: '/areas',
        component: Areas,
    },
    {
        path: '/territorios',
        component: Territorio,
    },
    {
        path: '/vulnerabilidades',
        component: Vulnerabilidades,
    },
    {
        path: '/servicos',
        component: HealthService,
    },
    {
        path: '/planos',
        component: RoutePlan,
    },
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
})

const app = createApp(App)
const pinia = createPinia()

app.use(router)
app.use(pinia)

app.mount('#app')

import { createApp } from 'vue'
import App from './App.vue'
import Login from './pages/Login.vue'
import { createWebHistory, createRouter } from 'vue-router'
import Usuarios from './pages/Usuarios.vue'
import Ambulancias from './pages/Ambulancias.vue'
import Motoristas from './pages/Motoristas.vue'
import HealthAgent from './pages/HealthAgent.vue'
import HealthUnit from './pages/HealthUnit.vue'
import Roles from './pages/Roles.vue'
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
        path: '/usuarios',
        component: Usuarios
    },
    {
        path: '/ambulancias',
        component: Ambulancias
    },
    {
        path: '/motoritas',
        component: Motoristas
    },
    {
        path: '/agentes',
        component: HealthAgent
    },
    {
        path: '/unidades',
        component: HealthUnit
    },
    {
        path: '/papeis',
        component: Roles
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
});

const app = createApp(App)
const pinia = createPinia();

app.use(router)
app.use(pinia)

app.mount('#app')

import { createApp } from 'vue'
import App from './App.vue'
import Login from './pages/Login.vue'
import { createWebHistory, createRouter } from 'vue-router'
import Usuarios from './pages/Usuarios.vue'

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
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
});

const app = createApp(App)

app.use(router)

app.mount('#app')

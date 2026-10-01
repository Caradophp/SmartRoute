import { createApp } from 'vue'
import App from './App.vue'
import Login from './pages/Login.vue'
import { createMemoryHistory, createRouter } from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        component: Login,
    },
]

export const router = createRouter({
    history: createMemoryHistory(),
    routes,
});

const app = createApp(App)

app.use(router)

app.mount('#app')

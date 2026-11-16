
<script setup>
import logo from '../assets/img/logo.png'
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const showHeader = computed(() => route.path !== '/login')

const links = [
  { nome: 'Home', caminho: '/home', icone: '⌂' },
  { nome: 'Ambulâncias', caminho: '/ambulancias', icone: '🚑' },
  { nome: 'Agentes de Saúde', caminho: '/agentes', icone: '♧' },
  { nome: 'Áreas', caminho: '/areas', icone: '▤' }
]
</script>

<template>
  <header v-if="showHeader" class="smart-header">
    <RouterLink to="/home" class="brand">
      <img :src="logo" alt="SmartRoute" class="brand-logo" />
      <div class="brand-info">
        <strong>Smart<span>Route</span></strong>
        <small>Gestão de Rotas em Saúde</small>
      </div>
    </RouterLink>

    <nav class="header-nav" aria-label="Menu principal">
      <RouterLink
        v-for="link in links"
        :key="link.caminho"
        :to="link.caminho"
        class="nav-link"
        :class="{ active: route.path === link.caminho }"
      >
        <span class="nav-icon">{{ link.icone }}</span>
        {{ link.nome }}
      </RouterLink>
    </nav>

    <div class="header-user">
      <span class="user-avatar">Y</span>
      <span class="user-name">Yasmim</span>
    </div>
  </header>
</template>

<style scoped>
.smart-header {
  width: 100%;
  min-height: 90px;
  padding: 15px clamp(20px, 4vw, 70px);
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
  background: #ffffff;
  box-shadow: 0 3px 18px rgba(20, 65, 120, 0.07);
  box-sizing: border-box;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  text-decoration: none;
  flex-shrink: 0;
}

.brand-logo {
  width: 55px;
  height: 55px;
  object-fit: contain;
}

.brand-info {
  display: flex;
  flex-direction: column;
}

.brand-info strong {
  color: #07346e;
  font-size: 25px;
  font-weight: 800;
  line-height: 1.2;
}

.brand-info strong span {
  color: #1595dc;
}

.brand-info small {
  color: #7285a0;
  font-size: 11px;
}

.header-nav {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 9px;
  padding: 12px 17px;
  border-radius: 12px;
  color: #173b70;
  font-size: 14px;
  font-weight: 600;
  text-decoration: none;
  transition: background 0.2s, color 0.2s;
}

.nav-link:hover,
.nav-link.active {
  background: #edf4fb;
  color: #0755a8;
}

.nav-icon {
  font-size: 19px;
}

.header-user {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;
}

.user-avatar {
  display: grid;
  place-items: center;
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background: #dceeff;
  color: #0755a8;
  font-weight: 800;
  font-size: 18px;
}

.user-name {
  color: #173b70;
  font-weight: 700;
  font-size: 14px;
}

@media (max-width: 1050px) {
  .smart-header {
    flex-wrap: wrap;
  }

  .header-nav {
    order: 3;
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 600px) {
  .smart-header {
    padding: 15px;
  }

  .brand-info strong {
    font-size: 20px;
  }

  .header-user {
    margin-left: auto;
  }

  .user-name {
    display: none;
  }

  .header-nav {
    justify-content: flex-start;
    gap: 4px;
  }

  .nav-link {
    padding: 9px;
    font-size: 12px;
  }
}
</style>


<script setup>
import { computed, ref } from 'vue'

const nomeUsuario = ref('Yasmim')

const saudacao = computed(() => {
  const hora = new Date().getHours()

  if (hora < 12) return 'Bom dia'
  if (hora < 18) return 'Boa tarde'
  return 'Boa noite'
})
</script>

<template>
  <main class="dashboard">
    <section class="welcome-banner">
      <div class="welcome-content">
        <span class="welcome-label">
          PAINEL ADMINISTRATIVO
        </span>

        <h1>{{ saudacao }}, {{ nomeUsuario }}! 👋</h1>

        <p>
          Bem-vinda ao SmartRoute. Gerencie ambulâncias,
          equipes de saúde e atendimentos em um só lugar.
        </p>

        <div class="welcome-actions">
          <RouterLink to="/ambulancias" class="primary-button">
            Ver ambulâncias
            <span aria-hidden="true">→</span>
          </RouterLink>
        </div>
      </div>

    </section>

    <section class="dashboard-intro">
      <div>
        <h2>Visão geral</h2>
        <p>Acompanhe as informações do sistema em tempo real.</p>
      </div>

      <span class="status-label">
        <span class="status-dot"></span>
        Painel SmartRoute
      </span>
    </section>

    
<section class="stats-grid">
  <article class="stat-card stat-blue">
    <div class="stat-top">
      <div class="stat-icon blue">🚑</div>
      <div class="stat-info">
        <span class="stat-title">Ambulâncias</span>
        <strong>—</strong>
      </div>
    </div>
    <span class="stat-badge badge-blue">Aguardando dados</span>
  </article>

  <article class="stat-card stat-green">
    <div class="stat-top">
      <div class="stat-icon green">👥</div>
      <div class="stat-info">
        <span class="stat-title">Agentes de saúde</span>
        <strong>—</strong>
      </div>
    </div>
    <span class="stat-badge badge-green">Aguardando dados</span>
  </article>

  <article class="stat-card stat-purple">
    <div class="stat-top">
      <div class="stat-icon purple">📍</div>
      <div class="stat-info">
        <span class="stat-title">Áreas atendidas</span>
        <strong>—</strong>
      </div>
    </div>
    <span class="stat-badge badge-purple">Aguardando dados</span>
  </article>

  <article class="stat-card stat-orange">
    <div class="stat-top">
      <div class="stat-icon orange">📋</div>
      <div class="stat-info">
        <span class="stat-title">Ocorrências</span>
        <strong>—</strong>
      </div>
    </div>
    <span class="stat-badge badge-orange">Aguardando dados</span>
  </article>
</section>


    <section class="dashboard-panel">
      <div class="panel-heading">
        <div>
          <h2>Ocorrências recentes</h2>
          <p>Visualize os últimos atendimentos registrados.</p>
        </div>
      </div>

      <div class="empty-state">
        <span>📋</span>
        <h3>Nenhuma informação carregada</h3>
        <p>
          As ocorrências serão exibidas aqui quando
          conectarmos o painel ao backend.
        </p>
      </div>
    </section>
  </main>
</template>

<style scoped>
.dashboard {
  min-height: 100vh;
  padding: 32px clamp(20px, 5vw, 80px) 140px;
  background: #f5f8fd;
  color: #17315c;
}


.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 22px;
}

.stat-card {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 156px;
  padding: 22px 24px;
  border: 1px solid;
  border-radius: 22px;
  box-shadow: 0 8px 22px rgba(20, 65, 120, 0.07);
  transition: transform 0.25s, box-shadow 0.25s;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 14px 28px rgba(20, 65, 120, 0.12);
}

.stat-blue {
  background: linear-gradient(135deg, #f0f8ff, #eaf4ff);
  border-color: #b5d9ff;
}

.stat-green {
  background: linear-gradient(135deg, #f1fcf7, #e9f9f1);
  border-color: #bcebd4;
}

.stat-purple {
  background: linear-gradient(135deg, #f8f5ff, #f1edff);
  border-color: #d7c6ff;
}

.stat-orange {
  background: linear-gradient(135deg, #fffaf5, #fff2e9);
  border-color: #ffdab8;
}

.stat-top {
  display: flex;
  align-items: flex-start;
  gap: 18px;
}

.stat-icon {
  display: grid;
  place-items: center;
  flex-shrink: 0;
  width: 66px;
  height: 66px;
  border-radius: 18px;
  font-size: 30px;
}

.blue {
  background: #cde8ff;
}

.green {
  background: #cef5e1;
}

.purple {
  background: #e4d7ff;
}

.orange {
  background: #ffe3ca;
}

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-width: 0;
}

.stat-title {
  color: #16335d;
  font-size: 15px;
  font-weight: 600;
}

.stat-info strong {
  color: #092653;
  font-size: 36px;
  font-weight: 800;
  line-height: 1.1;
}

.stat-badge {
  align-self: flex-end;
  margin-top: 14px;
  padding: 7px 11px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 700;
}

.badge-blue {
  background: #d8eaff;
  color: #14549b;
}

.badge-green {
  background: #cef5dd;
  color: #087d3a;
}

.badge-purple {
  background: #e5dcff;
  color: #6941bd;
}

.badge-orange {
  background: #ffe3cc;
  color: #b75a15;
}

@media (max-width: 1150px) {
  .stats-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 600px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
}


.welcome-banner {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 24px;

  min-height: 300px;
  padding: 42px 48px;

  overflow: hidden;
  border-radius: 24px;

  background-image:
    linear-gradient(
      90deg,
      rgba(3, 40, 103, 0.35) 0%,
      rgba(3, 40, 103, 0.12) 42%,
      transparent 70%
    ),
    url('../assets/img/banner-home.png');

  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;

  color: white;
  box-shadow: 0 14px 32px rgba(15, 75, 145, 0.16);
}

.welcome-banner::after {
  content: '';
  position: absolute;
  width: 340px;
  height: 340px;
  right: -100px;
  top: -150px;
  border: 55px solid rgba(255, 255, 255, .08);
  border-radius: 50%;
}

.welcome-content {
  position: relative;
  z-index: 1;
  max-width: 650px;
}

.welcome-label {
  display: inline-block;
  padding: 7px 12px;
  border: 1px solid rgba(255, 255, 255, .3);
  border-radius: 30px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 1.4px;
}

.welcome-content h1 {
  margin: 20px 0 12px;
  font-size: clamp(28px, 3vw, 40px);
  font-weight: 800;
  line-height: 1.2;
}

.welcome-content p {
  max-width: 550px;
  color: #e3efff;
  line-height: 1.7;
}

.welcome-actions {
  margin-top: 26px;
}

.primary-button {
  display: inline-flex;
  align-items: center;
  gap: 18px;
  padding: 12px 20px;
  border-radius: 10px;
  background: white;
  color: #0752a5;
  font-weight: 700;
  text-decoration: none;
  transition: transform .2s, box-shadow .2s;
}

.primary-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, .12);
}

.dashboard-intro {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  margin: 36px 0 22px;
}

.dashboard h2 {
  margin: 0 0 7px;
  font-size: 23px;
  font-weight: 800;
}

.dashboard-intro p,
.panel-heading p {
  color: #73829a;
  font-size: 14px;
}

.status-label {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #60738d;
  font-size: 13px;
}

.status-dot {
  width: 9px;
  height: 9px;
  border-radius: 50%;
  background: #2674d6;
}

.dashboard-panel {
  margin-top: 28px;
  padding: 28px;
  border: 1px solid #e8eef7;
  border-radius: 18px;
  background: white;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 240px;
  text-align: center;
}

.empty-state span {
  font-size: 42px;
}

.empty-state h3 {
  margin: 15px 0 8px;
  font-size: 17px;
  font-weight: 700;
}

.empty-state p {
  max-width: 370px;
  color: #8390a4;
  font-size: 14px;
  line-height: 1.6;
}

@media (max-width: 950px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .welcome-illustration {
    display: none;
  }
}

@media (max-width: 600px) {
  .dashboard {
    padding: 20px 16px 130px;
  }

  .welcome-banner {
    padding: 30px 24px;
  }

  .stats-grid {
    grid-template-columns: 1fr;
  }

  .dashboard-intro {
    align-items: flex-start;
    flex-direction: column;
  }
}
</style>

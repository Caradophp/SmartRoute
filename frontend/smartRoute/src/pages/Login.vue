<template>
  <main class="login-page">
    <section class="login-container">

      <!-- LADO ESQUERDO -->
      <div class="login-left">

        <!-- LOGO -->
        <div class="logo-wrapper">
          <img
            :src="logoSmartRoute"
            alt="SmartRoute"
            class="logo"
          />
        </div>

        <!-- CONTEÚDO DO LOGIN -->
        <div class="login-content">

          <div class="login-header">
            <h1>Bem-vindo!</h1>

            <p class="login-subtitle">
              Acesse o SmartRoute para gerenciar usuários, rotas e atendimentos.
            </p>
          </div>

          <form class="login-form" @submit.prevent="login">

            <!-- E-MAIL -->
            <div class="form-group">
              <label for="email">E-mail</label>

              <div class="input-wrapper">
                <span class="input-icon email-icon">✉</span>

                <input
                  id="email"
                  v-model="email"
                  type="email"
                  placeholder="Digite seu e-mail"
                  autocomplete="email"
                  required
                />
              </div>
            </div>

            <!-- SENHA -->
            <div class="form-group">
              <label for="senha">Senha</label>

              <div class="input-wrapper">
                <span class="input-icon">🔒</span>

                <input
                  id="senha"
                  v-model="senha"
                  :type="mostrarSenha ? 'text' : 'password'"
                  placeholder="Digite sua senha"
                  autocomplete="current-password"
                  required
                />

                <button
                  type="button"
                  class="password-toggle"
                  aria-label="Mostrar ou ocultar senha"
                  @click="mostrarSenha = !mostrarSenha"
                >
                  {{ mostrarSenha ? '◉' : '⊙' }}
                </button>
              </div>
            </div>

            <!-- ESQUECEU A SENHA -->
            <div class="forgot-wrapper">
              <a
                href="#"
                class="forgot-password"
                @click.prevent="recuperarSenha"
              >
                Esqueceu sua senha?
              </a>
            </div>

            <!-- BOTÃO -->
            <button
              class="login-button"
              type="submit"
              :disabled="carregando"
            >
              <span>
                {{ carregando ? 'Entrando...' : 'Acessar' }}
              </span>

              <span
                v-if="!carregando"
                class="button-arrow"
              >
                →
              </span>
            </button>

          </form>
        </div>
      </div>

      <!-- LADO DIREITO -->
      <div class="login-right">

        <div class="illustration-circle"></div>

        <img
          :src="imagemRota"
          alt="Sistema inteligente de rotas médicas"
          class="route-image"
        />

      </div>

    </section>
  </main>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import logoSmartRoute from '@/assets/img/logo.png'
import imagemRota from '@/assets/img/rota-medica.png'

const router = useRouter()

const email = ref('')
const senha = ref('')
const mostrarSenha = ref(false)
const carregando = ref(false)

/*
 * Mantenha aqui a mesma chamada de API que você já estava
 * utilizando no seu Login.vue caso ela seja diferente.
 */
const login = async () => {
  if (!email.value || !senha.value) {
    return
  }

  carregando.value = true

  try {
    const response = await fetch('http://localhost:8080/login', {
      method: 'POST',

      headers: {
        'Content-Type': 'application/json'
      },

      body: JSON.stringify({
        email: email.value,
        senha: senha.value
      })
    })

    if (!response.ok) {
      throw new Error('E-mail ou senha inválidos')
    }

    const data = await response.json()

    if (data.token) {
      localStorage.setItem('token', data.token)
    }

    await router.push('/usuarios')

  } catch (error) {
    console.error('Erro ao realizar login:', error)

    alert('Não foi possível realizar o login. Verifique seu e-mail e senha.')

  } finally {
    carregando.value = false
  }
}

const recuperarSenha = () => {
  /*
   * Se você já possui uma rota específica para recuperação,
   * troque "/recuperar-senha" pela rota existente.
   */
  router.push('/recuperar-senha')
}
</script>

<style scoped>

/* =========================================================
   TELA
========================================================= */

.login-page {
  width: 100%;
  min-height: 100vh;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 32px;

  box-sizing: border-box;

  overflow: hidden;

  background:
    radial-gradient(
      circle at top left,
      rgba(68, 132, 235, 0.10) 0,
      rgba(68, 132, 235, 0.10) 150px,
      transparent 151px
    ),
    radial-gradient(
      circle at bottom right,
      rgba(68, 132, 235, 0.08) 0,
      rgba(68, 132, 235, 0.08) 170px,
      transparent 171px
    ),
    #f3f8ff;

  font-family:
    Inter,
    -apple-system,
    BlinkMacSystemFont,
    "Segoe UI",
    Roboto,
    Helvetica,
    Arial,
    sans-serif;
}


/* =========================================================
   CONTAINER PRINCIPAL
========================================================= */

.login-container {
  width: min(1120px, 92vw);
  height: min(650px, 84vh);
  min-height: 590px;

  display: grid;

  grid-template-columns: 46% 54%;

  background: #ffffff;

  border-radius: 26px;

  overflow: hidden;

  border: 1px solid rgba(39, 108, 225, 0.16);

  box-shadow:
    0 28px 65px rgba(25, 73, 145, 0.15),
    0 10px 25px rgba(25, 73, 145, 0.08);
}


/* =========================================================
   LADO ESQUERDO
========================================================= */

.login-left {
  position: relative;

  display: flex;
  flex-direction: column;

  padding: 36px 58px;

  box-sizing: border-box;

  background: #ffffff;
}


/* =========================================================
   LOGO
========================================================= */

.logo-wrapper {
  height: 135px;

  display: flex;
  align-items: center;
  justify-content: center;

  flex-shrink: 0;
}

.logo {
  display: block;

  width: 190px;
  max-height: 135px;

  object-fit: contain;
}


/* =========================================================
   CONTEÚDO
========================================================= */

.login-content {
  width: 100%;

  flex: 1;

  display: flex;
  flex-direction: column;
  justify-content: center;

  box-sizing: border-box;

  padding-bottom: 2px;
}


/* =========================================================
   CABEÇALHO
========================================================= */

.login-header {
  margin-bottom: 25px;
}

.login-header h1 {
  margin: 0 0 9px;

  color: #092d66;

  font-size: 36px;
  font-weight: 750;

  line-height: 1.1;

  letter-spacing: -0.8px;
}

.login-subtitle {
  max-width: 390px;

  margin: 0;

  color: #7184a3;

  font-size: 15px;
  font-weight: 400;

  line-height: 1.55;
}


/* =========================================================
   FORMULÁRIO
========================================================= */

.login-form {
  width: 100%;
}

.form-group {
  width: 100%;

  margin-bottom: 17px;
}

.form-group label {
  display: block;

  margin-bottom: 8px;

  color: #0a2d60;

  font-size: 14px;
  font-weight: 650;
}


/* =========================================================
   INPUT
========================================================= */

.input-wrapper {
  position: relative;

  width: 100%;

  display: flex;
  align-items: center;
}

.input-wrapper input {
  width: 100%;
  height: 54px;

  box-sizing: border-box;

  padding: 0 48px 0 52px;

  border: 1px solid #d7e2f1;

  border-radius: 12px;

  outline: none;

  background: #f9fbff;

  color: #163965;

  font-family: inherit;

  font-size: 15px;
  font-weight: 400;

  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    background-color 0.2s ease;
}

.input-wrapper input::placeholder {
  color: #9eacc1;

  opacity: 1;
}

.input-wrapper input:hover {
  border-color: #bfd0e7;
}

.input-wrapper input:focus {
  border-color: #2d6bed;

  background: #ffffff;

  box-shadow:
    0 0 0 3px rgba(45, 107, 237, 0.10);
}


/* =========================================================
   ÍCONES
========================================================= */

.input-icon {
  position: absolute;

  left: 18px;

  z-index: 2;

  display: flex;
  align-items: center;
  justify-content: center;

  color: #2c6bed;

  font-size: 15px;

  pointer-events: none;
}

.email-icon {
  color: #2c6bed;
}


/* =========================================================
   MOSTRAR SENHA
========================================================= */

.password-toggle {
  position: absolute;

  right: 16px;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 28px;
  height: 28px;

  padding: 0;

  border: none;

  background: transparent;

  color: #8197b4;

  font-size: 18px;

  cursor: pointer;

  transition: color 0.2s ease;
}

.password-toggle:hover {
  color: #2c6bed;
}


/* =========================================================
   ESQUECEU A SENHA
========================================================= */

.forgot-wrapper {
  width: 100%;

  display: flex;
  justify-content: flex-end;

  margin-top: -2px;
  margin-bottom: 21px;
}

.forgot-password {
  color: #1761e8;

  font-size: 13px;
  font-weight: 600;

  text-decoration: none;

  transition: color 0.2s ease;
}

.forgot-password:hover {
  color: #0c4fd0;

  text-decoration: underline;
}


/* =========================================================
   BOTÃO
========================================================= */

.login-button {
  position: relative;

  width: 100%;
  height: 54px;

  display: flex;
  align-items: center;
  justify-content: center;

  border: none;

  border-radius: 12px;

  background:
    linear-gradient(
      90deg,
      #2c65df 0%,
      #287af5 100%
    );

  color: #ffffff;

  font-family: inherit;

  font-size: 15px;
  font-weight: 700;

  cursor: pointer;

  box-shadow:
    0 8px 18px rgba(38, 103, 225, 0.20);

  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease,
    filter 0.2s ease;
}

.login-button:hover:not(:disabled) {
  transform: translateY(-1px);

  filter: brightness(1.025);

  box-shadow:
    0 12px 24px rgba(38, 103, 225, 0.25);
}

.login-button:active:not(:disabled) {
  transform: translateY(0);
}

.login-button:disabled {
  opacity: 0.65;

  cursor: not-allowed;
}

.button-arrow {
  position: absolute;

  right: 42%;

  transform: translateX(55px);

  font-size: 18px;
  font-weight: 400;
}


/* =========================================================
   LADO DIREITO
========================================================= */

.login-right {
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 42px;

  box-sizing: border-box;

  overflow: hidden;

  background:
    linear-gradient(
      135deg,
      #edf8ff 0%,
      #dcefff 100%
    );
}


/* =========================================================
   CÍRCULO ATRÁS DA ILUSTRAÇÃO
========================================================= */

.illustration-circle {
  position: absolute;

  width: 500px;
  height: 500px;

  border-radius: 50%;

  background:
    rgba(255, 255, 255, 0.42);
}


/* =========================================================
   ILUSTRAÇÃO
========================================================= */

.route-image {
  position: relative;

  z-index: 1;

  display: block;

  width: 94%;
  max-width: 590px;

  height: auto;

  object-fit: contain;

  filter:
    drop-shadow(
      0 14px 20px rgba(31, 91, 168, 0.08)
    );
}


/* =========================================================
   RESPONSIVIDADE
========================================================= */

@media (max-width: 1100px) {

  .login-container {
    width: min(1000px, 94vw);

    grid-template-columns: 48% 52%;
  }

  .login-left {
    padding-left: 45px;
    padding-right: 45px;
  }

  .route-image {
    width: 100%;
  }
}


@media (max-width: 850px) {

  .login-page {
    padding: 24px;
    overflow: auto;
  }

  .login-container {
    width: min(520px, 94vw);
    height: auto;
    min-height: auto;

    display: block;

    border-radius: 22px;
  }

  .login-left {
    min-height: 620px;

    padding: 36px 46px;
  }

  .login-right {
    display: none;
  }

  .logo-wrapper {
    height: 135px;
  }

  .login-content {
    justify-content: center;
  }
}


@media (max-width: 520px) {

  .login-page {
    padding: 15px;
  }

  .login-container {
    width: 100%;

    border-radius: 20px;
  }

  .login-left {
    min-height: 600px;

    padding: 28px 26px;
  }

  .logo-wrapper {
    height: 125px;
  }

  .logo {
    width: 175px;
  }

  .login-header h1 {
    font-size: 32px;
  }

  .login-subtitle {
    font-size: 14px;
  }
}

</style>
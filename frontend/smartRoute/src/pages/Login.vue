<template>
  <main class="login-page">
    <section class="login-container">

      <!-- LADO ESQUERDO -->
      <div class="login-left">

        <!-- LOGO -->
        <div class="logo-area">
          <img
            :src="logoSmartRoute"
            alt="SmartRoute"
            class="logo"
          />
        </div>

        <!-- CONTEÚDO -->
        <div class="login-content">
          <div class="welcome">
            <h1>Bem-vindo!</h1>
            <p>
              Acesse o SmartRoute para gerenciar usuários, rotas e
              atendimentos.
            </p>
          </div>

          <form @submit.prevent="login" class="login-form">

            <!-- E-MAIL -->
            <div class="form-group">
              <label for="email">E-mail</label>

              <div class="input-wrapper">
                <span class="input-icon">✉</span>

                <input
                  id="email"
                  v-model="email"
                  type="email"
                  placeholder="Digite seu e-mail"
                  required
                />
              </div>
            </div>

            <!-- SENHA -->
            <div class="form-group">
              <label for="senha">Senha</label>

              <div class="input-wrapper">
                <span class="input-icon lock">🔒</span>

                <input
                  id="senha"
                  v-model="senha"
                  :type="mostrarSenha ? 'text' : 'password'"
                  placeholder="Digite sua senha"
                  required
                />

                <button
                  type="button"
                  class="eye-button"
                  @click="mostrarSenha = !mostrarSenha"
                >
                  ◉
                </button>
              </div>
            </div>

            <div class="forgot-area">
              <a href="#" @click.prevent="esqueciSenha">
                Esqueceu sua senha?
              </a>
            </div>

            <button type="submit" class="login-button">
              <span>Acessar</span>
              <span class="arrow">→</span>
            </button>

          </form>
        </div>
      </div>

      <!-- LADO DIREITO -->
      <div class="login-right">
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
import { ref } from "vue";
import { useRouter } from "vue-router";

import logoSmartRoute from "@/assets/img/logo.png";

/*
  IMPORTANTE:
  deixe aqui EXATAMENTE o nome que está no seu arquivo.
  Se você renomeou a imagem para rota-medica.png,
  use esta linha:
*/
import imagemRota from "@/assets/img/rota-medica.png";

const router = useRouter();

const email = ref("");
const senha = ref("");
const mostrarSenha = ref(false);

const login = async () => {
  try {
    const response = await fetch("http://localhost:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email.value,
        senha: senha.value,
      }),
    });

    if (!response.ok) {
      throw new Error("E-mail ou senha inválidos");
    }

    const data = await response.json();

    localStorage.setItem("token", data.token);

    router.push("/usuarios");
  } catch (error) {
    alert(error.message);
  }
};

const esqueciSenha = () => {
  router.push("/recuperar-senha");
};
</script>

<style scoped>
/* ================================
   PÁGINA
================================ */

.login-page {
  width: 100%;
  min-height: 100vh;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 55px 30px;

  box-sizing: border-box;

  background:
    radial-gradient(
      circle at 4% 2%,
      rgba(91, 151, 255, 0.10) 0,
      rgba(91, 151, 255, 0.10) 120px,
      transparent 121px
    ),
    radial-gradient(
      circle at 100% 100%,
      rgba(91, 151, 255, 0.08) 0,
      rgba(91, 151, 255, 0.08) 160px,
      transparent 161px
    ),
    #f4f8ff;
}


/* ================================
   CONTAINER PRINCIPAL
================================ */

.login-container {
  width: min(1180px, 92vw);
  height: 675px;

  display: grid;
  grid-template-columns: 46% 54%;

  background: #ffffff;

  border-radius: 25px;
  overflow: hidden;

  box-shadow:
    0 18px 45px rgba(32, 75, 145, 0.10);

  border: 1px solid rgba(203, 220, 244, 0.8);
}


/* ================================
   LADO ESQUERDO
================================ */

.login-left {
  background: #ffffff;

  padding: 54px 62px 45px;

  display: flex;
  flex-direction: column;

  box-sizing: border-box;
}


/* ================================
   LOGO
================================ */

.logo-area {
  height: 175px;

  display: flex;
  justify-content: center;
  align-items: flex-start;

  flex-shrink: 0;
}

.logo {
  width: 190px;
  height: auto;

  display: block;
  object-fit: contain;
}


/* ================================
   BLOCO DE LOGIN
================================ */

.login-content {
  /*
    Aqui está a principal mudança:
    o conteúdo sobe sem alterar a logo
    nem o tamanho geral do container.
  */
  transform: translateY(-18px);
}


/* ================================
   BEM-VINDO
================================ */

.welcome {
  margin-bottom: 28px;
}

.welcome h1 {
  margin: 0 0 10px;

  color: #082d68;

  font-size: 39px;
  line-height: 1.08;
  font-weight: 750;

  letter-spacing: -1.2px;
}

.welcome p {
  max-width: 410px;

  margin: 0;

  color: #6f83a5;

  font-size: 15.5px;
  line-height: 1.55;

  font-weight: 400;
}


/* ================================
   FORMULÁRIO
================================ */

.login-form {
  width: 100%;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;

  margin-bottom: 9px;

  color: #092b59;

  font-size: 14px;
  font-weight: 700;
}


/* ================================
   INPUTS
================================ */

.input-wrapper {
  width: 100%;
  height: 59px;

  display: flex;
  align-items: center;

  position: relative;

  background: #f8faff;

  border: 1px solid #d7e1f0;
  border-radius: 12px;

  box-sizing: border-box;

  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    background 0.2s ease;
}

.input-wrapper:focus-within {
  background: #ffffff;

  border-color: #2868f0;

  box-shadow:
    0 0 0 3px rgba(40, 104, 240, 0.09);
}

.input-icon {
  width: 55px;

  display: flex;
  align-items: center;
  justify-content: center;

  color: #2465ef;

  font-size: 18px;

  flex-shrink: 0;
}

.input-icon.lock {
  font-size: 15px;
}

.input-wrapper input {
  width: 100%;
  height: 100%;

  padding: 0 48px 0 0;

  border: none;
  outline: none;

  background: transparent;

  color: #153660;

  font-size: 15.5px;
  font-family: inherit;

  box-sizing: border-box;
}

.input-wrapper input::placeholder {
  color: #a2b1c8;
}


/* ================================
   MOSTRAR SENHA
================================ */

.eye-button {
  position: absolute;

  right: 15px;
  top: 50%;

  transform: translateY(-50%);

  width: 30px;
  height: 30px;

  display: flex;
  align-items: center;
  justify-content: center;

  border: none;
  outline: none;

  background: transparent;

  color: #8297b6;

  font-size: 13px;

  cursor: pointer;
}


/* ================================
   ESQUECEU SENHA
================================ */

.forgot-area {
  width: 100%;

  display: flex;
  justify-content: flex-end;

  margin-top: -2px;
  margin-bottom: 22px;
}

.forgot-area a {
  color: #1760ed;

  font-size: 13px;
  font-weight: 500;

  text-decoration: none;
}

.forgot-area a:hover {
  text-decoration: underline;
}


/* ================================
   BOTÃO
================================ */

.login-button {
  width: 100%;
  height: 55px;

  border: none;
  border-radius: 11px;

  background:
    linear-gradient(
      90deg,
      #2860df 0%,
      #2879fa 100%
    );

  color: #ffffff;

  font-size: 15px;
  font-weight: 700;

  display: flex;
  align-items: center;
  justify-content: center;

  gap: 13px;

  cursor: pointer;

  box-shadow:
    0 8px 18px rgba(39, 104, 230, 0.18);

  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease;
}

.login-button:hover {
  transform: translateY(-1px);

  box-shadow:
    0 11px 22px rgba(39, 104, 230, 0.25);
}

.login-button:active {
  transform: translateY(0);
}

.arrow {
  font-size: 19px;
  font-weight: 400;
}


/* ================================
   LADO DIREITO
================================ */

.login-right {
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 45px;

  box-sizing: border-box;

  overflow: hidden;

  background:
    radial-gradient(
      circle at center,
      rgba(255, 255, 255, 0.88) 0%,
      rgba(255, 255, 255, 0.55) 38%,
      transparent 64%
    ),
    linear-gradient(
      135deg,
      #e8f6ff 0%,
      #dff1ff 100%
    );
}

.login-right::before {
  content: "";

  position: absolute;

  width: 480px;
  height: 480px;

  border-radius: 50%;

  background: rgba(255, 255, 255, 0.38);

  z-index: 0;
}


/* ================================
   IMAGEM
================================ */

.route-image {
  position: relative;
  z-index: 1;

  width: 100%;
  max-width: 590px;
  height: auto;

  object-fit: contain;

  display: block;
}


/* ================================
   RESPONSIVIDADE
================================ */

@media (max-width: 1000px) {
  .login-container {
    width: min(600px, 92vw);
    height: auto;

    grid-template-columns: 1fr;
  }

  .login-right {
    display: none;
  }

  .login-left {
    padding: 45px 55px;
  }

  .logo-area {
    height: 160px;
  }

  .login-content {
    transform: translateY(-12px);
  }
}


@media (max-width: 600px) {
  .login-page {
    padding: 20px;
  }

  .login-container {
    width: 100%;

    border-radius: 20px;
  }

  .login-left {
    padding: 35px 28px 40px;
  }

  .logo-area {
    height: 145px;
  }

  .logo {
    width: 165px;
  }

  .login-content {
    transform: translateY(-8px);
  }

  .welcome h1 {
    font-size: 32px;
  }

  .welcome p {
    font-size: 14px;
  }
}
</style>
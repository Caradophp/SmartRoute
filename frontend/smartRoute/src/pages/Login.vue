<script setup>
import RecoveryPassDialog from '@/components/RecoveryPassDialog.vue';
import Toast from '@/utils/Toast';
import axios from 'axios';
import { FwbA, FwbButton } from 'flowbite-vue';
import { inject, onMounted, ref } from 'vue';

const showRecoveryPassForm = ref(false);

const email = ref('');
const senha = ref('');
const userId = ref();

const openLoader = inject("openLoader");
const closeLoader = inject("closeLoader");

const recoveryPass = () => {
    showRecoveryPassForm.value = true;
}

const login = async (event) => {
    event.preventDefault();
    openLoader();
    try {
        const response = await axios.post('http://localhost:8080/login', {
            email: email.value,
            senha: senha.value
        });

        if (response.status !== 200) {
            Toast.danger('Erro ao logar. Contate o suporte técnico');
            return;
        }

        const data = await response.data;
        localStorage.setItem("token", data.token);
        Toast.info('Logado com sucesso. Redirecionando...')
        location.href = '/home';
    } catch (erro) {
        Toast.error(erro)
        closeLoader();
    }
}

const closeModal = () => {
    if (userId.value != null) {
        localStorage.removeItem("token")
        location.href = '/';
    } else {
        showRecoveryPassForm.value = false
    }
}

onMounted(async () => {
    const urlParams = new URLSearchParams(location.search);

    if (urlParams.get("user") !== null) {

        try {
            const response = await axios.get(`http://localhost:8080/login/is-valid?user=${urlParams.get("user")}&expires=${urlParams.get("expires")}&sig=${urlParams.get("sig")}`)

            if (response.status !== 200) {
                Toast.danger("Erro ao validar link. Entre em contato com o suporte técnico.")
                return;
            }

            userId.value = urlParams.get("user");
            showRecoveryPassForm.value = true;
        } catch (error) {
            Toast.error(error)
        }
    }

});
</script>

<template>
    <div>
        <img src="../assets/img/logo.png" alt="Logo" width="250px" height="250px"/>
        <section class="login-panel">
            <form>
                <input type="text" placeholder="Usuário" v-model="email" />
                <input type="password" placeholder="Senha" v-model="senha" />
                <fwb-button type="button" @click="login">Acessar</fwb-button>
                <fwb-a href="#" @click="recoveryPass">Esqueceu sua senha?</fwb-a>
            </form>
        </section>
        <recovery-pass-dialog :show="showRecoveryPassForm" :close="() => closeModal()" :user-id="userId"/>
    </div>   
</template>

<style scoped>
div {
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.login-panel {
    width: 100%;
    max-width: 400px;
    padding: 30px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.login-panel form {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.login-footer {
    background-color: rgb(131, 130, 130);
}
</style>

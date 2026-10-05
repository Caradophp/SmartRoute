<script setup lang="ts">
import RecoveryPassDialog from '@/components/RecoveryPassDialog.vue';
import Toast from '@/utils/Toast';
import axios from 'axios';
import { FwbA, FwbButton } from 'flowbite-vue';
import { ref } from 'vue';

const showRecoveryPassForm = ref(false);

const email = ref('');
const senha = ref('');

const recoveryPass = () => {
    showRecoveryPassForm.value = true;
}

const login = async (event: Event) => {
    event.preventDefault(); 
    try {
        const response = await axios.post('http://localhost:8080/login', {
            email: email.value,
            senha: senha.value
        });

        if (response.status !== 200) {
            Toast.danger('Erro ao logar. Contate o suporte técnico');
            return;
        }

        Toast.info('Logado com sucesso. Redirecionando...')
    } catch (erro) {
        Toast.error(erro)
    }
}
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
        <recovery-pass-dialog :show="showRecoveryPassForm" :close="() => showRecoveryPassForm = false"/>
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

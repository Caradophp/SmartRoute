<script setup>
import { FwbInput } from 'flowbite-vue';
import Dialog from './Dialog.vue';
import { inject, ref } from 'vue';
import Toast from '@/utils/Toast.js';
import axios from 'axios';

const { show, close } = defineProps({
    show: Boolean,
    close: Function
});

const showEmailInput = ref(true);
const showNewPassForm = ref(false);

const userEmail = ref('');
const tipedCode = ref(0);

const openLoader = inject("openLoader");
const closeLoader = inject("closeLoader");

const sendEmail = async () => {
    openLoader();
    try {
        const response = await axios.post('http://localhost:8080/login/email', {
            email: userEmail.value
        });

        if (response.status !== 200) {
            Toast.error('Erro desconhecido. Contate o suporte técnico')
            return;
        }

        Toast.success('E-mail enviado com sucesso. Verifique sua caixa de entrada')
        showEmailInput.value = false;
        closeLoader();
    } catch (error) {
        Toast.error(error)
        closeLoader();
    }
}

const checkCode = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/login/check?code=${tipedCode.value}`,);

        if (!response.status !== 200) {
            Toast.error('Erro desconhecido. Contate o suporte técnico')
            return;
        }

        Toast.success('E-mail enviado com sucesso. Verifique sua caixa de entrada')
        showNewPassForm.value = true;
    } catch (error) {
        Toast.danger(error.message)
    }
}

const changePass = async () => {
    try {
        const response = await axios.patch('http://localhost:8080/login/changePass', {
            newPass: pass,
            confirmPass: confirmPass
        });

        if (!response.status !== 200) {
            Toast.error('Erro desconhecido. Contate o suporte técnico');
            return;
        }

        Toast.info('Senha alterada com sucesso');
        showEmailInput.value = true;
        showNewPassForm.value = false;
        close();
    } catch (error) {
        Toast.danger(error.message)
    }
}

const buttonHandler = () => {
    if (showEmailInput) {
        sendEmail();
    } else if (!showEmailInput && !showNewPassForm) {
        checkCode();
    } else {
        changePass();
    }
}

</script>

<template>
    <Dialog v-if="show" title="Recuperação de senha" @close="close" @submit="buttonHandler">
        <form v-if="showEmailInput">
            <span>Informe seu e-mail para poder recuperar a senha</span><br><br>
            <FwbInput autocomplete="off" placeholder="E-mail" v-model="userEmail" />
        </form>
        <form v-if="!showEmailInput && !showNewPassForm">
            <span>Informe o código de verificação envia para seu e-mail</span><br><br>
            <FwbInput autocomplete="off" placeholder="Código" />
        </form>
        <form v-if="showNewPassForm">
            <span>Informe a nova senha abaixo</span><br><br>
            <FwbInput autocomplete="off" placeholder="Senha" /><br>
            <FwbInput autocomplete="off" placeholder="Confirmar Senha" />
        </form>
    </Dialog>
</template>
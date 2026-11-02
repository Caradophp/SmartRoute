<script setup>
import { FwbInput } from 'flowbite-vue';
import Dialog from './Dialog.vue';
import { inject, watch, ref, toRefs } from 'vue';
import Toast from '@/utils/Toast.js';
import axios from 'axios';

const props = defineProps({
    show: Boolean,
    close: Function,
    userId: String
});

const { show, userId } = toRefs(props);

const showEmailInput = ref(true);
const showNewPassForm = ref(false);

const userEmail = ref('');
const tipedCode = ref(0);
const newPass = ref('');
const confirmPass = ref('');

const openLoader = inject("openLoader");
const closeLoader = inject("closeLoader");

const loadUserById = async () => {
    openLoader();
    try {
        const response = await axios.get(`http://localhost:8080/login/${userId.value}`);

        if (response.status !== 200) {
            Toast.danger("Erro ao recuperar e-mail. Contate o suporte técnico");
            return;
        }

        const data = await response.data;
        userEmail.value = data.email;
        showNewPassForm.value = true;
        closeLoader();
    } catch (error) {
        Toast.error(error);
        closeLoader();
    }
}

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
        const response = await axios.post(`http://localhost:8080/login/check`, {
            codigo: tipedCode.value,
            email: userEmail.value
        });

        if (response.status !== 200) {
            Toast.error('Erro desconhecido. Contate o suporte técnico')
            return;
        }

        Toast.success('Código válidado com sucesso. Digite sua nova abaixo')
        showNewPassForm.value = true;
    } catch (error) {
        Toast.danger(error.message)
    }
}

const changePass = async () => {
    try {
        const response = await axios.patch('http://localhost:8080/login/changePass', {
            email: userEmail.value,
            senha: newPass.value,
            confirmar_senha: confirmPass.value
        });

        if (response.status !== 200) {
            Toast.error('Erro desconhecido. Contate o suporte técnico');
            return;
        }

        Toast.info('Senha alterada com sucesso');
        showEmailInput.value = true;
        showNewPassForm.value = false;
        props.close();
    } catch (error) {
        Toast.danger(error.message)
    }
}

const buttonHandler = () => {
    if (showEmailInput.value && userId.value == null) {
        sendEmail();
    } else if (!showEmailInput.value && !showNewPassForm.value) {
        checkCode();
    } else {
        changePass();
    }
}

watch(userId, (newUserId) => {
    if (newUserId) {
        loadUserById();
    }
});

</script>

<template>
    <Dialog v-if="show" title="Recuperação de senha" @close="close" @submit="buttonHandler">
        <form v-if="showEmailInput">
            <span>Informe seu e-mail para poder recuperar a senha</span><br><br>
            <FwbInput autocomplete="off" placeholder="E-mail" v-model="userEmail" :disabled="userId != null ? true : fal" />
        </form>
        <form v-if="!showEmailInput && !showNewPassForm">
            <span>Informe o código de verificação envia para seu e-mail</span><br><br>
            <FwbInput autocomplete="off" placeholder="Código" v-model="tipedCode"/>
        </form>
        <form v-if="showNewPassForm">
            <br><span>Informe a nova senha abaixo</span><br><br>
            <FwbInput :type="'password'" autocomplete="off" placeholder="Senha" v-model="newPass" /><br>
            <FwbInput :type="'password'" autocomplete="off" placeholder="Confirmar Senha" v-model="confirmPass" />
        </form>
    </Dialog>
</template>
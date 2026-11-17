<script setup>
import CrudLayout from '@/components/CrudLayout.vue';
import apiClient from '@/utils/Axios';
import Toast from '@/utils/Toast';
import { onMounted, ref } from 'vue';

const telefone = ref('');
const agent = ref(null);
const agents = ref([]);
const route = ref(null);
const routes = ref([]);

const columns = [
    { data: 'nome', title: 'Agente' },
    { data: 'userEmail', title: 'E-mail' },
    { data: 'telefone', title: 'Contato' }
]

const fields = [
    { label: 'Telefone', fieldType: 'text', model: telefone },
    { label: 'Agênte', fieldType: 'autocomplete', model: agent, options: agents, searchFields: ['nome']},
    { label: 'Rota', fieldType: 'autocomplete', model: route, options: routes, searchFields: ['nome']},
]

const jsonData = () => JSON.stringify({
    telefone: telefone.value,
    userId: agent.value.id,
    routeId: route.value.id
});

const setFormData = (data) => {
    telefone.value = data.telefone;

    agents.value.forEach(ag => {
        if (ag.email == data.email) {
            agent.value = ag;
        }
    });
};

onMounted(() => {
    const getAgents = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/users/listar');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            agents.value = await response.data;

        } catch (error) {
            Toast.error(error);
        }
    }

    const getRoutes = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/route-plans/listar');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            routes.value = await response.data;

        } catch (error) {
            Toast.error(error);
        }
    }

    getAgents();
    getRoutes();
});

</script>

<template>
    <CrudLayout :url="'health-agents'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData"/>
</template>
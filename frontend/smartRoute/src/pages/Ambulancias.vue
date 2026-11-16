<script setup >
import CrudLayout from '@/components/CrudLayout.vue';
import apiClient from '@/utils/Axios';
import Toast from '@/utils/Toast';
import { onMounted, ref } from 'vue';

const placa = ref();
const tipo = ref('');
const agent = ref(null);
const agents = ref([]);
const driver = ref(null)
const drivers = ref([]);

const columns = [
    { data: 'placa', title: 'Placa' },
    { data: 'status', title: 'Status' }
];

const tipoOptions = [
    { value: 'IN_RESCUE', name: 'Em resgate' },
    { value: 'WAITING', name: 'Em espera' },
    { value: 'UNDER_MAINTENANCE', name: 'Em manutenção' },
    { value: 'IN_MOTION', name: 'Em movimento' },
];

const fields = [
    { label: 'Placa', fieldType: 'number', model: placa },
    { label: 'Situação', fieldType: 'select', model: tipo, options: tipoOptions },
    { label: 'Motorista', fieldType: 'autocomplete', model: driver, options: drivers, searchField: ['nome'] },
    { label: 'Agênte', fieldType: 'autocomplete', model: agent, options: agents, searchField: ['nome'] }
];

const jsonData = () => JSON.stringify({
    placa: placa.value,
    status: tipo.value,
    driverId: driver.value.id,
    healthAgentId: agent.value.id
});

const setFormData = (data) => {
    placa.value = data.placa,
    tipo.value = data.tipo
}

onMounted(() => {
    const getDrivers = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/drivers/listar');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            drivers.value = await response.data;

        } catch (error) {
            Toast.error(error);
        }
    }

    const getAgents = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/health-agents/listar');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            agents.value = await response.data;

        } catch (error) {
            Toast.error(error);
        }
    }

    getDrivers();
    getAgents();
});

</script>

<template>
    <CrudLayout :url="'ambulances'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData"/>
</template>
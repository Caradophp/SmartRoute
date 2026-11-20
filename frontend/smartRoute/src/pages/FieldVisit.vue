<script setup >
import CrudLayout from '@/components/CrudLayout.vue';
import { onMounted, ref } from 'vue';
import Toast from '@/utils/Toast';
import apiClient from '@/utils/Axios';

const columns = [
    { data: 'dataVisita', title: 'Data' },
    { data: 'assistedPersonName', title: 'Pessoa Assitida' },
    { data: 'finished', title: 'Finalizado' },
]

const dataVisita = ref('');
const observacoes = ref('')
const assistedPersons = ref([]);
const assistedPerson = ref(null);
const routes = ref([]);
const route = ref(null);

const fields = [
    { label: 'Data da Visita', fieldType: 'date', model: dataVisita },
    { label: 'Pessoa Assitida', fieldType: 'autocomplete', model: assistedPerson, options: assistedPersons, searchFields: ['nome'] },
    { label: 'Rota', fieldType: 'autocomplete', model: route, options: routes, searchFields: ['nome'] },
    { label: 'Observaçoes', fieldType: 'textarea', model: observacoes }
]

const jsonData = () => JSON.stringify({
    dataVisita: dataVisita.value,
    observacao: observacoes.value,
    personId: assistedPerson.value.id,
    routePlanId: route.value.id
});

const setFormData = (data) => {
    dataVisita.value = data.dataVisita,
    observacoes.value = data.observacao
}

onMounted(() => {
    const getPersons = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/assisted-persons/listar');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            assistedPersons.value = await response.data;

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

    getPersons();
    getRoutes();
});

</script>

<template>
    <CrudLayout :url="'field-visits'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData"/>
</template>
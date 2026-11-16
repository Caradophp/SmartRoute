<script setup >
import CrudLayout from '@/components/CrudLayout.vue';
import { ref } from 'vue';

const nome = ref('');
const data = ref('');
const status = ref('');

const columns = [
    { data: 'nome', title: 'Título' },
    { data: 'data', title: 'Data' },
    { data: 'status', title: 'Situação' }
];

const options = [
    { value: 'ACTIVE', name: 'Ativo' },
    { value: 'IN_ACTION', name: 'Em ação' },
    { value: 'STOPED', name: 'Parado' },
    { value: 'CANCELLED', name: 'Cancelado' }
];

const fields = [
    { label: 'Título', fieldType: 'text', model: nome },
    { label: 'Data', fieldType: 'date', model: data },
    { label: 'Situação', fieldType: 'select', model: status, options: options }
];

const jsonData = () => JSON.stringify({
    nome: nome.value,
    data: data.value,
    status: status.value
});

const setFormData = (data) => {
    nome.value = data.nome,
    data.value = data.data,
    status.value = data.status
}

</script>

<template>
    <CrudLayout :url="'route-plans'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData" />
</template>
<script setup>
import CrudLayout from '@/components/CrudLayout.vue';
import { ref } from 'vue';

const nome = ref('');
const tipo = ref('');

const columns = [
    { data: 'name', title: 'Nome' },
    { data: 'tipo', title: 'Tipo' }
]

const options = [
    { value: 'CLINIC', name: 'Clínica'},
    { value: 'HEALTH_CENTER', name: 'Centro de Saúde'},
    { value: 'HOSPITAL', name: 'Hospital'},
];

const fields = [
    { label: 'Nome', fieldType: 'text', model: nome },
    { label: 'Tipo', fieldType: 'select', model: tipo, options: options },
]

const jsonData = () => JSON.stringify({
    name: nome.value,
    tipo: tipo.value
});

const setFormData = (data) => {
    nome.value = data.name,
    tipo.value = data.tipo
}

</script>

<template>
    <CrudLayout :url="'health-unit'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData"/>
</template>
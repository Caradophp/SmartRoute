<script setup >
import CrudLayout from '@/components/CrudLayout.vue';
import { ref } from 'vue';

const nome = ref('');
const tipo = ref('');

const columns = [
    { data: "nomeTerritorio", title: 'Nome'},
    { data: "type", title: 'Tipo' }
];

const options = [
    { value: 'RURAL', name: 'Zona Rural' },
    { value: 'URBAN', name: 'Zona urbâna' },
]

const fields = [
    { label: 'Nome', fieldType: 'text', model: nome },
    { label: 'Tipo', fieldType: 'select', model: tipo, options: options }
];

const jsonData = () => JSON.stringify({
    nomeTerritorio: nome.value,
    type: tipo.value
});

const setFormData = (data) => {
    nome.value = data.nomeTerritorio,
    tipo.value = data.type
} 

</script>

<template>
    <CrudLayout :url="'territories'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData" />
</template>
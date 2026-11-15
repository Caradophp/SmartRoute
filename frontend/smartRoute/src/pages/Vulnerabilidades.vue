<script setup >
import CrudLayout from '@/components/CrudLayout.vue';
import { ref } from 'vue';

const nome = ref('');
const descricao = ref('');

const columns = [
    { data: 'nome', title: 'Nome' },
    { data: 'descricao', title: 'Descrição' },
];

const fields = [
    { label: 'Nome', fieldType: 'text', model: nome },
    { label: 'Descrição', fieldType: 'textarea', model: descricao }
];

const jsonData = () => JSON.stringify({
    nome: nome.value,
    descricao: descricao.value
});

const setFormData = (data) => {
    nome.value = data.nome,
    descricao.value = data.descricao
}

</script>

<template>
    <CrudLayout :url="'vulnerability-types'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData"/>
</template>
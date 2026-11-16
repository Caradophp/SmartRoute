<script setup>
import CrudLayout from '@/components/CrudLayout.vue';
import { ref } from 'vue';

const nome = ref('');

const columns = [
    {data: 'nome', title: 'Nome'}
]

const formFields = [
    {label: "Nome", fieldType: "text", model: nome},
]

const json = () => JSON.stringify({
    nome: nome.value
})

const setFormData = (data) => {
    nome.value = data.nome
}

</script>

<template>
    <CrudLayout :url="'roles'" :columns="columns" :form-fields="formFields" :json-data="json" :set-form-data="setFormData"/>
</template>
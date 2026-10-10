<script setup>
import CrudLayout from '@/components/CrudLayout.vue';
import apiClient from '@/utils/Axios';
import Toast from '@/utils/Toast';
import { onMounted, ref } from 'vue';

// Define as referências
const nome = ref('');
const email = ref('');
const cpf = ref(0);
const tipo = ref(null)
const perfils = ref([]);

// Define as colunas da tabela
const columns = [
  { data: 'nome', title: 'Nome' },
  { data: 'email', title: 'E-mail' },
  { data: 'cpf', title: 'Documento' },
  { data: 'perfil', title: 'Perfil' },
];

// Define campos que podem ser usados para pesquisa no dropdown
const searchFields = ['nome'];

// Define os campos no formulário
const fiels = [
    {label: "Nome", fieldType: "text", model: nome},
    {label: "E-mail", fieldType: "email", model: email},
    {label: "CPF", fieldType: "number", model: cpf},
    {label: "Perfil", fieldType: "autocomplete", model: tipo, options: perfils, searchFields: searchFields}
]

// Cria JSON para enviar para o backend
const json = () => JSON.stringify({
    nome: nome.value,
    email: email.value,
    cpf: cpf.value,
    perfil: tipo.value
})

const setFormData = (data) => {
    nome.value = data.nome,
    email.value = data.email,
    cpf.value = data.cpf
};


//Carrega dados do dropdown
onMounted(() => {
    const getPerfils = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/roles');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            perfils.value = await response.data;

        } catch (error) {
            Toast.error(error);
        }
    }

    getPerfils();
})

</script>

<template>
    <CrudLayout :columns="columns" :form-fields="fiels" :json-data="json" :set-form-data="setFormData" url="users"/>
</template>
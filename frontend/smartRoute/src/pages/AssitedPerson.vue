<script setup >
import CrudLayout from '@/components/CrudLayout.vue';
import apiClient from '@/utils/Axios';
import Toast from '@/utils/Toast';
import { onMounted, ref } from 'vue';

const columns = [
    { data: 'nome', title: 'Nome' },
    { data: 'dataNascimento', title: 'Data de nascimento' },
    { data: 'telefone', title: 'Telefone' },
    { data: 'rua', title: 'Rua' },
    { data: 'numero', title: 'numero' },
    { data: 'bairro', title: 'Bairro' },
    { data: 'cidade', title: 'Cidade' }
];

const nome = ref('');
const dataNascimento = ref('');
const telefone = ref('');
const rua = ref('');
const numero = ref();
const bairro = ref('');
const cidade = ref('');
const vulnerabilities = ref([]);
const vulnerability = ref('');
const territories = ref([]);
const territory = ref('');

const fields = [
    { label: 'Nome', fieldType: 'text', model: nome },
    { label: 'dataNascimento', fieldType: 'date', model: dataNascimento },
    { label: 'Telefone', fieldType: 'number', model: telefone },
    { label: 'Rua', fieldType: 'text', model: rua },
    { label: 'Número', fieldType: 'number', model: numero },
    { label: 'Bairro', fieldType: 'text', model: bairro },
    { label: 'Cidade', fieldType: 'text', model: cidade },
    { label: 'Vulnerabilidade', fieldType: 'autocomplete', model: vulnerability, options: vulnerabilities, searchFields: ['nome']},
    { label: 'Território', fieldType: 'autocomplete', model: territory, options: territories, searchFields: ['nome']}
];

const jsonData = () => JSON.stringify({
    nome: nome.value,
    dataNascimento: dataNascimento.value,
    telefone: telefone.value,
    rua: rua.value,
    numero: numero.value,
    bairro: bairro.value,
    cidade: cidade.value,
    idTerritorio: territory.value.id,
    vulnerabilityId: vulnerability.value.id
});

const setFormData = (data) => {
    nome.value = data.nome;
    dataNascimento.value = data.dataNascimento;
    telefone.value = data.telefone;
    rua.value = data.rua;
    numero.value = data.numero;
    bairro.value = data.bairro;
    cidade.value = data.cidade;
}

onMounted(() => {
    const getTerritories = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/territories/listar');

            if (response.status !== 200) {
                Toast.error('Erro desconhecido. Contate o suporte técnico.')
            } else {
                territories.value = await response.data;
            }

        } catch (error) {
            Toast.danger(error);   
        }
    }

    const getVulnerabilities = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/vulnerability-types/listar');

            if (response.status !== 200) {
                Toast.error('Erro desconhecido. Contate o suporte técnico.')
            } else {
                vulnerabilities.value = await response.data;
            }
        } catch (error) {
            Toast.danger(error);   
        }
    }

    getTerritories();
    getVulnerabilities();
});

</script>

<template>
    <CrudLayout :url="'assisted-persons'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData" />
</template>
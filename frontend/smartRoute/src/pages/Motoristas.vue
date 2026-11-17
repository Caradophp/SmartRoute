<script setup>
import CrudLayout from '@/components/CrudLayout.vue';
import apiClient from '@/utils/Axios';
import Toast from '@/utils/Toast';
import { onMounted, ref } from 'vue';

const users = ref([]);
const user = ref(null)
const licence = ref('');

const columns = [
    { data: 'driverLicenceLevel', title: 'Tipo de Carteira' },
    { data: 'nome', title: 'Motorista' },
    { data: 'driverEmail', title: 'E-mail' }
]

const licenceLevels = [
    {value: 'A', name: 'A'}, 
    {value: 'B', name: 'B'}, 
    {value: 'C', name: 'C'}, 
    {value: 'AB', name: 'AB'}, 
    {value: 'AC', name: 'AC'}, 
    {value: 'ABC', name: 'ABC'}
]

const fields = [
    {label: "Carteira", fieldType: "select", model: licence, options: licenceLevels},
    {label: "Motorista", fieldType: "autocomplete", model: user, options: users, searchFields: ['nome']}
]

const jsonData = () => JSON.stringify({
    driverLicenceLevel: licence.value,
    userId: user.value.id
});

const setFormData = (data) => {
    licence.value = data.driverLicenceLevel;

    users.value.forEach(element => {
        if (element.id === data.userId) {
            user.value = element;
        }
    });

}

onMounted(() => {
    const getUsers = async () => {
        try {
            const response = await apiClient.get('http://localhost:8080/users/listar');

            if (response.status !== 200)
                Toast.danger('Erro desconhecido. Contate o suporte técnico');

            users.value = await response.data;

        } catch (error) {
            Toast.error(error);
        }
    }

    getUsers();
});

</script>

<template>
    <CrudLayout :url="'drivers'" :columns="columns" :form-fields="fields" :json-data="jsonData" :set-form-data="setFormData"/>
</template>
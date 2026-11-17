
<script setup>
import CrudLayout from '@/components/CrudLayout.vue';
import { ref } from 'vue';

const nome = ref('');
const tipo = ref('');
const latitude = ref('');
const longitude = ref('');

const columns = [
    { data: 'name', title: 'Nome' },
    {
        data: 'tipo',
        title: 'Tipo',
        render: (value) => ({
            CLINIC: 'Clínica',
            HEALTH_CENTER: 'Centro de Saúde',
            HOSPITAL: 'Hospital'
        }[value] ?? value)
    },
    { data: 'latitude', title: 'Latitude' },
    { data: 'longitude', title: 'Longitude' }
];

const options = [
    { value: 'CLINIC', name: 'Clínica' },
    { value: 'HEALTH_CENTER', name: 'Centro de Saúde' },
    { value: 'HOSPITAL', name: 'Hospital' }
];

const fields = [
    { label: 'Nome', fieldType: 'text', model: nome },
    { label: 'Tipo', fieldType: 'select', model: tipo, options },
    { label: 'Latitude', fieldType: 'number', model: latitude },
    { label: 'Longitude', fieldType: 'number', model: longitude }
];

const jsonData = () => JSON.stringify({
    name: nome.value,
    tipo: tipo.value,
    latitude: latitude.value === '' ? null : Number(latitude.value),
    longitude: longitude.value === '' ? null : Number(longitude.value)
});

const setFormData = (data) => {
    nome.value = data.name ?? '';
    tipo.value = data.tipo ?? '';
    latitude.value = data.latitude ?? '';
    longitude.value = data.longitude ?? '';
};
</script>

<template>
    <CrudLayout
        :url="'health-unit'"
        :columns="columns"
        :form-fields="fields"
        :json-data="jsonData"
        :set-form-data="setFormData"
    />
</template>

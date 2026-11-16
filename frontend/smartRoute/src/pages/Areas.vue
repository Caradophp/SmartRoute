
<script setup>
import CrudLayout from '@/components/CrudLayout.vue'
import { ref } from 'vue'

const nomeTerritorio = ref('')
const tipo = ref('')

const columns = [
  { data: 'nomeTerritorio', title: 'Nome do território' },
  {
    data: 'type',
    title: 'Tipo',
    render: (value) => {
      const tipos = {
        URBAN: 'Urbana',
        RURAL: 'Rural'
      }

      return tipos[value] ?? value ?? '—'
    }
  }
]

const fields = [
  {
    label: 'Nome do território',
    fieldType: 'text',
    model: nomeTerritorio
  },
  {
  label: 'Tipo',
  fieldType: 'select',
  model: tipo,
  options: [
    { value: 'URBAN', name: 'Urbana' },
    { value: 'RURAL', name: 'Rural' }
  ]
}
]

const jsonData = () => JSON.stringify({
  nomeTerritorio: nomeTerritorio.value,
  type: tipo.value
})

const setFormData = (data) => {
  nomeTerritorio.value = data.nomeTerritorio
  tipo.value = data.type
}
</script>

<template>
  <CrudLayout
    url="territories"
    :columns="columns"
    :form-fields="fields"
    :json-data="jsonData"
    :set-form-data="setFormData"
  />
</template>

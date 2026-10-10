<script setup>
import { FwbButton, FwbInput } from 'flowbite-vue';
import { defineEmits } from 'vue';

const props = defineProps({
    novoClick: {
        type: Function,
        required: true
    },
    pesquisaClick: {
        type: Function,
        required: true
    },
    inputSearch: {
        type: String,
        required: true
    }
});

const emit = defineEmits(['update:inputSearch']);
</script>

<template>
    <div class="container">
        <section>
            <FwbInput placeholder="Pesquisar..." type="search" :model-value="props.inputSearch" @update:model-value="emit('update:inputSearch', $event)" />
            <FwbButton @click="pesquisaClick"><i class="fa-solid fa-magnifying-glass"></i></FwbButton>
        </section>
        <FwbButton @click="novoClick"><i class="fa fa-plus-circle"></i>Novo</FwbButton>
    </div>
</template>

<style scoped>

.container {
    display: flex;
    flex-direction: row;
    justify-content: end;
    gap: 10px;
    border: 1px solid black;
    border-radius: 10px;
    width: 100%;
    margin: 10px;
    padding: 2px;
}

.container section {
    display: flex;
    flex-direction: row;
    gap: 5px;
}

</style>
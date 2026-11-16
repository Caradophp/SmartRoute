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
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 16px;
    padding: 20px 24px;
    margin: 0;
    background: #ffffff;
    border: 1px solid #e5edf5;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(20, 62, 110, 0.06);
    box-sizing: border-box;
}

.container section {
    display: flex;
    align-items: center;
    flex: 1;
    max-width: 480px;
    min-width: 0;
    gap: 10px;
}

.container section :deep(input) {
    border-radius: 10px;
    border-color: #dce6f1;
    background: #f8fbff;
}

.container :deep(button) {
    border-radius: 10px;
    font-weight: 600;
    transition: all 0.2s ease;
}

.container :deep(button:hover) {
    transform: translateY(-1px);
}

@media (max-width: 640px) {
    .container {
        flex-direction: column;
        align-items: stretch;
        padding: 16px;
    }

    .container section {
        max-width: 100%;
        width: 100%;
    }
}
</style>

<script setup>
import { FwbInput, FwbCard } from 'flowbite-vue';
import { ref } from 'vue';

const name = ref('');

const cards = [

];

</script>

<template>
    <div class="home-content">
         <fwb-input v-model="name" placeholder="Pesquisar">
            <template #prefix>
                <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
                </svg>
            </template>
        </fwb-input>
    </div>
    <div class="screnn-cards">
        <fwb-card href="#" class="min-w-sm">
            <div class="p-5">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                Noteworthy technology acquisitions 2021
            </h5>
            <p class="font-normal text-gray-700 dark:text-gray-400">
                Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse chronological order.
            </p>
            </div>
        </fwb-card>
        <fwb-card href="#" class="min-w-sm">
            <div class="p-5">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                Noteworthy technology acquisitions 2021
            </h5>
            <p class="font-normal text-gray-700 dark:text-gray-400">
                Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse chronological order.
            </p>
            </div>
        </fwb-card>
        <fwb-card href="#" class="min-w-sm">
            <div class="p-5">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                Noteworthy technology acquisitions 2021
            </h5>
            <p class="font-normal text-gray-700 dark:text-gray-400">
                Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse chronological order.
            </p>
            </div>
        </fwb-card>
    </div>
</template>

<style scoped>

.home-content {
    margin: 25rem;
    margin-top: 2rem;
    margin-bottom: 0;
}

.screnn-cards {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    margin: 7rem;
    margin-top: 2rem;
    gap: 3rem;
}

</style>
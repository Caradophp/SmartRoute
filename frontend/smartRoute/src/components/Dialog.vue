<script setup>
import { FwbButton, FwbModal } from 'flowbite-vue'

const props = defineProps({
    title: String,
    oper: 'inc' | 'alt' | 'con' | 'exc',
});

const emit = defineEmits(['close', 'submit'])

let cssHeaderClass;

if (props.oper === 'inc') {
    cssHeaderClass = 'header inc';
} else if (props.oper === 'alt') {
    cssHeaderClass = 'header alt';
} else if (props.oper === 'con') {
    cssHeaderClass = 'header con';
} else if (props.oper === 'exc') {
    cssHeaderClass = 'header exc';
}

const close = () => {
    emit('close');
}

const submit = () => {
    emit('submit');
}

</script>

<template>
    <fwb-modal :header-class="cssHeaderClass" @close="close">
        <template #header>
            {{ props.title }}
        </template>
        <template #body>
            <div class="body">
                <slot />
            </div>
        </template>
        <template #footer>
            <div class="footer">
                <fwb-button color="red" @click="close"><i class="fa fa-close"></i>Fechar</fwb-button>
                <fwb-button @click="submit">Prosseguir</fwb-button>
            </div>
        </template>
    </fwb-modal>
</template>

<style scoped>

.body {
    margin: 5px;
    text-align: center;
}

.footer {
    display: flex;
    justify-content: flex-end;
    margin: 0;
}

.footer button {
    margin: 2px;
}

</style>
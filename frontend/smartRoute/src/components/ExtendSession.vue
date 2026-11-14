<script setup >
import { useUserStore } from '@/utils/User';
import { FwbButton, FwbInput, FwbModal } from 'flowbite-vue';
import { ref } from 'vue';

const show = ref(false);

const store = useUserStore();

</script>

<template>
    <div>
        <FwbModal v-if="show">
            <template #header>
                <div>
                    <h1>Extender sessão</h1>
                </div>
            </template>
            <template #body>
                <div>
                    <div class="form">
                        <label>E-mail</label>
                        <FwbInput :model-value="store.user.email" :disabled="true" />
                    </div>
                    <div class="form">
                        <label>Senha</label>
                        <FwbInput />
                    </div>
                </div>
            </template>
            <template #footer>
                <div>
                    <FwbButton :color="'green'"><i class="fa-solid fa-floppy-disk"></i>Entrar</FwbButton>
                    <FwbButton :color="'silver'"><i class="fa-solid fa-arrow-up-right-from-square"></i>Sair</FwbButton>
                </div>
            </template>
        </FwbModal>
    </div>
</template>

<style scoped>

.form {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin: 10px;
}

</style>
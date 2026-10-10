<script setup>
import { FwbAutocomplete, FwbButton, FwbInput, FwbModal, FwbP, FwbSelect, FwbTextarea } from 'flowbite-vue';
import Dialog from './Dialog.vue';
import DataTable from './grid/DataTable.vue';
import HeaderPanel from './HeaderPanel.vue';
import { onMounted, ref, unref, useTemplateRef } from 'vue';
import Toast from '@/utils/Toast.js';
import apiClient from '@/utils/Axios.js';

const props = defineProps({
    url: {
        type: String,
        required: true
    },
    columns: {
        type: Array,
        required: true
    },
    formFields: {
        type: Array,
        required: true
    },
    jsonData: {
        type: Function,
        required: true
    },
    setFormData: {
        type: Function,
        required: true
    }
})

const showDialog = ref(false);
const showConfirmDialog = ref(false);
const oper = ref('inc');
const currentDataId = ref('');
const searchParam = ref('');
const grid = useTemplateRef('grid');

const openForm = () => showDialog.value = true;
const closeForm = () => {
    props.formFields.map((r) => {
        r.model.value = '';
    })
    showDialog.value = false
};
const doConfirmOn = () => showConfirmDialog.value = true;
const doConfirmOff = () => showConfirmDialog.value = false;

const showInc = () => {
    oper.value = 'inc';
    openForm();
}

const showAlt = (data) => {
    currentDataId.value = data.id;
    oper.value = 'alt';
    props.setFormData(data);
    openForm();
}

const showExc = (id) => {
    currentDataId.value = id;
    oper.value = 'exc';
    doConfirmOn();
}

const inc = async () => {
    try {
        const response = await apiClient.post(`http://localhost:8080/${props.url}/create`, props.jsonData());

        if (response.status !== 201) {
            Toast.danger('Erro desconhecido ao inserir registro. Contate o suporte técnico');
            return;
        }

        Toast.info('Operação realizada com sucesso');
        closeForm();
        grid.value.reload();
    } catch (error) {
        Toast.error(error)
    }
}

const alt = async () => {
    try {
        const response = await apiClient.put(`http://localhost:8080/${props.url}/alter/${currentDataId.value}`, props.jsonData());

        if (response.status !== 200) {
            Toast.danger('Erro desconhecido ao alterar registro. Contate o suporte técnico');
            return;
        }

        Toast.info('Operação realizada com sucesso');
        closeForm();
        grid.value.reload();
    } catch (error) {
        Toast.error(error)
    }
}

const exc = async () => {
    try {
        const response = await apiClient.delete(`http://localhost:8080/${props.url}/delete/${currentDataId.value}`);

        if (response.status !== 200) {
            Toast.danger('Erro desconhecido ao deletar registro. Contate o suporte técnico');
            return;
        }

        Toast.info('Operação realizada com sucesso');
        grid.value.reload();
        doConfirmOff();
    } catch (error) {
        Toast.error(error)
    }
}

const search = async () => {

    if (searchParam.value === '') {
        grid.value.reload();
        return;
    }

    try {
        const response = await apiClient.get(`http://localhost:8080/users/search/${searchParam.value}`)

        if (response.status !== 200) {
            Toast.danger('Erro desconhecido.Contate o suporte técnico');
            return;
        }

        const data = response.data;
        grid.value.search(data);
    } catch (error) {
        Toast.error(error)
    }
}

const eventhandler = () => {
    let operation = oper.value;

    if (operation === 'inc') {
        inc();
    } else if (operation === 'alt') {
        alt();
    } else if (operation === 'exc') {
        exc();
    } else {
        Toast.warning('Operador inválido');
    }
}

onMounted(() => {
});
</script>

<template>
    <main class="main-page">
        <HeaderPanel :novo-click="showInc" :pesquisa-click="search" v-model:inputSearch="searchParam"/>
        <DataTable :url="props.url" :columns="props.columns" :on-edit="showAlt" :on-delete="showExc" ref="grid"/>
        <Dialog v-if="showDialog" @close="closeForm" @submit="eventhandler" @oper="'alt'" >
            <form>
                <div v-for="formField in props.formFields">
                    <div class="input-content">
                        <label>{{ formField.label }}</label>
                        <FwbAutocomplete 
                            v-model="formField.model.value" 
                            :options="unref(formField.options)"    
                            :search-fields="formField.searchFields"
                            display="nome" 
                            placeholder=""
                            v-if="formField.fieldType == 'autocomplete'"
                        />
                        <!-- <FwbInput :type="formField.fieldType" v-model="formField.model.value"/> -->
                        <FwbSelect v-else-if="formField.fieldType == 'select'" :options="formField.options" v-model="formField.model.value"/>
                        <FwbTextarea v-else-if="formField.fieldType == 'textarea'" v-model="formField.model.value" />
                        <FwbInput :type="formField.fieldType" v-model="formField.model.value" v-else/>
                    </div>
                </div>
            </form>
        </Dialog>
        <FwbModal v-if="showConfirmDialog" size="sm">
            <template #header>
                <h2>Confirmação</h2>
            </template>
            <template #body>
                <FwbP>Dejse mesmo excluir esse registro?</FwbP>
            </template>
            <template #footer>
                <div class="confirm-footer">
                    <FwbButton @click="exc" color="red">Sim</FwbButton>
                    <FwbButton @click="doConfirmOff" color="alternative">Não</FwbButton>
                </div>
            </template>
        </FwbModal>
    </main>
</template>

<style scoped>

.main-page {
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.input-content {
    display: flex;
    flex-direction: row;
    gap: 10PX;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 7px;
}

.confirm-footer {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    gap: 10px;
}

</style>
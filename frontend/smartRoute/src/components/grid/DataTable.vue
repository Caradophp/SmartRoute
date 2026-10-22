<script setup>
import { computed, onMounted, ref } from 'vue';
import apiClient from '@/utils/Axios';
import {
    FwbButton,
    FwbInput,
    FwbTable,
    FwbTableBody,
    FwbTableCell,
    FwbTableHead,
    FwbTableHeadCell,
    FwbTableRow,
} from 'flowbite-vue';
import Toast from '@/utils/Toast';

const props = defineProps({
    columns: {
        type: Array,
        required: true,
    },
    url: {
        type: String,
        required: true,
    },
    onEdit: {
        type: Function,
        required: true
    },
    onDelete: {
        type: Function,
        required: true
    },
    needReload: {
        type: Function,
        required: true
    }
});

const tableData = ref([]);
const loading = ref(false);
const error = ref(null);

const search = ref('');
const currentPage = ref(1);
const perPage = ref(10);

const sortColumn = ref(null);
const sortDirection = ref('asc');

const columns = computed(() => [
    ...props.columns,
    {
        data: null,
        title: 'Ações',
        orderable: false,
        searchable: false,
    },
]);

/**
 * Filtragem
 */
const filteredData = computed(() => {
    if (!search.value.trim()) {
        return tableData.value;
    }

    const term = search.value.toLowerCase();

    return tableData.value.filter((row) => {
        return props.columns.some((column) => {
            const value = row[column.data];

            return value
                ?.toString()
                .toLowerCase()
                .includes(term);
        });
    });
});

/**
 * Ordenação
 */
const sortedData = computed(() => {
    const data = [...filteredData.value];

    if (!sortColumn.value) {
        return data;
    }

    return data.sort((a, b) => {
        const valueA = a[sortColumn.value];
        const valueB = b[sortColumn.value];

        if (valueA == null) return 1;
        if (valueB == null) return -1;

        const comparison = valueA
            .toString()
            .localeCompare(
                valueB.toString(),
                'pt-BR',
                {
                    numeric: true,
                    sensitivity: 'base',
                }
            );

        return sortDirection.value === 'asc'
            ? comparison
            : -comparison;
    });
});

/**
 * Paginação
 */
const totalPages = computed(() => {
    return Math.ceil(sortedData.value.length / perPage.value);
});

const paginatedData = computed(() => {
    const start = (currentPage.value - 1) * perPage.value;
    const end = start + perPage.value;

    return sortedData.value.slice(start, end);
});

const startRecord = computed(() => {
    if (!sortedData.value.length) {
        return 0;
    }

    return (currentPage.value - 1) * perPage.value + 1;
});

const endRecord = computed(() => {
    return Math.min(
        currentPage.value * perPage.value,
        sortedData.value.length
    );
});

/**
 * Ordenação
 */
const sortBy = (column) => {
    if (column.orderable === false) {
        return;
    }

    if (sortColumn.value === column.data) {
        sortDirection.value =
            sortDirection.value === 'asc'
                ? 'desc'
                : 'asc';
    } else {
        sortColumn.value = column.data;
        sortDirection.value = 'asc';
    }

    currentPage.value = 1;
};

/**
 * Busca
 */
const handleSearch = () => {
    currentPage.value = 1;
};

/**
 * Paginação
 */
const goToPage = (page) => {
    if (page < 1 || page > totalPages.value) {
        return;
    }

    currentPage.value = page;
};

/**
 * Ações
 */
const edit = (row) => props.onEdit(row);

const remove = async (row) => props.onDelete(row.id);

/**
 * Carregamento
 */
const loadData = async () => {
    loading.value = true;
    error.value = null;

    try {
        const response = await apiClient.get(
            `http://localhost:8080/${props.url}/listar`
        );

        if (response.status !== 200) {
            Toast.error(
                'Erro ao carregar dados. Contate o suporte técnico'
            );
            return;
        }

        tableData.value = response.data;
    } catch (err) {
        error.value = err;
        Toast.danger(err);
    } finally {
        loading.value = false;
    }
};

const setExternalData = (dataArray) => tableData.value = dataArray;

onMounted(loadData);

defineExpose({
    reload: loadData,
    search: setExternalData
})
</script>

<template>
    <div class="relative overflow-x-auto full-table">

        <!-- Toolbar -->
        <div
            class="flex flex-col gap-4 pb-4 md:flex-row md:items-center md:justify-between"
        >
            <!-- <div>
                <FwbInput
                    v-model="search"
                    placeholder="Pesquisar..."
                    @input="handleSearch"
                />
            </div> -->

            <!-- <div class="flex items-center gap-2">
                <span class="text-sm text-gray-600">
                    Mostrar
                </span>

                <select
                    v-model.number="perPage"
                    @change="currentPage = 1"
                    class="rounded-lg border border-gray-300 bg-gray-50 px-3 py-2 text-sm"
                >
                    <option :value="5">5</option>
                    <option :value="10">10</option>
                    <option :value="25">25</option>
                    <option :value="50">50</option>
                </select>

                <span class="text-sm text-gray-600">
                    registros
                </span>
            </div> -->
        </div>

        <!-- Loading -->
        <div
            v-if="loading"
            class="py-10 text-center text-gray-500"
        >
            Carregando...
        </div>

        <!-- Erro -->
        <div
            v-else-if="error"
            class="py-10 text-center text-red-500"
        >
            Erro ao carregar os dados.
        </div>

        <!-- Tabela -->
        <FwbTable v-else hoverable striped>
            <FwbTableHead>
                <FwbTableHeadCell
                    v-for="column in columns"
                    :key="column.title"
                    :class="{
                        'cursor-pointer select-none':
                            column.orderable !== false
                    }"
                    @click="sortBy(column)"
                >
                    <div class="flex items-center gap-1">
                        {{ column.title }}

                        <template
                            v-if="
                                column.orderable !== false &&
                                sortColumn === column.data
                            "
                        >
                            <span>
                                {{
                                    sortDirection === 'asc'
                                        ? '↑'
                                        : '↓'
                                }}
                            </span>
                        </template>
                    </div>
                </FwbTableHeadCell>
            </FwbTableHead>

            <FwbTableBody>
                <!-- Sem registros -->
                <FwbTableRow
                    v-if="paginatedData.length === 0"
                >
                    <FwbTableCell
                        :colspan="columns.length"
                        class="py-8 text-center"
                    >
                        Nenhum registro encontrado
                    </FwbTableCell>
                </FwbTableRow>

                <!-- Registros -->
                <FwbTableRow
                    v-for="row in paginatedData"
                    :key="row.id"
                >
                    <FwbTableCell
                        v-for="column in props.columns"
                        :key="column.data"
                    >
                        {{ row[column.data] }}
                    </FwbTableCell>

                    <!-- Ações -->
                    <FwbTableCell>
                        <div class="flex gap-2">
                            <FwbButton
                                size="sm"
                                color="light"
                                @click="edit(row)"
                            >
                                <i class="fa fa-pencil"></i>
                            </FwbButton>

                            <FwbButton
                                size="sm"
                                color="red"
                                @click="remove(row)"
                            >
                                <i class="fa fa-trash"></i>
                            </FwbButton>
                        </div>
                    </FwbTableCell>
                </FwbTableRow>
            </FwbTableBody>
        </FwbTable>

        <!-- Footer / Paginação -->
        <div
            v-if="!loading && sortedData.length > 0"
            class="flex flex-col items-center justify-between gap-4 py-4 md:flex-row"
        >
            <span class="text-sm text-gray-600">
                Mostrando
                <strong>{{ startRecord }}</strong>
                até
                <strong>{{ endRecord }}</strong>
                de
                <strong>{{ sortedData.length }}</strong>
                registros
            </span>

            <div class="flex items-center gap-2">
                <FwbButton
                    size="sm"
                    color="light"
                    :disabled="currentPage === 1"
                    @click="goToPage(currentPage - 1)"
                >
                    Anterior
                </FwbButton>

                <span class="px-3 text-sm">
                    Página {{ currentPage }}
                    de {{ totalPages }}
                </span>

                <FwbButton
                    size="sm"
                    color="light"
                    :disabled="currentPage === totalPages"
                    @click="goToPage(currentPage + 1)"
                >
                    Próximo
                </FwbButton>
            </div>
        </div>
    </div>
</template>

<style scoped>

.full-table {
    width: 90%;
}

</style>
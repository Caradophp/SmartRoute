<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import apiClient from '@/utils/Axios'
import Toast from '@/utils/Toast'


const formatCell = (value, field) => {
    if (field === 'placa') {
        return String(value ?? '—').toUpperCase();
    }

    if (field === 'tipo') {
        const translations = {
            CLINIC: 'Clínica',
            HEALTH_CENTER: 'Centro de Saúde',
            HOSPITAL: 'Hospital'
        };

        return translations[value] ?? value ?? '—';
    }

    if (field === 'status') {
        const translations = {
            // Status já existentes
            ACTIVE: 'Ativo',
            IN_ACTION: 'Em ação',
            STOPED: 'Parado',
            CANCELLED: 'Cancelado',

            // Status das ambulâncias
            IN_RESCUE: 'Em resgate',
            WAITING: 'Em espera',
            UNDER_MAINTENANCE: 'Em manutenção',
            IN_MOTION: 'Em movimento'
        };

        return translations[value] ?? value ?? '—';
    }

    if (field === 'data' && typeof value === 'string') {
        const match = value.match(/^(\d{4})-(\d{2})-(\d{2})$/);

        if (match) {
            return `${match[3]}/${match[2]}/${match[1]}`;
        }
    }

    return value ?? '—';
};


const props = defineProps({
  columns: { type: Array, required: true },
  url: { type: String, required: true },
  onEdit: { type: Function, required: true },
  onDelete: { type: Function, required: true }
})

const tableData = ref([])
const searchTerm = ref('')
const loading = ref(false)
const error = ref(null)
const currentPage = ref(1)
const perPage = ref(10)
const sortColumn = ref(null)
const sortDirection = ref('asc')

const sortedData = computed(() => {
  const term = searchTerm.value.trim().toLocaleLowerCase('pt-BR')

  const data = tableData.value.filter(row => {
    if (!term) return true

    return props.columns.some(column => {
      if (!column.data) return false

      const value = formatCell(row[column.data], column.data)

      return String(value ?? '')
        .toLocaleLowerCase('pt-BR')
        .includes(term)
    })
  })
  if (!sortColumn.value) return data
  return data.sort((a, b) => {
    const valueA = a[sortColumn.value]
    const valueB = b[sortColumn.value]
    if (valueA == null && valueB == null) return 0
    if (valueA == null) return 1
    if (valueB == null) return -1
    const comparison = String(valueA).localeCompare(String(valueB), 'pt-BR', {
      numeric: true,
      sensitivity: 'base'
    })
    return sortDirection.value === 'asc' ? comparison : -comparison
  })
})

const totalPages = computed(() => Math.max(1, Math.ceil(sortedData.value.length / perPage.value)))
const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * perPage.value
  return sortedData.value.slice(start, start + perPage.value)
})
const startRecord = computed(() => sortedData.value.length ? (currentPage.value - 1) * perPage.value + 1 : 0)
const endRecord = computed(() => Math.min(currentPage.value * perPage.value, sortedData.value.length))

watch(totalPages, (total) => {
  if (currentPage.value > total) currentPage.value = total
})
watch(() => props.url, () => {
  currentPage.value = 1
  sortColumn.value = null
  loadData()
})

const sortBy = (column) => {
  if (column.orderable === false || !column.data) return
  if (sortColumn.value === column.data) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortColumn.value = column.data
    sortDirection.value = 'asc'
  }
  currentPage.value = 1
}

const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) currentPage.value = page
}

const loadData = async () => {
  loading.value = true
  error.value = null
  try {
    const response = await apiClient.get(`http://localhost:8080/${props.url}/listar`)
    if (response.status !== 200) throw new Error('Erro ao carregar registros')
    tableData.value = Array.isArray(response.data) ? response.data : []
    currentPage.value = 1
  } catch (err) {
    error.value = err
    Toast.error(err)
  } finally {
    loading.value = false
  }
}

const search = (term) => {
  searchTerm.value = String(term ?? '')
  currentPage.value = 1
}

const setExternalData = (dataArray) => {
  tableData.value = Array.isArray(dataArray) ? dataArray : []
  currentPage.value = 1
  error.value = null
}

onMounted(loadData)
defineExpose({
  reload: loadData,
  search,
  setExternalData
})
</script>

<template>
  <section class="data-card" aria-label="Tabela de registros">
    <div class="table-scroll">
      <table class="records-table">
        <thead>
          <tr>
            <th
              v-for="column in props.columns"
              :key="column.data ?? column.title"
              scope="col"
              :class="{ sortable: column.orderable !== false && column.data }"
              :aria-sort="sortColumn === column.data ? (sortDirection === 'asc' ? 'ascending' : 'descending') : undefined"
            >
              <button
                v-if="column.orderable !== false && column.data"
                type="button"
                class="sort-button"
                @click="sortBy(column)"
              >
                <span>{{ column.title }}</span>
                <span class="sort-indicator" aria-hidden="true">{{ sortColumn === column.data ? (sortDirection === 'asc' ? '↑' : '↓') : '↕' }}</span>
              </button>
              <span v-else>{{ column.title }}</span>
            </th>
            <th scope="col" class="actions-heading">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading || error || paginatedData.length === 0">
            <td :colspan="props.columns.length + 1" class="state-cell">
              <div v-if="loading" class="empty-state" role="status">
                <i class="fa-solid fa-spinner fa-spin state-icon" aria-hidden="true"></i>
                <strong>Carregando registros...</strong>
              </div>
              <div v-else-if="error" class="empty-state" role="alert">
                <i class="fa-solid fa-circle-exclamation state-icon" aria-hidden="true"></i>
                <strong>Não foi possível carregar os registros</strong>
                <button class="retry-button" type="button" @click="loadData">Tentar novamente</button>
              </div>
              <div v-else class="empty-state">
                <i class="fa-regular fa-folder-open state-icon" aria-hidden="true"></i>
                <strong>Nenhum registro encontrado</strong>
                <span>Os registros cadastrados aparecerão aqui.</span>
              </div>
            </td>
          </tr>
          <tr v-for="(row, index) in (loading || error ? [] : paginatedData)" :key="row.id ?? index">
            <td v-for="column in props.columns" :key="column.data ?? column.title">
              {{ formatCell(row[column.data], column.data) }}
            </td>
            <td class="actions-cell">
              <div class="row-actions">
                <button type="button" class="action-button edit-button" aria-label="Editar registro" title="Editar registro" @click="props.onEdit(row)">
                  <i class="fa-solid fa-pen" aria-hidden="true"></i>
                </button>
                <button type="button" class="action-button delete-button" aria-label="Excluir registro" title="Excluir registro" @click="props.onDelete(row.id)">
                  <i class="fa-solid fa-trash" aria-hidden="true"></i>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <footer v-if="!loading && !error && sortedData.length > 0" class="table-footer">
      <span class="records-count">Mostrando <strong>{{ startRecord }}–{{ endRecord }}</strong> de <strong>{{ sortedData.length }}</strong> registros</span>
      <div class="pagination">
        <label class="page-size-label" for="table-page-size">Por página</label>
        <select id="table-page-size" v-model.number="perPage" @change="currentPage = 1">
          <option :value="5">5</option>
          <option :value="10">10</option>
          <option :value="25">25</option>
          <option :value="50">50</option>
        </select>
        <button type="button" class="page-button" :disabled="currentPage === 1" @click="goToPage(currentPage - 1)">Anterior</button>
        <span class="page-indicator">{{ currentPage }} de {{ totalPages }}</span>
        <button type="button" class="page-button" :disabled="currentPage === totalPages" @click="goToPage(currentPage + 1)">Próximo</button>
      </div>
    </footer>
  </section>
</template>

<style scoped>
.data-card { width: 100%; min-width: 0; box-sizing: border-box; background: #fff; border: 1px solid #e3ebf5; border-radius: 18px; box-shadow: 0 5px 24px rgba(23,65,124,.05); overflow: hidden; }
.table-scroll { width: 100%; overflow-x: auto; }
.records-table { width: 100%; border-collapse: collapse; table-layout: auto; text-align: left; }
.records-table th { padding: 18px 22px; background: #f4f8fd; color: #173b69; font-size: 12px; font-weight: 750; text-transform: uppercase; letter-spacing: .045em; white-space: nowrap; border-bottom: 1px solid #e4ecf6; }
.records-table td { padding: 18px 22px; font-size: 14px; color: #344963; border-bottom: 1px solid #edf2f8; vertical-align: middle; overflow-wrap: anywhere; }
.records-table tbody tr:last-child td { border-bottom: 0; }
.records-table tbody tr:not(:has(.state-cell)):hover { background: #f8fbff; }
.sort-button { display: inline-flex; align-items: center; gap: 9px; border: 0; padding: 0; background: none; color: inherit; font: inherit; text-transform: inherit; letter-spacing: inherit; cursor: pointer; }
.sort-button:focus-visible, .action-button:focus-visible, .page-button:focus-visible, .retry-button:focus-visible { outline: 3px solid #9cc2ff; outline-offset: 2px; }
.sort-indicator { color: #8ca3c2; font-size: 15px; }
.actions-heading, .actions-cell { width: 140px; text-align: center !important; }
.row-actions { display: flex; align-items: center; justify-content: center; gap: 9px; }
.action-button { display: inline-flex; align-items: center; justify-content: center; width: 36px; height: 36px; border-radius: 10px; border: 1px solid transparent; cursor: pointer; transition: background .2s, transform .2s; }
.action-button:hover { transform: translateY(-1px); }
.edit-button { background: #eaf2ff; color: #1756e8; border-color: #dbe8ff; }
.edit-button:hover { background: #dbe9ff; }
.delete-button { background: #fff0f1; color: #d4384b; border-color: #ffe0e4; }
.delete-button:hover { background: #ffe1e6; }
.state-cell { text-align: center !important; padding: 0 !important; }
.empty-state { min-height: 180px; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 9px; padding: 28px 16px; box-sizing: border-box; color: #7184a3; font-size: 13px; }
.empty-state strong { color: #25456f; font-size: 15px; font-weight: 650; }
.state-icon { font-size: 26px; color: #8ea9ce; margin-bottom: 4px; }
.retry-button { margin-top: 8px; padding: 9px 14px; background: #1756e8; border: 0; border-radius: 9px; color: #fff; cursor: pointer; }
.table-footer { display: flex; align-items: center; justify-content: space-between; gap: 16px; padding: 16px 22px; border-top: 1px solid #edf2f8; }
.records-count { color: #7184a3; font-size: 13px; }
.records-count strong { color: #25456f; }
.pagination { display: flex; align-items: center; flex-wrap: wrap; justify-content: flex-end; gap: 10px; }
.page-size-label, .page-indicator { color: #7184a3; font-size: 13px; }
.pagination select, .page-button { min-height: 36px; padding: 7px 10px; border: 1px solid #dce6f2; border-radius: 9px; background: #fff; color: #25456f; font-size: 13px; }
.page-button { cursor: pointer; }
.page-button:disabled { opacity: .45; cursor: not-allowed; }
.page-button:not(:disabled):hover { background: #f1f6ff; }
@media (max-width: 760px) { .records-table { min-width: 580px; } .table-footer { flex-direction: column; align-items: stretch; } .pagination { justify-content: flex-start; } }
</style>

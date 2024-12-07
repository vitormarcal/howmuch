<template>
  <div class="price-history">
    <nuxt-link to="/add-coupon" class="add-coupon-link">Adicionar Cupom Fiscal</nuxt-link>
    <div class="search-box">
      <input
          type="text"
          v-model="productName"
          @input="fetchPriceHistory"
          placeholder="Pesquise por um produto..."
          class="search-input"
      />
    </div>
    <h1 class="page-title">Histórico de Preços para: {{ productName }}</h1>

    <div v-if="loading" class="loading">Carregando...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="prices.length">
      <table class="price-table">
        <thead>
        <tr>
          <th>Data e Hora</th>
          <th>Preço</th>
          <th>Produto</th>
          <th>Loja</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(price, index) in prices" :key="index">
          <td>{{ price.date }}</td>
          <td>{{ price.value }}</td>
          <td>{{ price.description }}</td>
          <td>{{ price.store }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const productName = ref('a');
const prices = ref([]);
const loading = ref(false);
const error = ref('');

const fetchPriceHistory = async () => {
  loading.value = true;
  try {
    const response = await fetch(`http://localhost:8080/api/price-history/${productName.value}`);
    if (!response.ok) {
      throw new Error('Falha ao buscar histórico de preços');
    }
    const data = await response.json();
    prices.value = data;
  } catch (err) {
    error.value = err.message || 'Falha ao buscar histórico de preços.';
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchPriceHistory();
});
</script>

<style scoped>
/* Container para a página */
.price-history {
  font-family: 'Arial', sans-serif;
  background-color: #f4f4f9;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.add-coupon-link {
  display: inline-block;
  padding: 10px 20px;
  margin-bottom: 20px;
  background-color: #4CAF50;
  color: white;
  border-radius: 5px;
  text-decoration: none;
  font-weight: bold;
  transition: background-color 0.3s;
}

.add-coupon-link:hover {
  background-color: #45a049;
}

/* Estilo da caixa de pesquisa */
.search-box {
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  padding: 12px 20px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
  outline: none;
  transition: border-color 0.3s;
}

.search-input:focus {
  border-color: #4CAF50;
}

/* Título da página */
.page-title {
  text-align: center;
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

/* Mensagens de Carregamento e Erro */
.loading, .error {
  text-align: center;
  font-size: 18px;
  color: #333;
}

.error {
  color: red;
}

.loading {
  color: #4CAF50;
}

/* Estilo da Tabela */
.price-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.price-table th, .price-table td {
  padding: 12px;
  text-align: left;
}

.price-table th {
  background-color: #4CAF50;
  color: white;
}

.price-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.price-table tr:hover {
  background-color: #f1f1f1;
}

.price-table td {
  border-bottom: 1px solid #ddd;
}

@media (max-width: 768px) {
  .search-input {
    font-size: 14px;
  }

  .price-table th, .price-table td {
    padding: 8px;
  }

  .price-table {
    font-size: 14px;
  }

  .page-title {
    font-size: 20px;
  }
}
</style>

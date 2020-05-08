<template>
  <div>
    <Navbar class="navb" />
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <div class="container-list">
      <h1>Lista de Produtos</h1>
      <div class="list-categoria">
        <ul class="list-group">
          <li
            class="list-group-item d-flex justify-content-between align-items-center element-categoria"
            v-for="(produto,index) in tableData"
            v-bind:key="index"
          >
            {{index}}
            {{produto.nome}}
          </li>
        </ul>
        <FormAddCategoria />
        <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          @change="hadleChange()"
          aria-controls="my-table"
        ></b-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import Produto from "../services/produto";
import Navbar from "../components/Navbar";
import FormAddCategoria from "../components/FormAddCategoria";
import Alert from "../components/utils/Alert";

export default {
  data() {
    return {
      rows: null,
      perPage: 10,
      currentPage: 1,
      tableData: [],

      alertBody: {
        message: "",
        type: ""
      },
      visible: null
    };
  },

  name: "Produto",
  components: {
    Navbar,
    FormAddCategoria,
    Alert
  },

  async mounted() {
    try {
      const res = await Produto.getPage();
      this.rows = res.data.totalPages * 10;
      this.perPage = res.data.numberOfElements;
      this.currentPage = res.data.pageable.pageNumber;

      if (Object.keys(res.data.content).length != 0) {
        res.data.content.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome
          };
          this.tableData.push(temp);
        });
      } else {
        const temp = {
          message: "Nenhum produto foi cadastrado",
          type: "warning"
        };
        this.alertBody = temp;
        this.visible = 5; // tempo para o alerta desaparecer

        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    } catch (e) {
      const temp = {
        message: "Erro ao buscar no banco",
        type: "danger"
      };
      this.alertBody = temp;
      this.visible = 5; // tempo para o alerta desaparecer

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    }
  },
  methods: {}
};
</script>

<style scoped>
.navb {
  width: 100%;
  top: 0;
  position: absolute;
}
.element-categoria {
  cursor: pointer;
  margin: 5px;
}
.container-list {
  flex-direction: column;
  display: flex;
  align-items: center;
  justify-content: center;
}
.list-categoria {
  width: 50%;
}
</style>
<template>
  <div>
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <b-modal id="modal-delete1" title="Tem certeza que deseja deletar" @ok="handleDelete()">
      <p class="my-4"></p>
    </b-modal>

    <SaveProdutoModal @emit-click="insertListner" />
    <!-- --------------------- -->
    <div class="container-list">
      <h1>Lista de Produtos</h1>

      <b-button class="title button-title" v-b-modal.modal-save>Adicionar</b-button>

      <div class="list-categoria">
        <b-table :items="tableData" :fields="fields" striped responsive="sm" :busy="isBusy">
          <template v-slot:cell(opções)="row">
            <b-icon class="icon" icon="trash-fill" aria-hidden="true" @click="showModal(row.item)"></b-icon>
            <b-icon class="icon" icon="pencil" aria-hidden="true" @click="row.toggleDetails"></b-icon>
          </template>

          <template v-slot:row-details="row" v-slot:table-busy>
            <b-card>
              <b-form>
                <h1>Editar item</h1>
                <b-form-group>
                  <b-input v-model="row.item.nome" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input v-model="row.item.preco"></b-input>
                </b-form-group>
              </b-form>
              <b-button size="sm" @click="handleEdit(row.item)" variant="primary">Salvar</b-button>
            </b-card>
          </template>
        </b-table>
      </div>
    </div>
  </div>
</template>

<script>
import Produto from "../services/produto";
import SaveProdutoModal from "./modals/SaveProdutoModal"
import Alert from "../components/utils/Alert";

export default {
  data() {
    return {
      tableData: [],
      fields: ["nome", "preco", "opções"],
      isBusy: false,
      editItem: {
        nome: "",
        preco: ""
      },
      saveItem: {
        nome: "",
        preco: ""
      },

      alertBody: {
        message: "",
        type: ""
      },
      visible: null,

      deleteItem: null
    };
  },

  name: "Produto",
  components: {
    SaveProdutoModal,
    Alert
  },

  async mounted() {
    try {
      const res = await Produto.getPage();

      if (Object.keys(res.data.content).length != 0) {
        res.data.content.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome,
            preco: element.preco
          };
          this.tableData.push(temp);
        });
      } else {
        this.showAlert("Nenhum produto foi cadastrado !", "warning", null)
        
      }
    } catch (e) {
      this.showAlert("Erro ao buscar no banco", "danger", e.message)
    }
  },
  methods: {
    insertListner(item, response){
      if(response != null){
        this.showAlert("Item cadastrado com sucesso ! ", "primary", null)
        const temp = {
          id: response,
          nome: item.nome,
          preco: item.preco
        }
        this.tableData.push(temp)
      }else{
        this.showAlert("Erro ao deletar item", "danger", item.message)
      }
    },

    showAlert(msg, type, error) {
      const temp = {
        message: msg,
        type: type
      };
      if (error != null) { 
        temp.message = temp.message+": "+error
      }
      this.alertBody = temp;
      this.$store.dispatch("setTime", 5);
    },

    showModal(item) {
      this.deleteItem = item;
      this.$bvModal.show("modal-delete1");
    },

    async handleDelete() {
      try {
        await Produto.del(this.deleteItem.id);
        const index = this.tableData.indexOf(this.deleteItem);
        this.tableData.splice(index, 1);
        this.showAlert("Item deletado com sucesso !", "primary", null)
        
      } catch (e) {
        this.showAlert("Erro ao deletar item", "danger", e.message)
      }
    },

    async handleEdit(item) {
      try {
        Produto.put(item.id, item);
        this.showAlert("Item alterado com sucesso", "primary", null)
    
      } catch (e) {
        this.showAlert("Erro ao alterar item", "danger", e.message)
      }
    }
  }
};
</script>

<style scoped>
.icon {
  margin-right: 10px;
  cursor: pointer;
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
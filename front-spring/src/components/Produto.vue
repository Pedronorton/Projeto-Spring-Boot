<template>
  <div>
    <Alert :message="alertBody.message" :type="alertBody.type" />
    <b-modal id="modal-delete1" title="Tem certeza que deseja deletar" @ok="handleDelete()">
      <p class="my-4"></p>
    </b-modal>

    <SaveProdutoModal @emit-click="insertListner" />
    <!-- --------------------- -->
    <div class="container-list container">
      <div class="header">
        <h1 class="title-header">Lista de Produtos</h1>
        <b-button class="btn-add" variant="primary" v-b-modal.modal-save>Adicionar</b-button>
      </div>
      <div class="list-categoria">
        <b-table :items="tableData" :fields="fields" striped responsive="sm" :busy="isBusy">
          <template v-slot:cell(opções)="row">
            <b-icon name="deletar" class="icon" icon="trash-fill" aria-hidden="true" @click="showModal(row.item)"></b-icon>
            <b-icon
              name="editar"
              class="icon"
              icon="pencil"
              aria-hidden="true"
              @click="row.toggleDetails"
              @click.once="feedTableTags(row.item.id)"
            ></b-icon>
          </template>

          <template v-slot:row-details="row" v-slot:table-busy>
            <b-card>
              <b-form>
                <h1>Editar item</h1>
                <b-form-group>
                  <b-input v-model="row.item.nome" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input name="preco" v-model="row.item.preco"></b-input>
                </b-form-group>

                <b-form-tags size="lg" add-on-change no-outer-focus class="mb-2">
                  <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
                    <li v-for="(tag,index) in tags" :key="index" class="list-inline-item">
                      <b-form-tag @remove="removeTag(tag)" variant="info">{{ tag.text }}</b-form-tag>
                    </li>
                  </ul>
                  <b-form-select id="tags" v-model="selectedOptionTags" :options="options">
                    <template v-slot:first>
                      <!-- This is required to prevent bugs with Safari -->
                      <option disabled value>Escolha as categorias relacionadas</option>
                    </template>
                  </b-form-select>
                </b-form-tags>
                <b-form-group label="Imagem do produto">
                  <b-form-file
                    name="file"
                    v-model="row.item.imageUrl"
                    :state="Boolean(row.item.imageUrl)"
                    placeholder="Choose a file or drop it here..."
                    drop-placeholder="Drop file here..."
                  ></b-form-file>
                </b-form-group>
              </b-form>

              <b-button name="salvar" size="sm" @click="handleEdit(row.item)" variant="primary">Salvar</b-button>
            </b-card>
          </template>
        </b-table>
      </div>
    </div>
  </div>
</template>

<script>
import Categoria from "../services/categoria";
import { mapState, mapGetters } from "vuex";
import Produto from "../services/produto";
import SaveProdutoModal from "./modals/SaveProdutoModal";
import Alert from "../components/utils/Alert";

export default {
  data() {
    return {
      options: [],
      tags: [],
      selectedOptionTags: "",
      tableData: [],
      tableDataCategoria: [],
      fields: ["nome", "preco", "opções"],
      isBusy: false,
      editItem: {
        nome: "",
        preco: "",
        imageUrl: "",
      },

      alertBody: {
        message: "",
        type: ""
      },

      deleteItem: null,
      selectedCategoria: ""
    };
  },

  name: "Produto",
  components: {
    SaveProdutoModal,
    Alert
  },
  computed: {
    ...mapState({
      tableDataProdutos: state => state.tableDataProdutos
    }),
    ...mapGetters({
      listIdsCategorias: "getAllIdsCategorias"
    })
  },
  watch: {
    selectedOptionTags(val) {
      if (val != null) {
        var temp = {
          text: val.nome,
          value: val
        };
        this.tags.push(temp);
      }

      this.options = this.options.filter(function(element) {
        if (element.text != val.nome) {
          return true;
        }
      });
    }
  },

  async mounted() {
    try {
      const ids = [];
      this.listIdsCategorias.forEach(element => {
        ids.push(element.id);
      });

      const res = await Produto.getPage(ids.toString());

      if (Object.keys(res.data.content).length != 0) {
        res.data.content.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome,
            preco: element.preco,
            // imageUrl: element.imageUrl
          };
          this.tableData.push(temp);
        });
      } else {
        this.showAlert("Nenhum produto foi cadastrado !", "warning", null);
      }
    } catch (e) {
      this.showAlert("Erro ao buscar no banco", "danger", e.message);
    }
    try {
      const responseCategoria = await Categoria.getAll();
      responseCategoria.data.forEach(element => {
        const temp = {
          text: element.nome,
          value: element
        };
        this.options.push(temp);
      });
    } catch (e) {
      alert(e);
    }
  },
  methods: {
    async feedTableTags(idItem) {
      try {
        const response = await Produto.getCategoria(idItem);
        response.data.forEach(element => {
          const temp = {
            text: element.nome,
            value: element
          };
          this.tags.push(temp);
        });
      } catch (e) {
        alert(e);
      }
    },
    insertListner(item, response) {
      if (response != null) {
        this.showAlert("Item cadastrado com sucesso ! ", "primary", null);
        const temp = {
          id: response,
          nome: item.nome,
          preco: item.preco
        };
        this.tableData.push(temp);
      } else {
        this.showAlert("Erro ao cadastrar item", "danger", item.message);
      }
    },

    showAlert(msg, type, error) {
      const temp = {
        message: msg,
        type: type
      };
      if (error != null) {
        temp.message = temp.message + ": " + error;
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
        this.showAlert("Item deletado com sucesso !", "primary", null);
      } catch (e) {
        this.showAlert("Erro ao deletar item", "danger", e.message);
      }
    },

    async handleEdit(item) {
      try {
        await Produto.put(item);
        const categorias = [];
        this.tags.forEach(element => {
           const temp = {
             id: element.value.id,
             nome: element.value.nome
           };          
          categorias.push(temp);
          
        });
        Produto.putCategoria(item.id, categorias);

        this.showAlert("Item alterado com sucesso", "primary", null);
      } catch (e) {
        this.showAlert("Erro ao alterar item", "danger", e.message);
      }
    },
    removeTag(tag) {
      if (tag != null) {
        this.options.push(tag);
      }
      this.tags = this.tags.filter(function(element) {
        if (element.text != tag.text) {
          return true;
        }
      });
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
.header {
  width: 90%;
  flex-direction: row;
  display: flex;
}
.btn-add{
  position : relative; 
  margin-left: auto;
}
.title-header{
  position: relative;
  margin-left: auto;
}
.container-list {
  padding-top: 5%;
  flex-direction: column;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
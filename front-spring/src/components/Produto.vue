<template>
  <div>
    <Navbar class="navb" />
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <b-modal id="modal-delete1" title="Tem certeza que deseja deletar" @ok="handleDelete()">
      <p class="my-4"></p>
    </b-modal>

    <b-modal id="modal-edit" title="Editar" @ok="handleEdit(editItem)">
      <p class="my-4"></p>

      <form ref="form" >
        <b-form-group
          label="Nome"
        >
          <b-form-input  v-model="editItem.nome"  disabled></b-form-input>
        </b-form-group>

        <b-form-group
          label="Preço"
        >
          <b-form-input v-model="editItem.preco" ></b-form-input>

        </b-form-group>
      </form>
    </b-modal>

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
            <div class="icons">
              <b-icon
                class="icon-delete"
                icon="trash-fill"
                aria-hidden="true"
                @click="showModal(produto)"
              ></b-icon>
              <b-icon 
              icon="pencil" 
              aria-hidden="true" 
              v-b-modal="'modal-edit'"
              @click="handleSubmitEdit(produto)" ></b-icon>
            </div>
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

      editItem: {
        nome:"",
        preco:""
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
            nome: element.nome,
            preco: element.preco
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
        message: "Erro ao buscar no banco: "+e.message,
        type: "danger"
      };
      this.alertBody = temp;
      this.visible = 5; // tempo para o alerta desaparecer

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    }
  },
  methods: {
    showModal(item) {
      this.deleteItem = item;
      this.$bvModal.show("modal-delete1");
    },
    async handleDelete() {
      try {
        await Produto.del(this.deleteItem.id);
        const index = this.tableData.indexOf(this.deleteItem);
        this.tableData.splice(index, 1);
        const temp = {
          message: "Item deletado com sucesso ! ",
          type: "primary"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      } catch (e) {
        const temp = {
          message: "Erro ao deletar produto " + e.message,
          type: "danger"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    },
    handleSubmitEdit(item){
      this.editItem = item
    },
    async handleEdit(item){
      try {
        Produto.put(item.id, item)
        const temp = {
          message: "Item alterado com sucesso ! ",
          type: "primary"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);

      }
      catch(e){
        const temp = {
          message: "Erro ao alterar item",
          type: "danger"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
      
    }
  }
};
</script>

<style scoped>
.icon-delete {
  margin-right: 10px;
}

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
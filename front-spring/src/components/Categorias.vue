<template>
  <div>
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />

    <b-modal id="modal-delete1" title="Tem certeza que deseja deletar" @ok="handleDelete()">
      <p class="my-4"></p>
    </b-modal>

    <div class="container-list">
      <div class="container-title">
        <h1 class="title">Lista de categorias</h1>
        <Button class="title button-title" message="Adicionar" path="/add-categoria"></Button>
      </div>
      <div class="list-categoria">
        <b-table :items="tableData" :fields="fields" striped responsive="sm" :busy="isBusy">
          <template v-slot:cell(opções)="row">
            <b-icon class="icon" icon="trash-fill" aria-hidden="true" @click="showModal(row.item)" ></b-icon>
            <b-icon class="icon" icon="pencil" aria-hidden="true" @click="row.toggleDetails"></b-icon>
          </template>
    
          <template v-slot:row-details="row" v-slot:table-busy>
            <b-card>
              <b-form>
                <h1>Editar item</h1>
                <b-form-group>
                  <b-input v-model="row.item.id" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input v-model="row.item.nome"></b-input>
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
import Categoria from "../services/categoria";
import Alert from "../components/utils/Alert";
import Button from "../components/utils/Button";
export default {
  data() {
    return {
      fields: [
        "id",
        "nome",
        "opções"
      ],
      isBusy: false,
      tableData: [],
      visible: null,
      alertBody: {
        message: "",
        type: ""
      },
      itemDelete: null,

      editItem:{
        nome:""
      }
    };
  },

  name: "teste",
  components: {
    Alert,
    Button
  },

  async mounted() {
    try {
      const res = await Categoria.getAll();

      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome
          };
          this.tableData.push(temp);
        });
      } else {
        const temp = {
          message: "Nenhuma categoria cadastrada",
          type: "info"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    } catch (e) {
      const temp = {
        message: "Erro ao buscar no banco: " + e.message,
        type: "danger"
      };
      this.alertBody = temp;
      this.visible = 5;
      setTimeout(function() {
        this.visible = null;
      }, 5000);
    }
  },
  methods: {

    showModal(item) {
      this.itemDelete = item;
      this.$bvModal.show("modal-delete1");
    },

    async handleDelete() {
      try {
        await Categoria.del(this.itemDelete.id);

        const index = this.tableData.indexOf(this.itemDelete)
        this.tableData.splice(index,1)

        

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
          message: "Erro ao deletar categoria " + e.message,
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

      try{
        console.log(item);
        
        await Categoria.put(item.id, item)
        const temp = {
          message: "Item atualizado com sucesso !",
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
          message: "Erro ao atualizar item: "+e.message,
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
.icon {
  margin-right: 10px;
}
.button-title {
  right: 0;
  /* position: absolute; */
  /* margin-left: 50px; */
}
.container-title {
  width: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.title {
  display: inline;
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
<template>
  <div>
    <Alert :message="alertBody.message" :type="alertBody.type" />

    <b-modal id="modal-delete1" title="Tem certeza que deseja deletar" @ok="handleDelete()">
      <p class="my-4"></p>
    </b-modal>

    <SaveCategoriaModal @emit-click="insertListner" />

    <div class="container-list container">
      <div class="header">
        <h1 class="title-header">Lista de Categorias</h1>
        <b-button class="title btn-add"  variant="primary" @click="showModalSave()">Adicionar</b-button>
      </div>
      <div class="table-container">
        <b-table :items="tableData" :fields="fields" striped responsive="sm" :busy="isBusy">

          <template v-slot:cell(opções)="row">
            <b-icon name="deletar" class="icon" icon="trash-fill" aria-hidden="true" @click="showModalDelete(row.item)" ></b-icon>
            <b-icon name="editar" class="icon" icon="pencil" aria-hidden="true" @click="row.toggleDetails"></b-icon>
          </template>
    
          <template v-slot:row-details="row" v-slot:table-busy>
            <b-card>
              <b-form>
                <h1>Editar item</h1>
                <b-form-group>
                  <b-input v-model="row.item.id" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input name="nome" v-model="row.item.nome"></b-input>
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
import Alert from "../components/utils/Alert";
import SaveCategoriaModal from "../components/modals/SaveCategoriaModal"

import {mapActions} from 'vuex'
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
      alertBody: {
        message: "",
        type: ""
      },
      itemDelete: null,

      editItem:{
        nome:""
      },
      saveItem:{
        id:"",
        nome:""
      },
    };
  },

  name: "teste",
  components: {
    Alert,
    SaveCategoriaModal
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
        this.showAlert("Nenhuma categoria cadastrada", "info", null)
      }
    } catch (e) {
      this.showAlert("Erro ao buscar no banco", "danger", e.message)
    }
  },
  methods: {
    ...mapActions([
      'setTime'
    ]),
    insertListner(item, response){
      if(response != null){
        this.showAlert("Item cadastrado com sucesso ! ", "primary", null)
        const temp = {
          id: response,
          nome: item.nome
        }
        this.tableData.push(temp)
      }else{
        this.showAlert("Erro ao deletar item", "danger", item.message)
      }
    },

    showModalSave(){
      this.$bvModal.show('modal-save')
    },

    showModalDelete(item) {
      this.itemDelete = item;
      this.$bvModal.show("modal-delete1");
    },

    showAlert(msg,type,error){
      const temp = {
        message: msg,
        type: type
      }
      if(error != null){
        temp.message = temp.message+": "+error
      }
        this.alertBody = temp;
        this.$store.dispatch('setTime',5)
    },

    async handleDelete() {
      try {
        await Categoria.del(this.itemDelete.id);

        const index = this.tableData.indexOf(this.itemDelete)
        this.tableData.splice(index,1)

        this.showAlert("Item deletado com sucesso ! ", "primary", null)

      } catch (e) {
        this.showAlert("Erro ao deletar item", "danger", e.message)

      }

    },

    handleSubmitEdit(item){
      this.editItem = item
    },

    async handleEdit(item){

      try{
        await Categoria.put(item.id, item)
        this.showAlert("Item atualizado com sucesso !", "primary", null)

      }
      catch(e){
        this.showAlert("Erro ao atualizar item", "danger", e.message)
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
.button-title {
  right: 0;
}
.container-title {
  width: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.table-container{
  width: 100%;
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
.header {
  width: 80%;
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
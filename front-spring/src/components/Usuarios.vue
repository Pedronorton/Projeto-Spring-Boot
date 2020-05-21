<template>
  <div>
    <Alert class="teste" :message="alertBody.message" :type="alertBody.type" :visible="visible" />

    <b-modal id="delete-modal" title="Tem certeza que deseja deletar o item ?" @ok="handleDelete()"></b-modal>

    <SaveUsuarioModal @emit-click="insertListner" />
  
    <div class="container-list">
      <div class="container-header">
        <h1>Lista de Clientes</h1>
        <b-button class="title button-title" v-b-modal.modal-save>Adicionar</b-button>

      </div>
      <div>
        <b-table :items="tableData" :fields="fields" striped responsive="sm" :busy="isBusy">
          <template v-slot:cell(opções)="row">
            <b-icon
              class="icon"
              icon="trash-fill"
              aria-hidden="true"
              @click="showDeleteModal(row.item)"
            ></b-icon>
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
                  <b-input v-model="row.item.email"></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input v-model="row.item.cpfOuCnpj" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input v-model="row.item.telefone" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input v-model="row.item.logradouro" disabled></b-input>
                </b-form-group>

                <b-form-group>
                  <b-input v-model="row.item.cep" disabled></b-input>
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
import Clientes from "../services/cliente";
import Alert from "../components/utils/Alert";
import SaveUsuarioModal from "./modals/SaveUsuarioModal";
import {mapActions} from 'vuex'
export default {
  data() {
    return {
      fields: [
        "id",
        "nome",
        "email",
        "cpfOuCnpj",
        "telefone",
        "logradouro",
        "cep",
        "opções"
      ],
      isBusy: false,
      tableData: [],
      
      alertBody: {
        message: "",
        type: ""
      },
      visible: null,
      
      
      tableDataCidade: [],
      
    };
  },

  name: "Clientes",
  components: {
    Alert,
    SaveUsuarioModal
  },

  async created() {
    try {
      this.isBusy = true;
      const res = await Clientes.getAll();
      
      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome,
            email: element.email,
            cpfOuCnpj: element.cpfOuCnpj,
            telefone: element.telefones[0],
            logradouro: element.enderecos[0].logradouro,
            cep: element.enderecos[0].cep
          };
          this.tableData.push(temp);
        });
      } else {
        this.showAlert("Nenhum cliente foi cadastrado ", "warning", null);
      }
    } catch (e) {
      this.showAlert("Erro ao buscar no banco de dados", "danger", e.message);

    }
    this.isBusy = false;
  },
  methods: {
    ...mapActions([
      'setTime'
    ]),
    showDeleteModal(item) {
      this.deletedItem = item;
      this.$bvModal.show("delete-modal");
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
    insertListner(item, response) {
      if (response != null) {
        this.showAlert("Item cadastrado com sucesso ! ", "primary", null);
        const temp = {
          id: response,
          nome: item.nome,
          email: item.email,
          cpfOuCnpj: item.cpfOuCnpj,
          telefone: item.telefone1,
          logradouro: item.logradouro,
          cep: item.cep
        };
        this.tableData.push(temp);

      } else {
        this.showAlert("Erro ao inserir item", "danger", item.message);
      }
    },
    async handleDelete() {
      this.$bvModal.hide("delete-modal"); // proprio do componente

      try {
        await Clientes.del(this.deletedItem.id);
        const index = this.tableData.indexOf(this.deletedItem);
        this.tableData.splice(index, 1);
        this.showAlert("Item deletado com sucesso !", "primary");

      } catch (e) {
        this.showAlert("Erro ao deletar item", "danger", e.message);

      }
    },

    async handleEdit(item) {    
      const from = {
        id: item.id,
        nome: item.nome,
        email: item.email
      };

      try {
        await Clientes.put(from.id, from);
        this.showAlert("Item atualizado com sucesso !", "primary", null);
        
      } catch (e) {
        this.showAlert("Erro ao atualizar item", "danger", e.message);
      }
    }
  }
};
</script>

<style scoped>
.container-header {
  flex-direction: row;
  display: flex;
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
.icons {
  left: 0;
}
.icon {
  margin-right: 10px;
}
</style>
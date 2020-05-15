<template>
  <div>
    <Navbar id="teste" class="navb" />
    <Alert class="teste" :message="alertBody.message" :type="alertBody.type" :visible="visible" />

    <b-modal id="delete-modal" title="Tem certeza que deseja deletar o item ?" @ok="handleDelete()">
    </b-modal>

    <div class="container-list">
      <h1>Lista de Clientes</h1>
      <div class="list-categoria">

        <b-table :items="tableData" :fields="fields" striped responsive="sm" :busy="isBusy">
          <template v-slot:cell(opções)="row">
            <b-icon class="icon" icon="trash-fill" aria-hidden="true" @click="showDeleteModal(row.item)" ></b-icon>
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
import Navbar from "../components/Navbar";
import Alert from "../components/utils/Alert";
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
      editedItem: {
        id:"",
        nome: "",
        cpfOuCnpj: "",
        email: "",
        tipo: null,
        telefone: "",
        logradouro: "",
        numero: "99",
        cep: "",
        cidade_id: "1"
      },

      alertBody: {
        message: "",
        type: ""
      },
      visible: null,
      titulo: "",
      selected: "",

      options: [
        { item: 1, name: "Pessoa Física" },
        { item: 2, name: "Pessoa Júridica" },
      ],
    };
  },

  name: "Clientes",
  components: {
    Navbar,
    Alert
  },

  async created() {
    try {
      this.isBusy = true
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
        const temp = {
          message: "Nenhum cliente foi cadastrado",
          type: "warning"
        };

        this.alertBody = temp;
        this.visible = 5;

        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    } catch (e) {
      const temp = {
        message: "Erro ao buscar no banco de dados: " + e.message,
        type: "danger"
      };

      this.alertBody = temp;
      this.visible = 5;

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    }
    this.isBusy = false
  },
  methods: {
    showDeleteModal(item){
      this.deletedItem = item
      this.$bvModal.show("delete-modal");
    },

    showAlert(message, type, error) {
      const temp = {
        message: message + ": " + error,
        type: type
      };

      this.alertBody = temp;
      this.visible = 5;

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    },

    async handleDelete() {
      
      this.$bvModal.hide("delete-modal"); // proprio do componente
      
      try {
        await Clientes.del(this.deletedItem.id);
        const index = this.tableData.indexOf(this.deletedItem)
        this.tableData.splice(index,1)

        const temp = {
          message: "Cliente deletado com sucesso !",
          type: "primary"
        };

        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      } catch (e) {
        const temp = {
          message: "Erro ao deletar cliente: " + e.message,
          type: "danger"
        };

        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
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

        const temp = {
          message: "Cliente atualizado com sucesso !",
          type: "primary"
        };

        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      } catch (e) {
        const temp = {
          message: "Erro ao atualizar o cliente: " + e.message,
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
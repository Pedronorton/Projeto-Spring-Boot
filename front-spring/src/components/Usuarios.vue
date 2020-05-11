<template>
  <div>
    <Navbar id="teste" class="navb" />
    <Alert class="teste" :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <b-modal id="modal-edit" title="Editar Cliente" @ok="handleEdit">
        <form ref="form">
        <b-form-group
          label="Nome"
        >
          <b-form-input
            disabled
            v-model="editedItem.nome"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          disabled
          label="CPF/CNPJ"
        >
          <b-form-input
            disabled
            v-model="editedItem.cpfOuCnpj"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="Email"
        >
          <b-form-input
            v-model="editedItem.email"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="Telefone"
        >
          <b-form-input
            disabled
            v-model="editedItem.telefone1"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="Logradouro"
        >
          <b-form-input
            disabled
            v-model="editedItem.logradouro"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="Cep"
        >
          <b-form-input
            disabled
            v-model="editedItem.cep"
          ></b-form-input>
        </b-form-group>

        <fieldset class="form-group">
          <div class="row">
            <legend class="col-form-label col-sm-2 pt-0">Tipo</legend>
            <div class="col-sm-10">
              <div class="form-check">
                <div>
                  <b-form-radio-group
                    v-model="selected"
                    :options="options"
                    class="mb-3"
                    value-field="item"
                    text-field="name"
                  ></b-form-radio-group>
                </div>
              </div>
            </div>
          </div>
        </fieldset>
      </form>
      </b-modal>

    <div class="container-list">
      <h1>Lista de Clientes</h1>
      <div class="list-categoria">
        <ul class="list-group">
          <li
            class="list-group-item d-flex justify-content-between align-items-center element-categoria"
            v-for="(cliente,index) in tableData"
            v-bind:key="index"
          >
            <h6>{{cliente.nome}}</h6>
            <div class="icons">
              <b-icon
                class="icon-delete"
                icon="trash-fill"
                aria-hidden="true"
                v-b-modal="'modal-delete'"
              ></b-icon>

              <b-modal
                id="modal-delete"
                title="Tem certeza que deseja deletar"
                @ok="handleDelete(cliente)"
              >
                <p class="my-4"></p>
              </b-modal>

              <b-icon
                icon="pencil"
                aria-hidden="true"
                v-b-modal="'modal-edit'"
                @click="handleSubmitEdit(cliente)"
              ></b-icon>
            </div>
          </li>
        </ul>
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
      tableData: [],
      editedItem:{
        nome: "",
        cpfOuCnpj: "",
        email: "",
        tipo: null,
        telefone1: "",
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
      const res = await Clientes.getAll();
      console.log(res);
      
      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome,
            email: element.email,
            cpfOuCnpj: element.cpfOuCnpj,
            telefone1: element.telefones[0],
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
  },
  methods: {
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

    async handleDelete(item) {
      this.$bvModal.hide("modal-delete"); // proprio do componente
      try {
        await Clientes.del(item.id);

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
    handleSubmitEdit(item){
      this.editedItem = item
    },
    async handleEdit() {
      
      const from = {
        id: this.editedItem.id,
        nome: this.editedItem.nome,
        email: this.editedItem.email,
      }

      try{
        await Clientes.put(from.id, from)
        
        const temp = {
          message: "Cliente atualizado com sucesso !",
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
          message: "Erro ao atualizar o cliente: "+e.message,
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
.icon-delete {
  margin-right: 10px;
}
</style>
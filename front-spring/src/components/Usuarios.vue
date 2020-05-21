<template>
  <div>
    <Alert class="teste" :message="alertBody.message" :type="alertBody.type" :visible="visible" />

    <b-modal id="delete-modal" title="Tem certeza que deseja deletar o item ?" @ok="handleDelete()"></b-modal>

    <!-- MODAL SAVE -->
    <b-modal id="modal-save" title="Cadastrar" @ok="handleSave()">
      <p class="my-4"></p>

      <form>
        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Nome</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="formUser.nome" />
          </div>
        </div>

        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">CPF/CNPJ</label>
          <div class="col-sm-10">
            <input class="form-control" v-model="formUser.cpfOuCnpj" />
          </div>
        </div>

        <div class="form-group row">
          <label for="inputPassword3" class="col-sm-2 col-form-label">Email</label>
          <div class="col-sm-10">
                        <input class="form-control" v-model="formUser.email" />

          </div>
        </div>

        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Telefone</label>
          <div class="col-sm-10">
            <input class="form-control" v-model="formUser.telefone1" />

          </div>
        </div>

        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Cidade</label>
          <div class="col-sm-10">
            <b-form-input placeholder="Selecione uma cidade" list="my-list-id" v-model="formUser.cidade"></b-form-input>

            <datalist id="my-list-id">
              <option v-for="(cidade,index) in tableDataCidade" v-bind:key="index">{{ cidade.nome }}</option>
            </datalist>
          </div>
        </div>

        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Logradouro</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="formUser.logradouro" />
          </div>
        </div>

        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Cep</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="formUser.cep" />
          </div>
        </div>

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
        <div class="form-group row">
          <div class="col-sm-10"></div>
        </div>
      </form>
    </b-modal>
<!-- --------------------------------------- -->

    <div class="container-list">
      <div class="container-header">
        <h1>Lista de Clientes</h1>
        <b-button class="title button-title" v-b-modal.modal-save>Adicionar</b-button>

        <!-- <Button class="title button-title" message="Adicionar" path="/add-user"></Button> -->
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
import Cidade from "../services/cidade";
// import Button from "../components/utils/Button";
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
        id: "",
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
      formUser: {
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
      cidade_nome: "",

      alertBody: {
        message: "",
        type: ""
      },
      visible: null,
      titulo: "",
      selected: "",
      tableDataCidade: [],
      options: [
        { item: 1, name: "Pessoa Física" },
        { item: 2, name: "Pessoa Júridica" }
      ]
    };
  },

  name: "Clientes",
  components: {
    Alert,
  },

  async created() {
    try {
      this.isBusy = true;
      const res = await Clientes.getAll();
      const res1 = await Cidade.getAll();

      res1.data.forEach(element => {
        const temp = {
          id: element.id,
          nome: element.nome,
          estado: element.estado.nome
        };

        this.tableDataCidade.push(temp);
      });

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
    this.isBusy = false;
  },
  methods: {
    showDeleteModal(item) {
      this.deletedItem = item;
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
    async handleSave(){      
      try{
        const res = await Cidade.getId(this.cidade_nome)
        
        this.editedItem.cidade_id = res.data.id
        
        this.formUser.tipo = this.selected
        await Clientes.post(this.formUser);
        
        this.tableData.push(this.formUser)
      }
      catch(e){
        alert(e)
      }
    },
    async handleDelete() {
      this.$bvModal.hide("delete-modal"); // proprio do componente

      try {
        await Clientes.del(this.deletedItem.id);
        const index = this.tableData.indexOf(this.deletedItem);
        this.tableData.splice(index, 1);

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
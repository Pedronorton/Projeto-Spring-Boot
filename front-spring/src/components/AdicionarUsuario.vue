<template>
  <div class="contaier-geral">
    <Navbar class="navb" />
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible"></Alert>
    <div class="container-form">
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
            <input type="text" class="form-control" v-model="formUser.telefone1" />
          </div>
        </div>

        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">{{formUser.cidade_nome}}</label>
          <div class="col-sm-10">
            <b-form-input placeholder="Selecione uma cidade" list="my-list-id" v-model="cidade_nome"></b-form-input>

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
      <b-overlay :show="show" rounded="sm">
        <button class="btn btn-primary" @click="handleSave()">Cadastrar</button>
      </b-overlay>
    </div>
  </div>
</template>

<script>
import Navbar from "./Navbar";
import Cliente from "../services/cliente";
import Cidade from "../services/cidade";
import Alert from "./utils/Alert";
export default {
  data() {
    return {
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

      selected: "",

      options: [
        { item: 1, name: "Pessoa Física" },
        { item: 2, name: "Pessoa Júridica" },
      ],

      alertBody: {
        message: "",
        type: ""
      },
      show: false,
      cidade_nome: "",
      visible: null,
      tableDataCidade: []
    };
  },

  name: "AdicionarUsuario",
  components: {
    Navbar,
    Alert
  },

  async mounted() {
    try {
      const res = await Cidade.getAll();

      res.data.forEach(element => {
        const temp = {
          id: element.id,
          nome: element.nome,
          estado: element.estado.nome
        };

        this.tableDataCidade.push(temp);
      });
    } catch (e) {
      const temp = {
        message: "Erro ao buscar os dados das cidades no banco de dados: "+e.message,
        type: "danger"
      }
      this.alertBody = temp
      this.visible = 5
      setTimeout(function(){
        this.visible = null
      }, 5000)

    }
  },

  methods: {
    async handleSave() {
      this.show = true;
      
      try {
        const res = await Cidade.getId(this.cidade_nome)
        console.log(typeof res.data.id);
        this.formUser.cidade_id = res.data.id
        this.formUser.tipo = this.selected
        await Cliente.post(this.formUser);
        const temp = {
          message: "Cliente salvo com sucesso",
          type: "primary"
        };
        this.alertBody = temp;
        this.visible = 5; // tempo para o alerta desaparecer

        setTimeout(function() {
          this.visible = null;
        }, 5000);
      } catch (e) {
        const temp = {
          message: "Erro ao salvar o cliente: " + e.message,
          type: "danger"
        };
        this.alertBody = temp;
        this.visible = 5; // tempo para o alerta desaparecer

        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
      this.show = false;
    },

    showAlert(message, type, error) {
      const temp = {
        message: message + ": " + error,
        type: type
      };
      this.alertBody = temp;
      this.visible = 5; // tempo para o alerta desaparecer

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    }
  }
};
</script>

<style scoped>
.contaier-geral {
  flex-direction: column;
  display: flex;
  align-items: center;
  justify-content: center;
}
.container-form {
  margin-top: 100px;
  width: 70%;
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
<template>
  <div class="contaier-geral">
    <Navbar class="navb" />
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible"></Alert>
    <h1>Adicionar Categoria</h1>
    <div class="container-form">
      <form>
        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Nome</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="formCategoria.nome" />
          </div>
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
import Categoria from "../services/categoria"
import Alert from "./utils/Alert";
export default {
  data() {
    return {
      formCategoria: {
        nome: "",
      },

      alertBody: {
        message: "",
        type: ""
      },
      show: false,
      visible: null,
    };
  },

  name: "AdicionarCategoria",
  components: {
    Navbar,
    Alert,
  },


  methods: {
    async handleSave() {
      this.show = true;
      
      try {
       
        await Categoria.post(this.formCategoria);
        const temp = {
          message: "Categoria salva com sucesso",
          type: "primary"
        };
        this.alertBody = temp;
        this.visible = 5; // tempo para o alerta desaparecer

        setTimeout(function() {
          this.visible = null;
        }, 5000);
      } catch (e) {
        const temp = {
          message: "Erro ao salvar categoria: " + e.message,
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
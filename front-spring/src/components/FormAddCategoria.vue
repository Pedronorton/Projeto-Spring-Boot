<template>
  <div>
    <div>
      <b-button v-b-modal.modal-1>Adicionar produto</b-button>

      <b-modal id="modal-1" title="Adicionar Produto" @ok="handleOk">
        <form>
          <div class="form-group">
            <label >Nome</label>
            <input
              v-model="produto.nome"
              class="form-control"
              id="exampleInputEmail1"
              aria-describedby="emailHelp"
              placeholder="insira o nome"
            />
          </div>
          <div class="form-group">
            <label >Valor</label>
            <input
              v-model="produto.preco"
              type="number"
              class="form-control"
              id="exampleInputPassword1"
              placeholder="insira o valor. (separado por ponto)"
            />
          </div>
        </form>
      </b-modal>
    </div>
  </div>
</template>
<script>
import Produto from "../services/produto";

export default {
  name: "FormAddCategoria",
  components: {
    
  },
  data() {
    return {
      produto: {
        nome: "",
        preco: ""
      },
      alertBody: {
        message: "",
        type: ""
      },
      visible:null
    };
  },
  methods: {
    //post
    async handleOk() {
      try {
        await Produto.post(this.produto);
        this.$emit('emit-click',this.produto,1)
        
      } catch (e) {
        this.$emit('emit-click',e,2)
        
      }
    }
  }
};
</script>

<style>
.modal {
  z-index: 100;
}
</style>
<template>
  <div>
    <div>
      <b-button v-b-modal.modal-1>Adicionar produto</b-button>

      <b-modal id="modal-1" title="Adicionar Produto" @ok="handleOk">

        <form>
          <div class="form-group">
            <label for="exampleInputEmail1">Nome</label>
            <input
              v-model="produto.nome"
              class="form-control"
              id="exampleInputEmail1"
              aria-describedby="emailHelp"
              placeholder="insira o nome"
            />

          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">Valor</label>
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
 import Produto from '../services/produto'
export default {
  name: "FormAddCategoria",
  components: {},
  data(){
      return {
          produto:{
            nome: "",
            preco: ""
          }
          
      }
  },
  methods:{
      //post
      async handleOk(){
        
        try{
          await Produto.post(this.produto)
          window.location.reload()
        }
        catch(e){
          alert("Erro")
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
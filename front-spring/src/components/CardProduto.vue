<template>
  <div>
    <b-card
      
      :img-src="imageUrl"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 20rem;"
      class="mb-2"
    > 
    <b-card-title class="nome-produto">{{nomeProduto}}</b-card-title>
      <!-- <b-card-text>Some quick example text to build on the card title and make up the bulk of the card's content.</b-card-text> -->
      <h4 class="preco-produto">{{precoProduto}}</h4>
      <h5 class="descricao">12X de 74,91 sem juros</h5>

      <b-button href="#" variant="primary">Carrinho</b-button>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "CardProduto",
  components: {},
  props:{
    nomeProduto:{
      type: String,
      required: true
    },
    precoProduto:{
      type: Number,
      required: true
    },
    imageUrl:{
      type: String,
      required: true
    }
  },
  data() {
    return {

    };
  },
 
};
</script>

<style scoped>
.preco-produto{
  font-weight: bold;
  font-size: 20px;
}
.nome-produto{
  font-size: 15px;
}
.descricao{
  font-size: 11px;
}
</style>
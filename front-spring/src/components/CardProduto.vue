<template>
  <div>
    <div class="row justify-content-center">
      <div>
        <router-link class="card-blog-link" :to="`/detalhes-produtos/${id}`">
        <div class="card" style="max-width: 20rem; max-heigth: 20rem">
          <img class="card-img-top" :src="imageUrl" alt="Card-image">
          <div class="card-body">
            <h5 class="card-title">{{nomeProduto}}</h5>
            <p class="card-text">12X de 74,91 sem juros</p>
            <a href="" class="btn btn-primary">Carrinho</a>
          </div>
        </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CardProduto",
  components: {},
  props: {
    id:{
      type: Number,
      required: true
    },
    nomeProduto: {
      type: String,
      required: true
    },
    precoProduto: {
      type: Number,
      required: true
    },
    imageUrl: {
      type: String,
      required: true
    }
  },
  data() {
    return {};
  },
  mounted(){
    if(this.nomeProduto.length > 51){
      this.nomeProduto = this.nomeProduto.substring(0,51)+"..."
    }
  },
};
</script>

<style scoped>
.preco-produto {
  font-weight: bold;
  font-size: 20px;
}
.nome-produto {
  font-size: 15px;
}
.descricao {
  font-size: 11px;
}
</style>
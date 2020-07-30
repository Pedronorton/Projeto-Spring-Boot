<template>
  <div>
    <div class="row justify-content-center">
      <div>
        <div class="card product-card" style="max-width: 20rem; max-heigth: 20rem">
        <router-link class="card-blog-link" :to="`/detalhes-produtos/${id}`">

          <img class="card-img-top" :src="imageUrl" alt="Card-image">

          <div class="card-body">
            <h5 align="left" class="card-title">{{nomeProduto}}</h5>
            <div class="preco-produto">
              <p>{{precoProduto.toLocaleString("pt-br", {
        style: "currency",
        currency: "BRL"
      })}}</p>
              <p class="card-text teste">12X de 74,91</p>
            </div>
          </div>
        </router-link>
        <a id="link-cart" class="btn btn-primary" @click="handleBag()">Carrinho</a>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions} from 'vuex'
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
  methods:{
    ...mapActions(["setProductOnCart"]),
    handleBag(){
      const temp = {
        id: this.id,
        nomeProduto: this.nomeProduto,
        precoProduto: this.precoProduto,
        imageUrl: this.imageUrl,
      }
      this.$store.state.tableProductsOnCart.push(temp);
      
    }
  }
};
</script>

<style scoped>
.card-body{
  padding-bottom: 0.2em;
}
.preco-produto {
  display: flex;
  font-weight: bold;
  font-size: 15px;
}
.teste{
  margin-left: auto;
}

</style>
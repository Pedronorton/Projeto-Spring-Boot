<template>
  
    <div id="cart" class="modal-container">
      <div class="modal-cart">
        <div class="item-cart" v-for="(produto,index) in getProductsOnCart" :key="index">
          <b-button class="button-delete" @click="removeItem(index)">x</b-button>
          <b-img class="image-cart" :src="produto.imageUrl"></b-img>

          <h3 class="item-nome">{{ produto.nomeProduto }}</h3>
          <span class="item-preco">{{ produto.precoProduto.toLocaleString("pt-br", {
        style: "currency",
        currency: "BRL"
      }) }}</span>
        </div>
        <span v-if="getProductsOnCart.length > 0" class="total-cart">Total:{{total()}}</span>
        <span v-if="getProductsOnCart.length == 0" class="total-cart">Nenhum produto no carrinho</span>
        <router-link  :to="`/finalizar-compra`">
          <b-button id="button-cart" v-if="getProductsOnCart.length > 0" class="button-cart" @click="showModalCart">CARRINHO</b-button>
        </router-link>
      </div>
    </div> 
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "CartModal",
  components: {},
  methods: {
    ...mapActions(["showModalCart", "removeProductOnCart"]),
    hasProduct() {
      return this.getProductsOnCart.length > 0;
    },
    removeItem(index){
      this.removeProductOnCart(index)
    },
    total() {

      var precoTotal = 0;
      var precoElement = 0;
      this.getProductsOnCart.forEach(element => {
        precoElement = parseFloat(element.precoProduto);
        precoTotal += precoElement;  
      });
      return precoTotal.toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
      });
    },
    showPopupCart() {
      this.showModalCart();
    }
  },
  computed: {
    ...mapGetters(["getProductsOnCart"])
  }
};
</script>

<style>
.modal-container {
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0px;
  z-index: 2;
  left: 0px;
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
}
.modal-cart {
  height: auto;
  background-color: white;
  width: 30%;
  min-width: 300px;
  padding: 40px;
  box-shadow: 0 0 0 10px white;
  position: relative;
  top: 10%;
}
.item-nome {
  grid-column: 2/4;
  grid-row: 2/3;
  font-size: 1em;
  font-weight: normal;
}
.item-cart {
  width: 100%;
  height: 130px;
  box-sizing: border-box;
  border-radius: 3px;
  padding: 0 0.5em;
  margin-top: 0.3em;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(3, 1fr);
}
.item-preco {
  margin-top: 5px;
  grid-row: 3/3;
  grid-column: 3/3;
}
.button-cart {
  width: 30%;
  margin-top: 30px;
}
.button-delete{
  margin-right: 10px;
  padding-right: 5px;
  width: 20px;
  height: 20px;
  grid-row: 1;
  grid-column: 4;
  padding: 0 !important;
}
.image-cart {
  max-width: 70%;
  align-self: center;
  grid-row: 1/4;
  grid-column: 1/2;
}
.total-cart {
  position: absolute;
  left: 10%;
}

</style>
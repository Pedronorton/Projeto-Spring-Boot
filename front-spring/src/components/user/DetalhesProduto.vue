<template>
  <div>
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="4" />
    <div class="row">
      <div class="col">
        <b-img :src="contentProduct.imageUrl"></b-img>
      </div>
      <div class="col">
        <h1>{{contentProduct.nome}}</h1>
        <h1>R${{contentProduct.preco}}</h1>
        <div class="div-button">
          <b-button class="button" @click="handleBag()">
            <b-icon icon="cart"></b-icon>Por no carrinho e continuar comprando
          </b-button>
          <b-button class="button" href="/finalizar-compra">
            <b-icon icon="cart"></b-icon>Finalizar compra
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import Alert from "../utils/Alert";
import Produto from "@/services/produto";
export default {
  name: "DetalhesProduto",
  components: {
    Alert
  },

  data() {
    return {
      contentProduct: {
        id: "",
        imageUrl: "",
        nome: "",
        preco: ""
      },
      alertBody: {
        message: "",
        type: ""
      }
    };
  },
  computed: {
    ...mapGetters({
      listaProdutos: "getTableProductOnCart"
    }),
    ...mapState({})
  },
  async created() {
    try {
      const response = await Produto.getOne(this.$route.params.id);
      this.contentProduct.id = response.data.id;
      this.contentProduct.preco = response.data.preco.toLocaleString("pt-br", {
        style: "currency",
        currency: "BRL"
      });
      this.contentProduct.imageUrl = response.data.imageUrl;
      this.contentProduct.nome = response.data.nome;
    } catch (e) {
      alert(e);
    }
  },
  methods: {
    ...mapActions(["setProductOnCart"]),
    handleBag() {
      this.$store.state.tableProductsOnCart.push(this.contentProduct);
      
      if (localStorage.getItem('sacola') == null ) {
          localStorage.setItem("sacola", JSON.stringify(this.contentProduct));
      }else{
       
       var temp = []
        const aux = JSON.parse(localStorage.getItem("sacola"))
        temp.push(aux)
        temp.push(this.contentProduct)
       localStorage.setItem("sacola", JSON.stringify(temp));
      }
      this.showAlert("Item inserido na sacola", "primary", null);
    },
    showAlert(msg, type, error) {
      const temp = {
        message: msg,
        type: type
      };
      if (error != null) {
        temp.message = temp.message + ": " + error;
      }
      this.alertBody = temp;
      this.$store.dispatch("setTime", 2);
    }
  }
};
</script>

<style scoped>
.div-button {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.button {
  margin-top: 10px;
  width: 40%;
}
</style>
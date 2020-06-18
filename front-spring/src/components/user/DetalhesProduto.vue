<template>
  <div>
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="4" />
    <!-- <Navbar /> -->
    <div class="container product-box">
      <div class="product-image">
        <b-img :src="contentProduct.imageUrl"></b-img>
        <Avaliacao class="rate" :rate="rated(stars)" :totalReviews="review" />
      </div>
      <div class="product-info">
        <h1 class="product-title">{{contentProduct.nomeProduto}}</h1>

        <h1 class="product-price">{{contentProduct.precoProduto.toLocaleString("pt-br", {
        style: "currency",
        currency: "BRL"
      })}}</h1>
        <div class="div-button">
          <b-button class="button button-buy" @click="handleBag()">
            <b-icon icon="cart"></b-icon>Comprar
          </b-button>
          <b-button class="button button-info">
            <b-icon icon="cart"></b-icon>Mais informações
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
// import Navbar from "./NavbarUser";
import Alert from "../utils/Alert";
import Produto from "@/services/produto";
import Avaliacao from "./Avaliacao";
export default {
  name: "DetalhesProduto",
  components: {
    Alert,
    // Navbar,
    Avaliacao
  },

  data() {
    return {
      stars: 3.7,
      review: 150,
      contentProduct: {
        id: "",
        imageUrl: "",
        nomeProduto: "",
        precoProduto: ""
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
      this.contentProduct.precoProduto = response.data.preco;
      this.contentProduct.imageUrl = response.data.imageUrl;
      this.contentProduct.nomeProduto = response.data.nome;
    } catch (e) {
      alert(e);
    }
  },
  methods: {
    ...mapActions(["setProductOnCart"]),
    handleBag() {
      this.$store.state.tableProductsOnCart.push(this.contentProduct);

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
    },
    rated(rate) {
      return `${rate * 20}%`;
    }
  }
};
</script>

<style scoped>
.product-box {
  margin-top: 50px;
  width: 800px;
  display: flex;
}
.product-image {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.rate {
  width: 70%;
}
.product-info {
  /* display: flex; */
  margin-left: 30px;
  width: 400px;
  text-align: justify;
}
.product-title {
  font-weight: 300;
  font-size: 1.5em;
  display: flex;
}
.product-price {
  font-size: 2em;
  font-weight: bolder;
  display: flex;
  align-content: flex-start;
}
.div-button {
  width: 300px;
  display: flex;
  flex-direction: column;
}
.button {
  margin-top: 7px;
  border-color: transparent;
}
.button-buy {
  background-color: #27ae60;
  /* border-color: #27ae60; */
}
.button-info {
  background-color: #2d9cdb;
  /* border-color: #2d9cdb; */
}
</style>
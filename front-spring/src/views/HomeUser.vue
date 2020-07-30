<template>
  <div class="home">
    <div>
      <!-- <NavbarUser class="navb" /> -->
      <!-- <FiltroProdutos class="filtro"/> -->
    </div>
    <div>
      <div class="carousel">
        <HomeCarroussel />
      </div>
      <div class="container">
        <div class="row produtos">
          <div class="card" v-for="(item,index) in tableDataProdutos" :key="index">
            <div class="col">
              <CardProdutos
                :id="item.id"
                :nomeProduto="item.nome"
                :precoProduto="item.preco"
                :imageUrl="item.imageUrl"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import FiltroProdutos from '../components/FiltroProdutos'
// import NavbarUser from "../components/user/NavbarUser";
import HomeCarroussel from "../components/HomeCarroussel";
import CardProdutos from "../components/CardProduto";
import Produto from "../services/produto";
import Categorias from "../services/categoria";
import { mapState, mapActions, mapGetters } from "vuex";
export default {
  name: "HomeUser",
  components: {
    // NavbarUser,
    HomeCarroussel,
    CardProdutos
    // FiltroProdutos,
  },
  computed: {
    ...mapState({
      tableDataProdutos: state => state.tableDataProdutos
    }),
    ...mapGetters(["getTableDataProdutos"])
  },

  data() {
    return {
      idsCategoria: []
    };
  },
  methods: {
    ...mapActions(["setObjToTableDataProdutos"])
  },

  async created() {
    if (this.getTableDataProdutos.length == 0) {
      try {
        const res = await Categorias.getAll();

        if (Object.keys(res.data) != 0) {
          res.data.forEach(element => {
            this.idsCategoria.push(element.id);
          });
        }
      } catch (e) {
        alert(e);
      }
      try {
        const res = await Produto.getAll();

        if (Object.keys(res).length != 0) {
          res.data.content.forEach(element => {
            console.log(element.imageUrl);

            this.$store.state.tableDataProdutos.push({
              id: element.id,
              nome: element.nome,
              preco: element.preco,
              imageUrl: element.imageUrl
            });
          });
        }
      } catch (e) {
        alert(e);
      }
    }
  }
};
</script>

<style scoped>
.filtro {
  padding-top: 50px;
}
.navb {
  position: fixed;
  z-index: 2;
  top: 0;
  width: 100%;
}
.card {
  padding: 10px;
  background-color: transparent;
  border-color: transparent;
}
.home {
  background-color: #f4f4f4;
  height: 100%;
}
.produtos {
  margin-top: 100px;
  display: flex;
  justify-content: center;
}
</style>
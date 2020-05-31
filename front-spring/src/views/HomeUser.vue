<template>
  <div class="home">
    <div>
      <Navbar class="navb" />
      <FiltroProdutos class="filtro"/>
    </div>
    <div>
      <div class="carousel">
        <HomeCarroussel />
      </div>
      <div class="container ">
        <div class="row produtos">
          <div  class="card" v-for="(item,index) in tableDataProdutos" :key="index">
            <div class="col">
              <CardProdutos :nomeProduto="item.nome" :precoProduto="item.preco"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FiltroProdutos from '../components/FiltroProdutos'
import Navbar from "../components/Navbar";
import HomeCarroussel from "../components/HomeCarroussel";
import CardProdutos from "../components/CardProduto";
import Produto from "../services/produto"
import Categorias from "../services/categoria"
import {mapState, mapActions, mapGetters} from 'vuex'
export default {
  name: "HomeUser",
  components: {
    Navbar,
    HomeCarroussel,
    CardProdutos,
    FiltroProdutos,
  },
  computed:{
    ...mapState({
      tableDataProdutos: state => state.tableDataProdutos
    }),
    ...mapGetters({
      listaProdutos: 'getTableDataProdutos'
    })
  },

  data() {
    return {
      idsCategoria:[],
    };
  },
  methods: {
    ...mapActions([
      'setObjToTableDataProdutos'
    ]),
     
  },

  async mounted(){
    try{
      const res = await Categorias.getAll();
      
      if(Object.keys(res.data) != 0){
        res.data.forEach(element =>{
          this.idsCategoria.push(element.id)
        })
      }
    }
    catch(e){
      alert(e)
    }
    try{
      const res = await Produto.getPage(this.idsCategoria.toString());
      
      if(Object.keys(res).length != 0){
        res.data.content.forEach(element => {
          
          this.$store.state.tableDataProdutos.push({id: element.id, nome: element.nome, preco: element.preco})
          
        });
      }
    }
    catch(e){
      alert(e)
    }
    
    
  }
};
</script>

<style scoped>
.filtro{
  padding-top: 50px;
}
.navb {
  position: fixed;
  z-index: 2;
  top: 0;
  width: 100%;
}
.card{
    padding: 10px;
    background-color: transparent;
    border-color: transparent;
}
.carousel {
  padding-top: 200px;
}

.home {
  background-color: #f4f4f4;
  height: 100%;
}
.produtos{
    margin-top: 100px;
    display: flex;
    justify-content: center;
}

</style>
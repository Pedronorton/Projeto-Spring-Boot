<template>
  <div>

    <div>
      <div class="conteiner-g">
        <div class="row row-items">
          <div v-for="(item,index) in items" :key="index">
            <div class="col">
              <CardAdmin :title="item.title" :icon="item.icon" :slug="item.slug" :contentNumber="item.contentNumber" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex"
import CardAdmin from "../components/CardAdmin";
import Cliente from "../services/cliente"
import Produto from "../services/produto"
import Categoria from "../services/categoria"
import Pedido from "../services/pedido"
export default {
  
  name: "Home",
  components: {
    CardAdmin,
  },
  data() {
    return {
      items: [
        {
          title: "Clientes",
          slug: "clientes",
          icon: "person-square",
          contentNumber:null
        },
        {
          title: "Produtos",
          slug: "produtos",
          icon: "collection",
          contentNumber:null

        },
        {
          title: "Categorias",
          slug: "categorias",
          icon: "files",
          contentNumber:null

        },
        {
          title: "Pedidos",
          slug: "pedidos",
          icon: "file-earmark-text",
          contentNumber:null
        },
      ],
      
    };
  },

  computed:{
    ...mapGetters({
      idsCategoria: 'getAllIdsCategorias'
    })

  },

  async mounted(){

    try{
      const reponseCliente = await Cliente.getAll();
      this.items[0].contentNumber = Object.keys(reponseCliente.data).length
    }
    catch(e){
      alert(e+ "cliente")
    }

    try{
      const responseCategoria = await Categoria.getAll();
      this.items[2].contentNumber = Object.keys(responseCategoria.data).length
      this.insertAllIdsCategoria(responseCategoria)
    }
    catch(e){
      alert(e+ "categoria")
    }

    
    try{
      const responsePedido = await Pedido.getAll();
      this.items[3].contentNumber = Object.keys(responsePedido.data).length
    }
    catch(e){
      alert(e+" Pedido")
    }

    try{
      const ids = []
      this.idsCategoria.forEach(element => {
        ids.push(element.id)  
      })
      
      const responseProduto = await Produto.getPage(ids.toString());
      this.items[1].contentNumber = Object.keys(responseProduto.data).length

    }
    catch(e){
      alert(e+" Produto")
    }
  },

  methods: {
    insertAllIdsCategoria(response){
      response.data.forEach(element => {
        this.$store.state.allIdsCategorias.push({id: element.id})
      });
    }
  }

  
};
</script>

<style scoped>

.max-width{
  display: flex;
  justify-content: center;
  justify-items: center;
  align-items: center;
}
.navb {
  width: 100%;
  top: 0;
  position: absolute;
}
.conteiner-g {
  display: flex;
  justify-content: center;
  justify-items: center;
  align-items: center;
}
.row-items {
  position: absolute;
  bottom: 50%;
}
</style>
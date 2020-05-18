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
// import Sidebar from './Sidebar'
// import Navbar from "../components/Navbar";
import CardAdmin from "../components/CardAdmin";
import Cliente from "../services/cliente"
import Produto from "../services/produto"
import Categoria from "../services/categoria"
import Pedido from "../services/pedido"
export default {
  
  name: "Home",
  components: {
    // Navbar,
    CardAdmin,
    // Sidebar
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
        
      ]
    };
  },

  async mounted(){
    try{
      const res = await Cliente.getAll();
      const res1 = await Produto.getPage();
      const res2 = await Categoria.getAll();
      const res3 = await Pedido.getAll();
      
      
      this.items[0].contentNumber = Object.keys(res.data).length
      this.items[1].contentNumber = Object.keys(res1.data).length
      this.items[2].contentNumber = Object.keys(res2.data).length
      this.items[3].contentNumber = Object.keys(res3.data).length
    }
    catch(e){
      alert(e)
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
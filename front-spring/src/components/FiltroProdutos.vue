<template>
  <nav class="menu">
    <ul>
      <li
        class="item-nav"
        v-for="(item,index) in items"
        :key="index"
        @click=" boldItem(index, item.id), filterByCategoria()"
      >{{item.nome}}</li>
    </ul>
  </nav>
</template>

<script>
import Categoria from "../services/categoria";
import Produto from "../services/produto";
export default {
  name: "FiltroProdutos",
  components: {},
  data() {
    return {
      items: [{ id: 0, nome: "Todos" }],
      lastIndexBoldItem: null,
      searchByIds: []
    };
  },
  async mounted() {
    try {
      const res = await Categoria.getAll();
      const ids = [];
      if (Object.keys(res.data) != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome
          };

          ids.push(temp.id);
          this.items.push(temp);
        });
      }

      this.items[0].id = ids;
    } catch (e) {
      alert(e);
    }
  },
  methods: {
    async filterByCategoria() {
        
      try {
        const res = await Produto.getPage(this.searchByIds.toString());
        this.$store.state.tableDataProdutos = [];

        if (res.data.numberOfElements > 0) {
          res.data.content.forEach(element => {
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
    },
    boldItem(index, idItem) {
      const item = document.getElementsByClassName("item-nav");

      const styleItem = window.getComputedStyle(item[index]).getPropertyValue('font-weight')
      
        
      if (styleItem == 400) { //normal font
        this.searchByIds.push(idItem)
        item[index].style.fontWeight = "bold";
      } else if (styleItem == 700) { //bold font
        this.searchByIds = this.searchByIds.filter(function(element) {
            if(element != idItem){
                return true
            }
        })
        item[index].style.fontWeight = "normal";
      }
      
    }
  }
};
</script>

<style scoped>
.item-nav {
  display: inline;
  margin-right: 15px;
  cursor: pointer;
}
.item-nav:hover {
  position: relative;
}
.item-nav:hover::after {
  content: "";
  background-color: blue;
  width: 50%;
  height: 20%;
  position: absolute;
  top: 60%;
  left: 5%;
  z-index: 0;
}
.item-nav:hover {
}
.menu {
  background-color: #f8f9fa;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
<template>
  <nav class="menu">
    <ul>
      <li
        :name="item.nome"
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
      //getCompútedStyle pega todos os syles do elemento
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
  font-size: 17px;
  display: inline;
  margin-right: 15px;
  cursor: pointer;
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  
}
.item-nav:hover {
  position: relative;
}
.item-nav:hover::after {
  content: "";
  background-color: #A40606;
  width: 90%;
  height: 20%;
  position: absolute;
  top: 70%;
  left: 5%;
  z-index: 0;
  -moz-animation: swing .2s ease-out !important;
  animation: swing .2s ease-out !important;
  -webkit-animation: swing .2s ease-out !important;
}
.item-nav:hover {
}
.menu {
  background-color: #f8f9fa;
  display: flex;
  align-items: center;
  justify-content: center;
}
@keyframes swing
{
    15%
    {
        -webkit-transform: translateX(5px);
        transform: translateX(5px);
    }
    50%
    {
        -webkit-transform: translateX(3px);
        transform: translateX(3px);
    }
    80%
    {
        -webkit-transform: translateX(2px);
        transform: translateX(2px);
    }
    100%
    {
        -webkit-transform: translateX(0);
        transform: translateX(0);
    }
}

</style>
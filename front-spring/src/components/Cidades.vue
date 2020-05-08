<template>
<div>
<Navbar class="navb"/>
  <div class="container-list">
    <h1>Lista de Cidades</h1>
    <div class="list-categoria">
      <ul class="list-group">
        <li
          class="list-group-item d-flex justify-content-between align-items-center element-categoria"
          v-for="(categoria,index) in tableData"
          v-bind:key="index"
        >
          {{categoria.nome}}
          <span class="badge badge-primary badge-pill">{{categoria.qtdProduto}}</span>
        </li>
      </ul>
    </div>
    
  </div>
</div>
</template>

<script>
import Cidade from "../services/cidade";
import Navbar from "../components/Navbar"
export default {
  data() {
    return {
      tableData: []
    };
  },

  name: "teste",
  components: {
      Navbar,
  },

  async mounted() {
    try {
      const res = await Cidade.getAll();

      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome,
          };
          this.tableData.push(temp);
        });
      }
    } catch (e) {
      console.log(e.message);
    }
  },
  methods: {

  }
};
</script>

<style scoped>
.navb{
  width: 100%;
  top:0;
  position: absolute;
}
.element-categoria{
    cursor: pointer;
    margin: 5px;
}
.container-list{
    flex-direction: column;
    display: flex;
    align-items: center;
    justify-content: center;

}
.list-categoria {
  width: 50%;
}
</style>
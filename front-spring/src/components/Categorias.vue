<template>
<div>
<Navbar class="navb"/>
<Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
  <div class="container-list">
    <div>
      <h1>Lista de categorias</h1>
      <Button message="Adicionar" path="/" ></Button>
    </div>
    <div class="list-categoria">
      <ul class="list-group">
        <li
          class="list-group-item d-flex justify-content-between align-items-center element-categoria"
          v-for="(categoria,index) in listCategoria"
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
import Categoria from "../services/categoria";
import Navbar from "../components/Navbar"
import Alert from "../components/utils/Alert"
import Button from "../components/utils/Button"
export default {
  data() {
    return {
      listCategoria: [],
      visible: null,
      alertBody:{
        message:"",
        type:""
      }
    };
  },

  name: "teste",
  components: {
      Navbar,
      Alert,
      Button,
  },

  async mounted() {
    try {
      const res = await Categoria.getAll();
      
      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome,
          };
          this.listCategoria.push(temp);
        });
      }else{
        const temp = {
          message:"Nenhuma categoria cadastrada",
          type: "info"
        }
        this.alertBody = temp
        this.visible = 5
        setTimeout(function(){
          this.visible = null
        }, 5000)
      }
    } catch (e) {
      const temp = {
          message:"Erro ao buscar no banco: "+e.message,
          type: "danger"
        }
        this.alertBody = temp
        this.visible = 5
        setTimeout(function(){
          this.visible = null
        }, 5000)
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
<template>
  <div class="container-list container">
      <div class="header">
        <h1 class="title-header">Lista de Pedidos</h1> -->
         <!-- <Button class="btn-add" message="Adicionar" path="/add-pedido"></Button>  -->
      </div>
      <div class="list-pedido">
        <div >
        <b-table striped hover :items="tableData"></b-table>
      </div>
      </div>
    </div>
</template>

<script>
// import Alert from "../components/utils/Alert";
// import Button from "../components/utils/Button";
import Pedido from "../services/pedido"


export default {
    name: 'Pedido',
    components:{
        // Alert,
        // Button
    },

    data(){
        return{
            tableData:[],
            
        }
    },
    async mounted(){
        try{
            const res = await Pedido.getAll()
            if(Object.keys(res) != 0){
                res.data.forEach(element => {
                    const temp = {
                        id: element.id,
                        cliente: element.cliente.nome,
                        endereco : element.enderecoEntrega.logradouro,
                        valorTotal : element.valorTotal.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'} )
                    }
                    this.tableData.push(temp)
                });
            }
        }
        catch(e){
            alert(e)
        }
    }
}
</script>

<style scoped>
.teste{
  background-image: url(../assets/wall.jpg);
  background-color: #cccccc;
  height: 500px;
  background-position: center;
}
.header {
  width: 80%;
  flex-direction: row;
  display: flex;
  justify-content: center;
}
.btn-add{
  position : relative; 
  margin-left: auto;
}
.title-header{
  /* position: relative; */
  /* margin-left: auto; */
}
.list-pedido{
  width: 100%;
}
.container-list {
  padding-top: 5%;
  flex-direction: column;
  display: flex;
  align-items: center;
}
.title {
  display: inline;
}


</style>
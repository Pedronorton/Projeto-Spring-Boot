<template>
  <div class="container">
  
    <div>
      <div class="container-header" >

        <h1 class="title">Lista de Pedidos</h1>
        <Button class="button-title" message="Adicionar" path="/add-pedido"></Button>
      </div>

      <div >
        <b-table striped hover :items="tableData"></b-table>
      </div>
    </div>
  </div>
</template>

<script>
// import Alert from "../components/utils/Alert";
import Button from "../components/utils/Button";
import Pedido from "../services/pedido"


export default {
    name: 'Pedido',
    components:{
        // Alert,
        Button
    },
    data(){
        return{
            tableData:[]
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
                        valorTotal : element.valorTotal
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

.container-header{
  display: inline;
}
.title {
  display: inline;
}


</style>
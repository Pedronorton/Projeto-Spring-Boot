<template>
  <div>
    <Navbar class="navb" />
    
    <div class="container-list">
      <div class="container-title">
        <h1 class="title">Lista de Pedidos</h1>
        <Button class="title button-title" message="Adicionar" path="/add-categoria"></Button>
      </div>
      <div class="list-categoria">
        <b-table striped hover :items="tableData"></b-table>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "../components/Navbar";
// import Alert from "../components/utils/Alert";
import Button from "../components/utils/Button";
import Pedido from "../services/pedido"


export default {
    name: 'Pedido',
    components:{
        Navbar,
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


</style>
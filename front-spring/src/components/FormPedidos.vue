<template>
  <div>
    <Navbar class="navb" />
    <div>
      <b-form-group label="Selecione o cliente">
        <b-form-select v-model="selectedCliente" :options="tableDataCliente" change="teste"></b-form-select>
      </b-form-group>

      <b-form-group label="Endereço de entrega">
        <b-form-select v-model="selectedEndereco" :options="tableDataEndereco"></b-form-select>
      </b-form-group>

      <b-form-group label="Itens">
        <b-form-select v-model="selectedItem" :options="tableDataItens"></b-form-select>
      </b-form-group>
      <button @click="teste()">OK</button>
    </div>
  </div>
</template>

<script>
import Cliente from "../services/cliente"
import Produto from "../services/produto"
import Pedido from "../services/pedido"
import Navbar from "../components/Navbar"
export default {
    name: 'FormPedidos',
    components:{
        Navbar,

    },
    data(){
        return {
            tableDataCliente:[],
            tableDataItens:[],
            tableDataEndereco:[],

            tableData:{
                
                cliente : {},
                enderecoEntrega : {},
                pagamento : {
                    numeroDeParcelas : 10,
                    type: "PagamentoCartao"
                },
                itens : []


            },
            selectedCliente:"",
            selectedItem:"",
            selectedEndereco:"",

        }
    },

    watch: {
        selectedCliente(newValue){
            this.tableDataEndereco = []
            try {
                newValue.enderecos.forEach(element => {
                    const temp = {
                        text: element.logradouro,
                        value: element
                    }
                    this.tableDataEndereco.push(temp)
                    
                })
                
            }
            catch(e){
                alert("erro")
            }
            
        }


    },

    async mounted(){
        try{
            const res = await Cliente.getAll();
            const res1 = await Produto.getPage();

            if(Object.keys(res) != 0){
                res.data.forEach(element => {
                    const temp = {
                        text: element.nome,
                        value: element
                    }
                // if(Object.keys(element.enderecos) != 0){
                //     element.enderecos.forEach(endereco => {
                //         const temp2 = {
                //             text: endereco.logradouro,
                //             value: endereco
                //         }
                //         this.tableDataEndereco.push(temp2)
                //     })
                // }
                    
                    this.tableDataCliente.push(temp)
                });
            }

            if(Object.keys(res1) != 0){
                res1.data.content.forEach(element => {
                    const temp1 = {
                        text: element.nome,
                        value: element
                    }
                    
                    
                    this.tableDataItens.push(temp1)
                });
            }
            
        }
        
        catch(e){
            alert(e.message)
        }
    },
    methods:{
        teste(){
            this.tableData.cliente = this.selectedCliente;
            this.tableData.enderecoEntrega = this.selectedEndereco;
             const temp = {
                 quantidade : 1,
                    produto : {
                        id: this.selectedItem.id 
                    }
             }
            
            this.tableData.itens.push(temp)

            
            try{
                Pedido.post(this.tableData)
                alert("deu")

            }
            catch(e){
                alert(e)
            }

            
        }
    }


}
</script>

<style scoped>
.navb {
  width: 100%;
  top: 0;
  position: absolute;
}
</style>
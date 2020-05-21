<template>
  <div>
    <div class="container">
      <h1 class="title">Fazer pedido</h1>
      <b-form-group label="Selecione o cliente">
        <b-form-select v-model="selectedCliente" :options="tableDataCliente" change="teste"></b-form-select>
      </b-form-group>

      <b-form-group label="Endereço de entrega">
        <b-form-select v-model="selectedEndereco" :options="tableDataEndereco"></b-form-select>
      </b-form-group>

      <b-form-group label="Produtos">
        <!-- prop `add-on-change` is needed to enable adding tags vie the `change` event -->
        <b-form-tags v-model="value" size="lg" add-on-change no-outer-focus class="mb-2">
          <template v-slot="{ tags, inputAttrs, inputHandlers, disabled, removeTag }">
            <ul v-if="tags.length > 0" class="list-inline d-inline-block mb-2">
              <li v-for="tag in tags" :key="tag" class="list-inline-item">
                <b-form-tag
                  tagClass="Object"
                  @remove="removeTag(tag)"
                  :title="tag"
                  :disabled="disabled"
                  variant="info"
                >{{ tag }}</b-form-tag>
              </li>
            </ul>

            <b-form-select
              v-bind="inputAttrs"
              v-on="inputHandlers"
              :disabled="disabled || availableOptions.length === 0"
              :options="availableOptions"
            >
              <template v-slot:first>
                <!-- This is required to prevent bugs with Safari -->
                <option disabled value>Escolha seus produtos.</option>
              </template>
            </b-form-select>
          </template>
        </b-form-tags>
      </b-form-group>
      <b-button @click="handleSave()">Salvar</b-button>
    </div>
  </div>
</template>

<script>
import Cliente from "../services/cliente"
import Produto from "../services/produto"
import Pedido from "../services/pedido"
export default {
    name: 'FormPedidos',
    components:{

    },
    data(){
        return {
            value:[],
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
        itens: []
      },
      selectedCliente: "",
      selectedItem: [],
      selectedEndereco: ""
    };
  },
  computed: {
    availableOptions() {
        const aux = [];
        this.tableDataItens.forEach((element, index) => {
            aux[index] = element.text;
        });
        
        
        this.value.forEach(element => {
            this.tableDataItens.forEach(element2 => {
                if(element == element2.text){
                    this.selectedItem.push(element2)
                }
            });
            
        })

      return aux.filter(opt =>

          this.value.indexOf(opt) === -1

      ) // deleta ele da lista
    }
  },

  watch: {
    selectedCliente(newValue) {
     if(newValue != ""){
      this.tableDataEndereco = [];

      try {
        newValue.enderecos.forEach(element => {
          const temp = {
            text: element.logradouro,
            value: element
          };
          this.tableDataEndereco.push(temp);
        });
      } catch (e) {
        alert("erro");
      }
     }
    }
  },

  async mounted() {
    try {
      const res = await Cliente.getAll();
      const res1 = await Produto.getPage();

      if (Object.keys(res) != 0) {
        res.data.forEach(element => {
          const temp = {
            text: element.nome,
            value: element
          };

          this.tableDataCliente.push(temp);
        });
      }

      if (Object.keys(res1) != 0) {
        res1.data.content.forEach(element => {
          const temp1 = {
            text: element.nome,
            value: element
          };

          this.tableDataItens.push(temp1);
        });
      }
    } catch (e) {
      alert(e.message);
    }
  },
  methods: {

    handleSave() {
      this.tableData.cliente = this.selectedCliente;
      this.tableData.enderecoEntrega = this.selectedEndereco;
      this.selectedItem.forEach(element => {
          const temp = {
            quantidade: 1,
            produto: {
                id: element.value.id,
                nome: element.value.nome,
                preco: element.value.preco
        }
      };
          this.tableData.itens.push(temp);
      })

      try {
        Pedido.post(this.tableData);
        this.resetForm()

      } catch (e) {
        alert(e);
      }
    },
    resetForm(){
        this.selectedCliente = ""
        this.selectedEndereco = ""
        this.selectedItem = []
        this.value= []
    }
  }
};
</script>

<style scoped>
.title{
  margin-bottom: 40px;
}
</style>
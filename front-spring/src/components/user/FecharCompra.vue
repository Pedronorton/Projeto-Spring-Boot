<template>
  <b-overlay :show="isLoading">
  <div class="container">
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <b-table :items="tableDataItens" :fields="fields">
      <template v-slot:cell(Produto)="data">
        <b-img class="imagem" :src="data.item.img"></b-img>
      </template>
      <template v-slot:cell(Valor)="data">
        <span>
          {{data.item.Valor.toLocaleString("pt-br", {
          style: "currency",
          currency: "BRL"
          }) }}
        </span>
      </template>
      <template v-slot:cell(Opções)="data">
        <b-button class="btn-delete" @click="removeFromCart(data.item)">X</b-button>
      </template>
    </b-table>
    <div class="total-cart row">
      <h2>Total: {{totalCart()}}</h2>
    </div>
    <b-form>
      <b-form-group label="Endereço de entrega">
        <b-form-select v-model="selectedEndereco" :options="tableDataEndereco"></b-form-select>
      </b-form-group>
    </b-form >
    <div class="btn-fechar-pedido">
      <b-button variant="primary" @click="handleSave()">Fechar pedido</b-button>
    </div>
  </div>
  </b-overlay>
</template>

<script>
import { mapGetters } from "vuex";
import Cliente from "@/services/cliente";
import Pedido from "@/services/pedido";
import Alert from "../utils/Alert";
//import produto from '../../services/produto';
export default {
  name: "FecharCompra",
  components: { Alert },
  data() {
    return {
      isLoading : false,
      loggedUser: false,
      alertBody: {
        message: "",
        type: ""
      },
      tableDataItens: [],
      fields: ["id", "Produto", "Nome", "Valor", "Opções"],
      selectedEndereco: "",
      tableDataEndereco: [],
      tableData: {
        cliente: {},
        enderecoEntrega: {},
        pagamento: {
          numeroDeParcelas: 10,
          type: "PagamentoCartao"
        },
        itens: []
      }
    };
  },
  computed: {
    ...mapGetters(["getProductsOnCart"])
  },
  async created() {
    if (this.getProductsOnCart.lengh != 0) {
      this.getProductsOnCart.forEach(element => {
        const temp = {
          id: element.id,
          img: element.imageUrl,
          Nome: element.nomeProduto,
          Valor: element.precoProduto
        };
        this.tableDataItens.push(temp);
      });
    }
    try {
      const response = await Cliente.getUserAutenticated();
      const clienteTemp = {
        id: response.data.id
      };
      this.tableData.cliente = clienteTemp;
      this.loggedUser = true
      response.data.enderecos.forEach(element => {
        const temp = {
          text: element.logradouro,
          value: element
        };
        this.tableDataEndereco.push(temp);
      });
    } catch (e) {

      console.log("Erro ao logar: "+e.message+" ou cliente não logado");
      
    }
  },
  methods: {
    showAlert(msg, type, error) {
      const temp = {
        message: msg,
        type: type
      };
      if (error != null) {
        temp.message = temp.message + ": " + error;
      }
      this.alertBody = temp;
      this.$store.dispatch("setTime", 5);
    },

    async handleSave() {
      this.isLoading = true;
      this.getProductsOnCart.forEach(element => {
        const temp = {
          quantidade: 1,
          produto: {
            id: element.id
          }
        };
        this.tableData.itens.push(temp);
      });
      const temp = {
        id: this.selectedEndereco.id
      };
      this.tableData.enderecoEntrega = temp;
      if(this.tableData.cliente != "" && this.selectedEndereco != "" && this.tableData.itens != ""){
        try {
          await Pedido.post(this.tableData);
          this.showAlert("Pedido realizado com sucesso", "primary", null);
        } catch (e) {
          this.showAlert("Erro ao realizar pedido, tente novamente", "danger", e.message);

        }
      }else {
        const emptyFields = []
        if(this.tableData.cliente == ""){
          emptyFields.push("Logar no sistema")
        }
        if(this.selectedEndereco == ""){
          emptyFields.push("Preencher endereço")
        }
        if(this.tableData.itens == ""){
          emptyFields.push("Adicionar itens ao carrinho")
        }
        this.showAlert("Erro ao realizar pedido, tente novamente", "danger", "Sugestões -> "+emptyFields.toString());
      }
      this.isLoading = false
    },
    removeFromCart(item) {
      const newList = this.getProductsOnCart.filter(function(element) {
        if (element.id != item.id) {
          return true;
        }
      });
      this.$store.state.tableProductsOnCart = newList;
      const index = this.tableDataItens.indexOf(item);
      this.tableDataItens.splice(index, 1);
    },
    totalCart() {
      var price = 0;
      this.getProductsOnCart.forEach(element => {
        price += element.precoProduto;
      });
      return price.toLocaleString("pt-br", {
        style: "currency",
        currency: "BRL"
      });
    }
  }
};
</script>

<style scoped>
.imagem {
  width: 100px;
}
.btn-delete {
  width: 20px;
  height: 20px;
  padding: 0 !important;
}
.total-cart {
  position: absolute;
  right: 10%;
  left: 70%;
  padding-bottom: 20px;
}
.btn-fechar-pedido{
  padding-bottom: 50px;
}
</style>
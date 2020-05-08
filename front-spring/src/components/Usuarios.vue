<template>
  <div>
    <Navbar id="teste" class="navb" />
    <Alert class="teste" :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <div class="container-list">
      <h1>Lista de Clientes</h1>
      <div class="list-categoria">
        <ul class="list-group">
          <li
            class="list-group-item d-flex justify-content-between align-items-center element-categoria"
            v-for="(cliente,index) in tableData"
            v-bind:key="index"
          >
            <h6>{{cliente.nome}}</h6>
            <div class="icons">
              <b-icon
                class="icon-delete"
                icon="trash-fill"
                aria-hidden="true"
                v-b-modal="'modal-delete'"
              ></b-icon>

              <b-modal id="modal-delete" title="Tem certeza que deseja deletar"   @ok="handleDelete(cliente)">
                <p class="my-4"></p>
              </b-modal>

              <b-icon icon="pencil" aria-hidden="true"
              v-b-modal="'modal'"
              >    
              </b-icon>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Clientes from "../services/cliente";
import Navbar from "../components/Navbar";
import Alert from "../components/utils/Alert";
export default {
  data() {
    return {
      tableData: [],

      alertBody: {
        message: "",
        type: ""
      },
      visible: null,
      titulo:"",
    };
  },

  name: "Clientes",
  components: {
    Navbar,
    Alert
  },

  async created() {
    try {
      const res = await Clientes.getAll();

      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome
          };
          this.tableData.push(temp);
        });
      } else {
        const temp = {
          message: "Nenhum cliente foi cadastrado",
          type: "warning"
        };

        this.alertBody = temp;
        this.visible = 5;

        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    } catch (e) {
      const temp = {
        message: "Erro ao buscar no banco de dados: " + e.message,
        type: "danger"
      };

      this.alertBody = temp;
      this.visible = 5;

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    }
  },
  methods: {
    showAlert(message, type, error) {
      const temp = {
        message: message + ": " + error,
        type: type
      };
      console.log(temp);

      this.alertBody = temp;
      this.visible = 5;

      setTimeout(function() {
        this.visible = null;
      }, 5000);
    },

    async handleDelete(item) {

      this.$bvModal.hide('modal-delete') // proprio do componente
      try {
        await Clientes.del(item.id)
        
        
        const temp = {
            message: "Cliente deletado com sucesso !",
            type: "primary"
        };

        this.alertBody = temp
        this.visible = 5
        setTimeout(function(){
            this.visible = null
        }, 5000)
        
            
      } catch (e) {
        const temp = {
                message: "Erro ao deletar cliente: "+e.message,
                type: "danger"
            };
            
            this.alertBody = temp
            this.visible = 5
            setTimeout(function(){
                this.visible = null
            }, 5000)
      }
    },
    handleEdit() {
      alert("edit");
    }
  }
};
</script>

<style scoped>
.navb {
  width: 100%;
  top: 0;
  position: absolute;
}
.element-categoria {
  cursor: pointer;
  margin: 5px;
}
.container-list {
  flex-direction: column;
  display: flex;
  align-items: center;
  justify-content: center;
}
.list-categoria {
  width: 50%;
}
.icons {
  left: 0;
}
.icon-delete {
  margin-right: 10px;
}
</style>
<template>
  <div>
    <Navbar class="navb" />
    <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
    <b-modal id="modal-delete1" title="Tem certeza que deseja deletar" @ok="handleDelete()">
      <p class="my-4"></p>
    </b-modal>
    <div class="container-list">
      <div class="container-title">
        <h1 class="title">Lista de categorias</h1>
        <Button class="title button-title" message="Adicionar" path="/add-categoria"></Button>
      </div>
      <div class="list-categoria">
        <ul class="list-group">
          <li
            class="list-group-item d-flex justify-content-between align-items-center element-categoria"
            v-for="(categoria,index) in listCategoria"
            v-bind:key="index"
          >
            {{categoria.nome}}
            <div class="icons">
              <b-icon
                class="icon-delete"
                icon="trash-fill"
                aria-hidden="true"
                @click="showModal(categoria)"
              ></b-icon>
              <b-icon icon="pencil" aria-hidden="true" v-b-modal="'modal'"></b-icon>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Categoria from "../services/categoria";
import Navbar from "../components/Navbar";
import Alert from "../components/utils/Alert";
import Button from "../components/utils/Button";
export default {
  data() {
    return {
      listCategoria: [],
      visible: null,
      alertBody: {
        message: "",
        type: ""
      },
      itemDelete: null
    };
  },

  name: "teste",
  components: {
    Navbar,
    Alert,
    Button
  },

  async mounted() {
    try {
      const res = await Categoria.getAll();

      if (Object.keys(res.data).length != 0) {
        res.data.forEach(element => {
          const temp = {
            id: element.id,
            nome: element.nome
          };
          this.listCategoria.push(temp);
        });
      } else {
        const temp = {
          message: "Nenhuma categoria cadastrada",
          type: "info"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    } catch (e) {
      const temp = {
        message: "Erro ao buscar no banco: " + e.message,
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

    showModal(item) {
      this.itemDelete = item;
      this.$bvModal.show("modal-delete1");
    },

    async handleDelete() {
      try {
        await Categoria.del(this.itemDelete.id);
        const index = this.listCategoria.indexOf(this.itemDelete)
        this.listCategoria.splice(index,1)
        const temp = {
          message: "Item deletado com sucesso ! ",
          type: "primary"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      } catch (e) {
        const temp = {
          message: "Erro ao deletar categoria " + e.message,
          type: "danger"
        };
        this.alertBody = temp;
        this.visible = 5;
        setTimeout(function() {
          this.visible = null;
        }, 5000);
      }
    }
  }
};
</script>

<style scoped>
.icon-delete {
  margin-right: 10px;
}
.button-title {
  right: 0;
  /* position: absolute; */
  /* margin-left: 50px; */
}
.container-title {
  width: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.title {
  display: inline;
}
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
</style>
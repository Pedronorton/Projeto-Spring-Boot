<template>
  <div>
    <Alert class="alerta" message="alertBody.message" type="alertBody.type" :visible="visible" />
    <b-modal id="modal-save" title="Alterar imagem" @ok="handleSave()">
      <div>
        <b-form>
          <b-form-group>
              <b-img :src="loginImage"></b-img>
          </b-form-group>
          <b-form-group>
            <b-form-file
              v-model="file"
              :state="Boolean(file)"
              placeholder="Choose a file or drop it here..."
              drop-placeholder="Drop file here..."
            ></b-form-file>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>

    <b-nav class="navbar-light bg-light navb">
      <!-- <Navbar class="navb" /> -->
      <Sidebar />
      <div class="mb-2">
        <div @click="editImage()">
          <b-avatar class="teste" :src="loginImage"></b-avatar>
        </div>
      </div>
    </b-nav>
    <router-view></router-view>
  </div>
</template>

<style>
</style>
<script>
import { mapState } from "vuex";
import Sidebar from "./Sidebar";
import Alert from "./utils/Alert";
import Cliente from "../services/cliente";
export default {
  name: "AdminRouter",

  components: {
    Sidebar,
    Alert
  },
  data() {
    return {
      loginImage: null,
      file: null,
    };
  },

  async mounted() {
    try {
      const res = await Cliente.getUserAutenticated();
      this.loginImage = res.data.imageUrl;
    } catch (e) {
      alert(e);
    }
  },
  computed: {
    ...mapState({
      visible: state => state.visible
    })
  },
  methods: {
    editImage() {
      this.$bvModal.show("modal-save");
    },
    async handleSave() {
      try {
        await Cliente.postPicture(this.file);
        this.loginImage = this.file;
      } catch (e) {
        alert(e);
      }
    },
    teste(){
        console.log(this.file);
        
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
.alerta {
  position: absolute;
}
.teste {
  position: absolute;
  right: 0;
  margin-right: 16px;
  margin-top: 10px;
}
</style>
<template>
  <div>
    <div class="background-home">
      <b-img :src="require(`@/assets/wall.jpg`)" class="background-img"></b-img>
    </div>
    <div class="div-total-form">
      <b-container class="div-form">
        <div>
          <h1 class="title">Login</h1>
        </div>
        <div>
          <b-form>
            <b-form-group>
              <b-input placeholder="email" v-model="login.email"></b-input>
            </b-form-group>
            <b-form-group>
              <b-input placeholder="senha" type="password" v-model="login.senha"></b-input>
            </b-form-group>
          </b-form>
          <div>
              <b-overlay :show="isLoading" :opacity="opacity" :variant="variant"></b-overlay>

            <b-button @click="handleLogin()">
              LOGAR
            </b-button>
          </div>
        </div>
      </b-container>
    </div>
  </div>
</template>

<script>
import login from "../services/Login";
import { mapActions, mapState } from "vuex";
export default {
  name: "Login",
  components: {
  },
  data() {
    return {
      login: {
        email: "",
        senha: ""
      },
      alertBody: {
        message:"",
        type:""
      },
      opacity: 0.1,
      variant: "light",
      visible:null
    };
  },
  computed:{
    ...mapState({
      isLoading: state => state.isLoading,
    }),
  },
  methods: {
    ...mapActions(["setAuthLocalStorage", "changeLoading", "setTime"]),


    async handleLogin() {
      this.$store.dispatch('changeLoading')
      this.$store.dispatch('setTime',5)
      try {
        const res =  await login.post(this.login);

        this.$store.dispatch("setAuthLocalStorage", res.data);
        this.$router.push("/admin");
        
        } catch (e) {

        alert("Erro ao logar")
        
        // alert("Erro ao logar !");
      }
      this.$store.dispatch('changeLoading')
    }
  }
};
</script>

<style scoped>
.alerta{
  position: absolute;
}
.title {
  color: white;
}
.div-total-form {
  height: 100vh;
  width: 100%;
  padding-bottom: 100px;
  justify-content: center;
  display: flex;
  align-content: center;
  align-items: center;
}
.div-form {
  position: absolute;
}
.background-img {
  filter: invert(30%);
  height: 100vh;
  width: 100%;
}
.background-home {
  position: absolute;
  top: 0;
  height: 100vh;
  width: 100%;
}
</style>
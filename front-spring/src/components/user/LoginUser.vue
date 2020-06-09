<template>

<b-overlay :show="teste">
  <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
  <div class="div-geral col-md-12 col-sm-12">
    <b-img :src="require(`@/assets/logo_size.jpg`)"></b-img>
    <div class="div-login">
      <b-form>
        <b-form-group>
          <h1 class="title">Fazer Login</h1>
          <div class="div-input">
            <b-input v-model="login.email" class="input" placeholder="Email"></b-input>
          </div>
        </b-form-group>
        <b-form-group>
          <div class="div-input">
            <b-input v-model="login.senha" class="input" placeholder="Senha" type="password"></b-input>
          </div>
        </b-form-group>
        <b-form-group>
          <b-button class="button entrar" @click="handleLogin()">Entrar</b-button>
        </b-form-group>
      </b-form>
      
      <div>
          <hr class="linha"> 
          <h5 class="descricao">Novo no sistema ?</h5>
          <a class="esqueci-senha" href="/forgot-pass">Esqueci minha senha</a>
      </div>

      <div>
        <b-button class="button criar">Criar conta</b-button>
      </div>
    </div>
  </div>
</b-overlay>
</template>
<script>
import {mapActions} from 'vuex'
import Login from "@/services/Login"
import Alert from "../utils/Alert"
export default {
  name: 'LoginUser',
  components:{
      Alert
  },  
  data() {
    return {
        login: {
            email: "",
            senha:"",
        },
        teste: false,

        alertBody:{
            message:"",
            type:""
        }
    };
  },
  methods:{
    ...mapActions(["setAuthLocalStorage", "changeLoading", "setTime"]),
      async handleLogin(){
          this.teste = true
          try{
              const response = await Login.post(this.login)
              this.showAlert("Sucesso", "primary", null)
              this.$store.dispatch("setAuthLocalStorage", response.data);
              this.$router.push("/")
          }
          catch(e){
              this.showAlert("Erro ao logar", "danger", e.message)
          }
          this.teste = false
      },
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
  }
};
</script>

<style scoped>

.title{
    margin: 20px;
}
.entrar {
  background-color: #A40606;
  border-color: #A40606;
}
.entrar:hover{
    background-color: #710000;
    border-color: #710000;
}
.criar {
    margin-bottom: 20px;
    margin-top: 10px;
}
.descricao{
    font-size: 12px;
}
.esqueci-senha{
    font-size: 12px;
    cursor: pointer;
    text-decoration: underline;
}
.linha{
  width: 50%;
}
.button {
  width: 80%;
}
.div-input {
  display: flex;
  justify-content: center;
  align-items: center;
}
.div-form {
  display: flex;
  justify-items: center;
  align-items: center;
}
.input {
  width: 80%;
}
.div-login {
  width: 40%;
  border: 1px solid;
  border-color: gray;
}
.div-geral {
  display: flex;
  justify-items: center;
  align-items: center;
  flex-direction: column;
}

</style>
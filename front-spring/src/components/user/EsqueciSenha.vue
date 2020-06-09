<template>
<b-overlay :show="teste">
  <Alert :message="alertBody.message" :type="alertBody.type" :visible="visible" />
  <div class="div-geral col-md-12 col-sm-12">
    <b-img :src="require(`@/assets/logo_size.jpg`)"></b-img>
    <div class="div-login">
      <b-form>
        <b-form-group>
          <h1 class="title">Recuperar senha</h1>
          <div class="div-input">
            <b-input class="input" placeholder="Email"></b-input>
          </div>
        </b-form-group>
        <b-form-group>
          <b-button class="button entrar" @click="handleForgotPass()">Continuar</b-button>
        </b-form-group>
      </b-form>
    </div>
  </div>
</b-overlay>
</template>
<script>
// import Login from "@/services/Login"
import Alert from "../utils/Alert"
export default {
  name:'EsqueciSenha',
  components:{
      Alert
  },
  data() {
    return {
        formData: {
            email:"",
        },
        teste: false,

        alertBody:{
            message:"",
            type:""
        }
    };
  },
  methods:{
      
      handleForgotPass(){

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
.title {
  margin: 20px;
}
.entrar {
  background-color: #a40606;
  border-color: #a40606;
}
.entrar:hover {
  background-color: #710000;
  border-color: #710000;
}
.criar {
  margin-bottom: 20px;
  margin-top: 10px;
}
.descricao {
  font-size: 12px;
}
.esqueci-senha {
  font-size: 12px;
  cursor: pointer;
  text-decoration: underline;
}
.linha {
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
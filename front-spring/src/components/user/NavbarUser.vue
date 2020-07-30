<template>
  <nav class="navbar">
    <div class="nav-left">
      <router-link to="/">
        <b-img :src="require(`@/assets/logo_size.jpg`)" class="logo-image"></b-img>
      </router-link>
    </div>

    <div class="nav-search">
      
    <ul class="navbar-nav">
        <li>
        <b-form>
          <b-form-group>
            <b-input placeholder="O que está procurando ?"></b-input>
          </b-form-group>
        </b-form>
        </li>
        <li class="nav-item nome">{{items[0].label}}{{items[0].nameUser}}
        </li>
      </ul>
    </div>
    <div class="nav-right">
      <ul class="navbar-nav">
        <li class="nav-item">
          <b-button id="button-modal-cart" class="button-nav" variant="primary" @click="showModalCart">
            Carrinho
            <b-badge variant="dark">{{getProductsOnCart.length}}</b-badge>
          </b-button>
        </li>
        <li class="nav-item">
          <b-button id="btn-login" class ="button-nav" variant="primary" v-if="!presentUserFlag" href="/login-user">
            Login
          </b-button>
          <b-button class="button-nav" variant="primary" v-if="presentUserFlag" @click="logout()">
            Logout
          </b-button>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import Cliente from "@/services/cliente";
import {mapGetters, mapActions} from 'vuex'
export default {
  name: "NavbarUser",
  components: {},
  data() {
    return {
      presentUserFlag:false,
      items: [
        {
          id: "0",
          label: "olá, ",
          nameUser: "cliente"
        },
        {
          id: "1",
          label: "image",
          path: ""
        },
        {
          id: "2",
          label:"",

        }
      ]
    };
  },
  computed: {
    ...mapGetters([
      'getProductsOnCart',
    ])
  },
  async created() {
    try {
      const response = await Cliente.getUserAutenticated();
      this.items[0].nameUser = response.data.nome;
      this.presentUserFlag = true
    } catch (e) {
      console.log("Erro: "+ e.message +" ou nenhum cliente logado");
    }
    this.items[2].label = Object.keys(this.$store.state.tableProductsOnCart).length
  },
  methods:{
    ...mapActions([
      'showModalCart',
    ]),
    logout(){
      localStorage.removeItem('token');
      this.items[0].nameUser = "cliente"
      this.presentUserFlag = false
    }
  }
};
</script>

<style scoped>
.navbar {
  background-color: #333;
} 
.nav-left {
  position: relative;
  float: left;
  width: auto;
  display: inline-block;
}
.nav-search {
  position: relative;
  display: flex;
  flex-direction: row;
  white-space: nowrap;
  overflow: hidden;
}
.search{
    top:50%;
}
.nome {
  margin-left: 10px;
  margin-top: 5px;
  color: white;
}
.nav-right {
  position: relative;
  float: right;
  width: auto;
  display: inline-block;
  margin-right: 50px;
}

.navbar-nav {
  display: flex;
  flex-direction: row;
  align-items: center;
  align-content: center;
}

.button-nav{
  margin-left: 20px;
  background-color: #A40606;
  border-color: #A40606;
}
.button-nav:hover{
  background-color: #710000;
  border-color: #710000;
}

.logo-image {
  width: 100px;
}
.logo-image:hover{
  animation: test 2s;
}

@keyframes test {
  0% {
    transform: rotate(0deg);
  }
  100%{
    transform: rotate(360deg);
  }
}
</style>
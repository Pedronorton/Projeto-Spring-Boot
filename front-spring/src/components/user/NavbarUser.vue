<template>
  <nav class="navbar">
    <div class="nav-left">
      <b-img :src="require(`@/assets/logo_size.jpg`)" class="logo-image"></b-img>
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
      
      
        <li class="nav-item nome">{{items[0].label}} {{items[0].nameUser}}</li>
      </ul>
    </div>
    <div class="nav-right">
      <ul class="navbar-nav">
        <li class="nav-item">
          <b-button @click="showModalCart">
            Carrinho
            <b-badge variant="primary">{{getProductsOnCart.length}}</b-badge>
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
      items: [
        {
          id: "0",
          label: "olá, ",
          nameUser: ""
        },
        {
          id: "1",
          label: "image",
          path: ""
        },
        {
          id: "0",
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
    } catch (e) {
      alert(e);
    }
    this.items[2].label = Object.keys(this.$store.state.tableProductsOnCart).length
  },
  methods:{
    ...mapActions([
      'showModalCart',
    ]),
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
.nav-item:nth-child(2) {
}

.nav-item {
}
.nav-item:first-child {
  /* margin-right: 50px;
  margin-left: 50px; */
}

.logo-image {
  width: 100px;
}
</style>
<template>
  <div>
      <div>
          <NavbarUser class="navb"/>
          <FiltroProdutos/>
          <CartModal v-if="getPopupCart"/>
      </div>

    <router-view></router-view>
  </div>
</template>

<style>
</style>
<script>
import {mapGetters, mapActions} from 'vuex'
import NavbarUser from "../user/NavbarUser";
import FiltroProdutos from "../FiltroProdutos";
import CartModal from "../modals/CartModal"
export default {
  name: "UserRouter",

  components: {
    NavbarUser,
    FiltroProdutos,
    CartModal
  },
    computed: {
    ...mapGetters([
      'getPopupCart',
    ]),
  },
  mounted(){
    if(this.getPopupCart == false){
      window.addEventListener("click", this.closeModal)
    }
  },
  methods:{
    ...mapActions([
      'showModalCart'
    ]),
    closeModal(evt){
      
      if(evt.target.getAttribute('id') == "cart"){
        this.showModalCart();
      }
      
    }
  },

  data() {
    return {
      
    };
  },

};
</script>
<style>
</style>
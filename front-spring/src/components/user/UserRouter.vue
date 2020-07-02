<template>
  <div>
      <div>
          <NavbarUser class="navb"/>
          <FiltroProdutos/>
          <transition name="appear">
            <CartModal class="cart" v-if="getPopupCart"/>
          </transition>
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
.navb{
  position: fixed;
}
.cart{
  position: absolute;
}
.appear-enter-active {
  animation: appear-animation .5s;
}
.appear-leave-active {
  animation: appear-animation .5s reverse;
}
@keyframes appear-animation {
  0% {
    transform: translateY(-50%);
    opacity: 0;
  }
  100% {
    transform: translateY(0%);
    opacity: 1;
  }
}
</style>
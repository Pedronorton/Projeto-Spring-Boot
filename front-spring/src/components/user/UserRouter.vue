<template>
  <div>
      <div>
          <NavbarUser class="navb"/>
          <FiltroProdutos/>
      </div>

    <router-view></router-view>
  </div>
</template>

<style>
</style>
<script>
import NavbarUser from "../user/NavbarUser";
import FiltroProdutos from "../FiltroProdutos";
export default {
  name: "UserRouter",

  components: {
    NavbarUser,
    FiltroProdutos,
  },
  data() {
    return {
      
    };
  },

};
</script>
<style scoped>

</style>
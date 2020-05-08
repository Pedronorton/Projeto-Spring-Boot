<template>
  <div>
    <Navbar class="navb" />
    
  </div>
</template>

<script>
import Navbar from "../components/Navbar";

export default {
  data() {
    return {};
  },

  name: "Home",
  components: {
    Navbar
  }
};
</script>

<style scoped>
.navb {
  width: 100%;
  top: 0;
  position: absolute;
}
</style>
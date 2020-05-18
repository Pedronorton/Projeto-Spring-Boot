<template>
<div>
    <b-nav class="navbar-light bg-light navb">
    <!-- <Navbar class="navb" /> -->
    <Sidebar/>
    <div class="mb-2">
    <b-avatar class="teste" src="https://placekitten.com/300/300"></b-avatar>
    
    </div>
    </b-nav>
    <router-view></router-view>    
</div>
</template>

<style>
    
</style>
<script>
import Sidebar from './Sidebar'
export default {
    
    name: 'AdminRouter',

    components:{
        Sidebar
    }
}
</script>
<style scoped>
.navb {
  width: 100%;
  top: 0;
  position: absolute;
}
.teste{
    position: absolute;
    right: 0;
    margin-right: 16px;
    margin-top: 10px;
}
</style>
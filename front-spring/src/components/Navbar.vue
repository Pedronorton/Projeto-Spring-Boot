<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">Navbar</a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          
          <li class="nav-item active" v-for="(item,index) in itemNav" v-bind:key="index">
            <a class="nav-link" :href='item.route' >
              {{item.nome}}
              <span class="sr-only">(current)</span>
            </a>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'Navbar',
  components: {
  },

  data(){
    return {
      itemNav: [
      {
        id:'0',
        nome:'Cidades',
        route:'/cidades'
      },
      {
        id:'1',
        nome:'Produtos',
        route:'/produtos'
      },
      {
        id:'2',
        nome:'Categorias',
        route:'/categorias'
      },
      {
        id:'3',
        nome:'Fazer Pedido',
        route:'/add-pedido'
      },
      {
        id:'4',
        nome:'Adicionar Usuário',
        route:'/add-user'
      },
      {
        id:'5',
        nome:'Usuarios',
        route:'/clientes'
      },
      {
        id:'6',
        nome:'Ver Pedidos',
        route:'/pedidos'
      },
      
      ]
    }
  },
  
};
</script>

<style scoped>
.navbar{
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
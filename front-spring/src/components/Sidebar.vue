<template>
  <div>
    <div class="h2 icon-side">
      <b-icon  icon="blockquote-right" v-b-toggle.sidebar-no-header></b-icon>
    </div>
    <b-sidebar id="sidebar-no-header" aria-labelledby="sidebar-no-header-title" no-header shadow > 
      <template v-slot:default="{ hide }">
        <div class="icon-side h2">
          <b-icon icon="blockquote-left" @click="hide"></b-icon>
        </div>
        <hr>
        <div class="p-3">
          <nav class="mb-3">
            <b-nav vertical>
              <p v-for="item in itens" :key="item.id" >
                  
                <b-nav-item class="item-side"  @click="navigateTo(item.route)">
                  <b-icon :icon="item.icon"></b-icon>
                  {{item.title}}
                  <hr>
                </b-nav-item>        
              </p>
            </b-nav>
          </nav>
        </div>
      </template>
    </b-sidebar>
  </div>
</template>

<script>
export default {
  name: "Sidebar",
  components: {},
  data() {
    return {
        itens:[
      {
        id:'',
        title:'Home',
        route:'/admin',
        icon:"house-door-fill",

      },
      {
        id:'1',
        title:'Produtos',
        route:'/produtos',
        icon:"collection",
      },
      {
        id:'2',
        title:'Categorias',
        route:'/categorias',
        icon:"files",
      },
      
      
      {
        id:'3',
        title:'Clientes',
        route:'/clientes',
        icon:"person-square",
      },
      {
        id:'4',
        title:'Pedidos',
        route:'/pedidos',
        icon:"file-earmark-text",
      },
        ]
    };
  },
  methods:{
      navigateTo(route){
          this.$router.push(route);
      }
  }
};
</script>

<style scoped>
.icon-side {
  display: flex;
  align-items: flex-start;
  margin-left: 16px;
  margin-top: 10px;
}
.item-side{
    color: white !important;
}
#a{
    color: white !important;
}

</style>
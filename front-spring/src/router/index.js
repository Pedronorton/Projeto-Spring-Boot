import Vue from 'vue'
import Router from 'vue-router'
import Categorias from '../components/Categorias'
import Usuarios from '../components/Usuarios'
import Home from '../components/Home'
import AddUser from '../components/AdicionarUsuario'
import Produto from '../components/Produto'
import AddCategoria from '../components/AdicionarCategoria'
import FormPedidos from '../components/FormPedidos'
import Pedido from "../components/Pedido"
import AdminRouter from "../components/AdminRouter"
import Login from "../components/Login"
import Auth from "../services/Auth"
import HomeUser from "../views/HomeUser"
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/',
      name: 'HomeUser',
      component: HomeUser,
    },

    {
      path:"",
      name:"AdminRouter",
      component:AdminRouter , 
      async beforeEnter(to, from, next) {
        if(await Auth.isValidToken() == true){
          next()
        }else{
          next("/login")
        }
      },
    children:
    [
    { 
      path:"/admin",
      name: 'Home',
      component: Home,
      
    },
    {
      path: '/categorias',
      name: 'Categorias',
      component: Categorias 
    },
    {
      path: '/clientes',
      name: 'Clientes',
      component: Usuarios 
    },
    {
      path: '/produtos',
      name: 'Produto',
      component: Produto 
    },
    {
      path: '/pedidos',
      name: 'Pedido',
      component: Pedido
    },
    {
      path: '/add-user',
      name: 'AdicionarUsuario',
      component: AddUser 
    },
   
    {
      path: '/add-categoria',
      name: 'AdicionarCategoria',
      component: AddCategoria 
    }, 
    {
      path: '/add-pedido',
      name: 'FormPedidos',
      component: FormPedidos 
    }, 
  ]
},
    
     
  ]
})
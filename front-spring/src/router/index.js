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
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home 
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
      path: '/add-user',
      name: 'AdicionarUsuario',
      component: AddUser 
    },

    {
      path: '/produtos',
      name: 'Produto',
      component: Produto 
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
    {
      path: '/pedidos',
      name: 'Pedido',
      component: Pedido
    }, 
  ]
})
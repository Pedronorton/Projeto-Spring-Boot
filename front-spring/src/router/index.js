import Vue from 'vue'
import Router from 'vue-router'
import Categorias from '../components/Categorias'
import Usuarios from '../components/Usuarios'
import Home from '../components/Home'
import Produto from '../components/Produto'
import FormPedidos from '../components/FormPedidos'
import Pedido from "../components/Pedido"
import AdminRouter from "../components/AdminRouter"
import Login from "../components/Login"
import Auth from "../services/Auth"
import HomeUser from "../views/HomeUser"
import LoginUser from "../components/user/LoginUser"
import EsqueciSenha from "../components/user/EsqueciSenha"
import DetalhesProduto from "../components/user/DetalhesProduto"
import FecharCompra from "../components/user/FecharCompra"
import UserRouter from "../components/user/UserRouter"
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
      name: 'UserRouter',
      component: UserRouter,
      children:[
        {
          path: '/',
          name: 'HomeUser',
          component: HomeUser,
        },
        {
      
          path: '/detalhes-produtos/:id',
          name: 'DetalhesProduto',
          component: DetalhesProduto,
        },
        {
      
          path: '/finalizar-compra',
          name: 'FecharCompra',
          component: FecharCompra,
        },
      ]
    },
    {
      path: '/login-user',
      name: 'LoginUser',
      component: LoginUser,
    },
    {
      path: '/forgot-pass',
      name: 'EsqueciSenha',
      component: EsqueciSenha,
    },

    {
      path:"",
      name:"AdminRouter",
      component:AdminRouter , 
      async beforeEnter(to, from, next) {
        const user = await Auth.getUser();
        if(user == "ROLE_ADMIN"){
          next();
        }else{
          alert("Usuário ou senhas errados")
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
      path: '/add-pedido',
      name: 'FormPedidos',
      component: FormPedidos 
    }, 
  ]
},
    
     
  ]
})
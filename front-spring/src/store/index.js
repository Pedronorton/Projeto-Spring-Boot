import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex);



export default new Vuex.Store({
    state: {
      time: 0,
      auth:"",
      isLoading: false,
      tableDataProdutos:[],
      allIdsCategorias:[],
      tableProductsOnCart:[],
      showPopupCart: false,
    },
    mutations: {
      setTime(state, value){
        state.time = value
      },

      setAuthLocalStorage(state, value){
        state.auth = value
        localStorage.setItem('token', state.auth)
      },
      changeLoading(state){
        if(state.isLoading == true){
          state.isLoading = false
        }else{
          state.isLoading = true
        }
      },
      setObjToTableDataProdutos(state, obj){
        state.tableDataProdutos.push(obj)
      },
      setIdCategoria(state, obj){
        state.allIdsCategorias.push(obj)
      },
      setProductOnCart(state, obj){
        state.tableProductsOnCart.push(obj)
      },
      showModalCart: (state) => {        
        state.showPopupCart = !state.showPopupCart;
      },
      removeProductOnCart: (state,index) => {
        state.tableProductsOnCart.splice(index,1)
      }
      
    },
    getters: {
      getTableDataProdutos: state => state.tableDataProdutos,
      getAllIdsCategorias(state){
        return state.allIdsCategorias;
      },

      getProductsOnCart: state => state.tableProductsOnCart,
      getPopupCart: state => state.showPopupCart,

    },
    actions: { 
      setTime(context, value){
        context.commit('setTime', value)
      },
      setAuthLocalStorage(context, value){
        context.commit('setAuthLocalStorage',value)
      },
      changeLoading(context){
        context.commit('changeLoading')
      },
      getTableDataProdutos(context){
        context.commit('getTableDataProdutos')
      },
      setObjToTableDataProdutos(context){
        context.commit('setObjToTableDataProdutos')
      },
      setProductOnCart(context){
        context.commit('setProductOnCart')
      },
      showModalCart: (context) => {
        context.commit('showModalCart');
      },
      removeProductOnCart: (context, index) =>{
        context.commit('removeProductOnCart',index)
      }
    },
    modules: {}
  });
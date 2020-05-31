import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex);



export default new Vuex.Store({
    state: {
      time: 0,
      auth:"",
      isLoading: false,
      tableDataProdutos:[]
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
      
    },
    getters: {
      getTableDataProdutos(state){
        return state.tableDataProdutos;
      }
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
      }
    },
    modules: {}
  });
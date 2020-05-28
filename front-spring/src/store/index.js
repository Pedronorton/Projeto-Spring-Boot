import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex);



export default new Vuex.Store({
    state: {
      time: 0,
      auth:"",
      isLoading: false
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
      }

    },
    getters: {
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
      }
    },
    modules: {}
  });
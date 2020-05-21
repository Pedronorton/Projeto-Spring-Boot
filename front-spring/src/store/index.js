import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex);


export default new Vuex.Store({
    state: {
      time: 0,
    },
    mutations: {
      setTime(state, value){
        state.time = value
      }
    },
    getters: {
    },
    actions: { 
      setTime(context, value){
        context.commit('setTime', value)
      }
    },
    modules: {}
  });
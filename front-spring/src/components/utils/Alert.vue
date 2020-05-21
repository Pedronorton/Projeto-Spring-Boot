<template>
  <div>
    <b-alert
      :show="time"
      dismissible
      :variant="type"
      @dismissed="setTime(0)" 
      @dismiss-count-down="countDownChanged"
    >
    {{message}}</b-alert>
  </div>
</template>

<script>
import {mapState ,mapActions} from 'vuex'
export default {
  name: "Alert",
  props: {
    visible: Number,
    message: String,
    type: String
  },
  data() {
    return {
    };
  },
  computed:{
    ...mapState({
      time: state => state.time
    })
  },
  methods: {
    ...mapActions([
      'changeValue',
      'setTime'
    ]),
    countDownChanged(val) { 
      this.$store.dispatch('setTime', val)
    },
    
  },
};
</script>
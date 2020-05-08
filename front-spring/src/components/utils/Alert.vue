<template>
  <div>
    <b-alert
      :show="visible"
      dismissible
      fade
      :variant="type"
      @dismiss-count-down="countDownChanged"
    >
    {{message}}</b-alert>
  </div>
</template>

<script>
export default {
  name: "Alert",
  props: {
    visible: Number,
    message: String,
    type: String
  },
  data() {
    return {
      dismissCountDown: 0,
    };
  },
  methods: {
    countDownChanged(dismissCountDown) { 
      this.dismissCountDown = dismissCountDown;
    },
  }
};
</script>
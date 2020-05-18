<template>
  <div class="row">
    <div class="col">
      <b-card-group deck class="card-admin">
        <b-card
          bg-variant="success"
          header-tag="header"
          footer="Ver mais"
          footer-tag="footer"
          :title="title"
          @click="navigateTo()"
        >
          <p class="h1">
            <b-icon :icon="icon" class="icon"></b-icon>
          </p>
          <b-card-text>Quantidade de {{slug}}: {{contentNumber}}</b-card-text>
        </b-card>
      </b-card-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "CardAdmin",
  components: {},

  data() {
    return {};
  },
  props: {
    title: String,
    icon: String,
    contentNumber: Number,
    slug: String
  },
  
  methods:{
      navigateTo(){
          this.$router.push("/"+this.slug);
      }
  }

};
</script>

<style scoped>
.card-admin{
    cursor: pointer;
}
.icon:hover {
  width: 15%;
  height: 15%;
}
</style>
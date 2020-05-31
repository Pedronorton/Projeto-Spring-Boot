<template>
  <div>
    <b-container class="teste">
      <b-carousel id="carousel-fade" style="text-shadow: 0px 0px 2px #000" fade indicators img-width="400" img-height="400">
        <b-carousel-slide caption="First slide" img-src="https://http2.mlstatic.com/optimize/o:f_webp/resources/deals/exhibitors_resources/mlb-home-desktop-slider-picture-4409b903-1710-4948-bbe3-c1ac1442e925.jpg" img-width="400" img-height="400">
          
        </b-carousel-slide>
        <b-carousel-slide caption="Second Slide" img-src="https://http2.mlstatic.com/optimize/o:f_webp/resources/deals/exhibitors_resources/mlb-home-desktop-slider-picture-0bfca7c2-d014-42e6-a2d5-7aef5cd457bc.jpg"></b-carousel-slide>
        <b-carousel-slide caption="Third Slide" img-src="https://http2.mlstatic.com/optimize/o:f_webp/resources/deals/exhibitors_resources/mlb-home-desktop-slider-picture-bfb827fb-84b4-4d28-be0c-d27f2767e063.jpg"></b-carousel-slide>
      </b-carousel>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "HomeCarroussel",
  components: {},

  data() {
    return {};
  }
};
</script>

<style scoped>

</style>
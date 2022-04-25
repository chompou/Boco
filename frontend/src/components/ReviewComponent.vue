<template>
  <h3>Reviews</h3>
  <div v-for="review in reviews" :key="review" id="container">
    <b id="name">{{ review.name }}</b>
    <br />
    <div style="display: inline-block">
      <star-rating
        :inline="true"
        :star-size="20"
        :read-only="true"
        :show-rating="false"
        :rating="review.rating"
      ></star-rating>
      {{ review.description }}
    </div>
  </div>
</template>

<script>
import StarRating from "vue-star-rating";
export default {
  props: ["reviews"],
  components: {
    StarRating,
  },
  data() {
    return {
      users: [
        {
          name: "Olav Eikrem",
          rating: 3,
          description: "Helt ok",
        },
        {
          name: "Øyvind Bjøntegaard",
          rating: 5,
          description: "Fantastisk!",
        },
      ],
    };
  },
};
</script>

<style>
#container {
  margin-top: 20px;
}
</style>

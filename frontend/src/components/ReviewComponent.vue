<template>
  <h3>Reviews</h3>
  <div v-for="user in users" :key="user" id="container">
    <b id="name">{{ user.name }}</b>
    <br />
    <div style="display: inline-block">
      <star-rating
        :inline="true"
        :star-size="20"
        :read-only="true"
        :show-rating="false"
        :rating="user.rating"
      ></star-rating>
      {{ user.description }}
    </div>
  </div>
</template>

<script>
import StarRating from "vue-star-rating";
export default {
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

<template>
  <div id="items">
    <p>Rating:</p>
    <star-rating
      :rating="rating"
      :read-only="true"
      v-bind:max-rating="5"
      inactive-color="#d8d8d8"
      active-color="#ffd055"
      v-bind:star-size="20"
    >
    </star-rating>
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
      rating: 4,
    };
  },
};
</script>

<style scoped>
#items {
  margin-right: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

p {
  font-size: 20px;
}
</style>

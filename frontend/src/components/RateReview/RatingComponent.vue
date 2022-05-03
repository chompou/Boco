<template>
  <div id="items">
    <star-rating
      v-if="rating === 5"
      :rating="rating"
      :read-only="true"
      v-bind:max-rating="5"
      inactive-color="#d8d8d8"
      active-color="#fffb00"
      :rounded-corners="true"
      v-bind:star-size="20"
      border-color="#2986cc"
      :border-width="3"
    >
    </star-rating>
    <star-rating
      v-else
      :rating="rating"
      :read-only="true"
      v-bind:max-rating="5"
      inactive-color="#d8d8d8"
      active-color="#ffd055"
      v-bind:star-size="20"
      border-color="#999"
      :border-width="3"
    >
    </star-rating>
  </div>
</template>

<script>
import StarRating from "vue-star-rating";
export default {
  props: ["rating"],
  components: {
    StarRating,
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
#items {
  margin-right: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 20px;
}
</style>

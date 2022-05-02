<template>
  <div class="container">
    <div class="rating-container">
      <h2>Rating</h2>
      <div class="rating-box-container">
        <rating-component v-for="i in 3" :key="i" />
      </div>
    </div>
    <h2>Reviews</h2>
    <div class="review-list-container">
      <div class="review-container" v-for="review in reviews" :key="review">
        <b>{{ review.name }}</b>
        <rating-component :rating="review.rating" />
        <p>{{ review.description }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RatingComponent.vue";
export default {
  components: { RatingComponent },

  data() {
    return {
      reviews: [],
    };
  },

  created() {},
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  gap: 50px;
}

.rating-box-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.review-list-container {
  display: flex;
  justify-content: space-evenly;
  gap: 40px;
  flex-direction: row;
  flex-wrap: wrap;
}

.review-container {
  border: 1px solid;
  border-radius: 15px;
  max-width: 45%;
  padding: 15px;
  overflow-wrap: break-word;
  background-color: rgb(215, 215, 215, 0.5);
}
</style>

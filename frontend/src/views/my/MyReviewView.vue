<template>
  <div class="container">
    <div class="rating-container">
      <h2>Rating</h2>
      <div class="rating-box-container">
        <div class="items">
          <p class="ratingText">Lease out</p>
          <RatingComponent :rating="5" />
        </div>
        <div class="items">
          <p class="ratingText">Overall</p>
          <RatingComponent :rating="3" />
        </div>
        <div class="items">
          <p class="ratingText">Lease in</p>
          <RatingComponent :rating="0" />
        </div>
      </div>
    </div>
    <h2>Reviews</h2>
    <div class="review-list-container">
      <div
        class="review-container"
        v-for="review in reviews.slice().reverse()"
        :key="review"
      >
        <b>{{ review.displayName }}</b>
        <rating-component :rating="review.rating" />
        <p>{{ review.comment }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RateReview/RatingComponent.vue";
import apiService from "@/services/apiService";
export default {
  components: { RatingComponent },

  data() {
    return {
      reviews: [],
    };
  },

  created() {
    apiService
      .getReviews(
        { profileId: this.$store.state.loggedInUser, reviewType: "owner" },
        0,
        20
      )
      .then((response) => (this.reviews = response.data))
      .catch((error) => console.log(error));
  },
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
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid;
  border-radius: 15px;
  max-width: 45%;
  padding: 15px;
  overflow-wrap: break-word;
  background-color: rgb(215, 215, 215, 0.5);
}

.items {
  display: flex;
  flex-direction: column;
  font-size: 20px;
}

.ratingText {
  margin-right: 30px;
}
</style>

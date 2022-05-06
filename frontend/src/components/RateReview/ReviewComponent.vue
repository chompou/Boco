<template>
  <h3 id="reviews">Reviews</h3>
  <div class="review-container">
    <div
      v-for="review in reviews.slice().reverse()"
      :key="review"
      id="container"
    >
      <b id="name">{{ review.displayName }}</b>
      <div style="display: inline-block">
        <star-rating
          :inline="true"
          :star-size="20"
          :read-only="true"
          :show-rating="false"
          :rating="review.rating"
        ></star-rating>
        {{ review.comment }}
      </div>
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
};
</script>

<style>
#container {
  margin-top: 20px;
  justify-items: center;
}

#reviews {
  text-align: center;
}

.review-container {
  display: grid;
  grid-template-columns: min-content min-content;
  column-gap: 50px;
}

#container {
  border-radius: 10px;
  padding: 20px;
  justify-content: center;
  align-items: center;
  text-align: left;
  gap: 50px;

  color: var(--text-color);
  background: var(--background-color-header-nav-footer);
}
</style>

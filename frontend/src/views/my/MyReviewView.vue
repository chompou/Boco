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
        <p>{{ review.rating }}/5 - {{ review.description }}</p>
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
      reviews: [
        {
          name: "Olav Eikrem",
          rating: 5,
          description:
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris eget lobortis lectus. Cras mattis sem odio, at convallis odio dignissim ac. Mauris turpis turpis, lobortis vel aliquet at, lacinia sed quam. Integer vel fringilla sem. Suspendisse non euismod lacus. Ut imperdiet, lorem sed volutpat auctor, mauris tellus aliquam magna, in venenatis ex neque id enim. Nullam consequat nec velit eget faucibus. Ut ut arcu facilisis, pulvinar erat vel, hendrerit purus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Quisque non venenatis massa. Phasellus eu tincidunt metus. Vestibulum consectetur lorem id ultricies suscipit. Cras cursus eu augue vitae sagittis. Sed hendrerit eleifend felis, ac mattis neque eleifend sit amet. Donec in gravida justo, ac tincidunt mi. Ut rutrum leo non viverra volutpat.",
        },
        {
          name: "Olav Eikrem",
          rating: 1,
          description:
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed hendrerit eleifend felis, ac mattis neque eleifend sit amet. Donec in gravida justo, ac tincidunt mi. Ut rutrum leo non viverra volutpat.",
        },
        {
          name: "Olav Eikrem",
          rating: 2,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
        {
          name: "Olav Eikrem",
          rating: 10,
          description: "Helt ok",
        },
      ],
    };
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
  border: 1px solid;
  border-radius: 15px;
  max-width: 45%;
  padding: 15px;
  overflow-wrap: break-word;
  background-color: rgb(215, 215, 215, 0.5);
}
</style>

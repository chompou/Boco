<template>
  <div class="newRating">
    <div class="ratingAndText">
      <h5>Rate user</h5>
      <star-rating
        :rating="ownerReview.rating"
        :animate="true"
        v-bind:max-rating="5"
        inactive-color="#d8d8d8"
        active-color="#ffd055"
        v-bind:star-size="20"
        border-color="#999"
        :border-width="3"
      >
      </star-rating>
    </div>
    <div>
      <h5>Description for user</h5>
      <textarea
        v-model="ownerReview.comment"
        placeholder="Description"
        class="description"
        name="description"
      ></textarea>
    </div>
    <div v-if="!isOwner" class="ratingAndText">
      <h5>Rate item</h5>
      <star-rating
        :rating="itemReview.rating"
        :animate="true"
        v-bind:max-rating="5"
        inactive-color="#d8d8d8"
        active-color="#ffd055"
        v-bind:star-size="20"
        border-color="#999"
        :border-width="3"
      >
      </star-rating>
    </div>
    <div v-if="!isOwner">
      <h5>Description for item</h5>
      <textarea
        v-model="itemReview.comment"
        placeholder="Description"
        class="description"
        name="description"
      ></textarea>
    </div>
  </div>
  <div id="CreateButtons" class="element">
    <button class="CreateButton" @click="onSubmit">Submit</button>
    <button id="Delete" class="CreateButton" @click="onDismiss">Dismiss</button>
  </div>
</template>

<script>
import StarRating from "vue-star-rating";
import apiService from "@/services/apiService";
export default {
  props: ["id", "owner"],

  components: {
    StarRating,
  },

  data() {
    return {
      itemReview: {
        rating: 1,
        comment: "",
        id: this.id,
      },
      ownerReview: {
        rating: 1,
        comment: "",
        id: this.id,
      },
    };
  },

  methods: {
    onSubmit() {
      if (this.isOwner) {
        apiService.giveReview(this.ownerReview, "leasee");
      } else {
        apiService.giveReview(this.ownerReview, "owner");
        apiService.giveReview(this.itemReview, "item");
      }
    },
  },

  computed: {
    isOwner() {
      return this.owner == "true" ? true : false;
    },
  },

  created() {
    console.log(this.owner);
  },
};
</script>

<style scoped>
.newRating {
  display: grid;
  grid-template-columns: 100%;
  justify-items: center;
}

.ratingAndText {
  margin: 30px;
  font-size: 20px;
}

h5 {
  text-align: center;
}

.description {
  width: 400px;
  display: block;
  height: 100px;
  font-size: 15px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.CreateButton {
  margin: 20px;
  color: white;
  align-items: center;
  background-color: var(--button-color);
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto,
    "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell,
    "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  justify-content: center;
  line-height: 20px;
  max-width: 480px;
  min-height: 40px;
  min-width: 0;
  overflow: hidden;
  padding: 0 20px;
  text-align: center;
  touch-action: manipulation;
  transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
}

#CreateButtons {
  margin-top: 20px;
  display: flex;
  justify-content: space-evenly;
}

.CreateButton:hover {
  background-color: var(--button-hover);
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#Delete {
  background: #ff6565;
}

#Delete:hover {
  background: #b74646;
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}
</style>

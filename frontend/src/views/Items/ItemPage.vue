<template>
  <div class="mainContainer">
    <Transition>
      <lease-request-component
        v-if="leaseOverlay"
        @close-overlay="leaseOverlay = false"
      />
    </Transition>

    <div class="container">
      <div>
        <div class="imageButtons">
          <img alt="Vue logo" src="@/assets/service.png" />
          <div v-if="my">
            <button class="editButtons">Set Active</button>
            <button class="editButtons">Edit</button>
            <button class="editButtons">Delete</button>
          </div>
          <button class="leaseButton" v-else @click="leaseOverlay = true">
            Book leasing
          </button>
        </div>
        <div id="About">
          <div id="About11">
            <div id="About1">
              <h2>{{ item.name }}</h2>
              <div id="category">
                <label>Category: </label>
                <!-- <label v-for="item in category" :key="item">{{ item }}, </label> -->
              </div>
              <p>Address: {{ item.address }}</p>
              <p>Price: {{ item.price }}kr{{ item.priceType }}</p>
            </div>
            <div id="About2">
              <RatingComponent :rating="item.rating" />
            </div>
          </div>
          <h2>Description</h2>
          <p>{{ item.description }}</p>
        </div>
      </div>
    </div>
    <div>
      <ProfileBoxComponent :profile="profile" />
      <ReviewComponent :reviews="reviews" />
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RatingComponent";
import ProfileBoxComponent from "@/components/ProfileBoxComponent";
import ReviewComponent from "@/components/ReviewComponent";
import LeaseRequestComponent from "@/components/LeaseRequestComponent.vue";
import apiService from "@/services/apiService";
export default {
  props: ["id"],

  components: {
    ProfileBoxComponent,
    RatingComponent,
    ReviewComponent,
    LeaseRequestComponent,
  },
  data() {
    return {
      item: { id: null, profileId: null },
      profile: {},
      reviews: [],
    };
  },

  created() {
    apiService
      .getItem(this.id)
      .then((response) => {
        this.item = response.data;
        apiService
          .getProfile(this.item.profileId)
          .then((response) => {
            this.profile = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.log(error);
      });

    apiService
      .getReviews({ listingId: this.id }, 0, 15)
      .then((response) => {
        this.reviews = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.mainContainer {
  display: flex;
}

.container {
  margin-top: 20px;
  width: 600px;
}

.my-overlay {
  position: fixed;
  display: none;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 2;
  cursor: pointer;
}

img {
  height: 300px;
  width: 300px;
  padding: 10px;
  border: 1px solid #39495c;
}

.imageButtons {
  display: flex;
}

.leaseButton {
  border: 1px solid #39495c;
  width: 150px;
  height: 50px;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 5px;
  background: white;
  margin: 50px;
}

.editButtons {
  border: 1px solid #39495c;
  width: 150px;
  height: 50px;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 5px;
  background: white;
  margin: 25px;
}

button:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#About {
  margin-top: 30px;
  text-align: left;
}

#About11 {
  display: flex;
}

#About1 {
  width: 200px;
}

#About2 {
  width: 100px;
  margin-left: 50px;
}

#category label {
  display: inline;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>

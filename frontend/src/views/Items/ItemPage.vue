<template>
  <div class="mainContainer">
    <Transition name="overlay">
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
          <button
            class="leaseButton boco-btn"
            v-else
            @click="leaseOverlay = true"
          >
            Book
          </button>
        </div>
        <div id="About">
          <div id="About11">
            <div id="About1">
              <h2>{{ item.name }}</h2>
              <div id="category">
                <label>Category: </label>
                <label v-for="category in item.categoryTypes" :key="category"
                  >{{ category.name }},
                </label>
              </div>
              <p>Address: {{ item.address }}</p>
              <p>Price: {{ price }}kr / {{ item.priceType }}</p>
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
      <router-link
        class="link"
        :to="{ name: 'profile', params: { id: profile.id } }"
      >
        <ProfileBoxComponent :profile="profile" />
      </router-link>
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
      leaseOverlay: false,
      item: { id: null, profileId: null, price: 0, priceType: null },
      profile: {},
      reviews: [],
    };
  },
  computed: {
    price() {
      let actuallyPrice = this.item.price;
      if (this.item.priceType === "Week") {
        actuallyPrice = this.item.price * 7 * 24;
      }
      if (this.item.priceType === "Day") {
        actuallyPrice = this.item.price * 24;
      }
      return actuallyPrice;
    },
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
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  padding: 5px;
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

.overlay-enter-active,
.overlay-leave-active {
  transition: all 0.2s ease;
}

.overlay-enter-from,
.overlay-leave-to {
  opacity: 0;
  top: 20%;
}
</style>

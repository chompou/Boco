<template>
  <div class="mainContainer">
    <Transition name="overlay">
      <lease-request-component
        v-if="leaseOverlay"
        :item="item"
        @close-overlay="leaseOverlay = false"
      />
    </Transition>

    <div class="container">
      <div>
        <div class="imageButtons">
          <imageComponent></imageComponent>
          <div v-if="my">
            <button
              v-if="item.active"
              @click="changeStatus(false)"
              class="editButtons boco-btn"
            >
              Active
            </button>
            <button
              v-else
              @click="changeStatus(true)"
              class="editButtons boco-btn"
            >
              Inactive
            </button>
            <button @click="edit" class="editButtons boco-btn">Edit</button>
            <button class="editButtons boco-btn" @click="deleteItem">
              Delete
            </button>
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
              <p>Price: {{ displayPrice }}kr / {{ item.priceType }}</p>
            </div>
            <div id="About2">
              <div id="items">
                <p>Rating:</p>
                <RatingComponent :rating="item.rating" />
              </div>
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
import RatingComponent from "@/components/RateReview/RatingComponent";
import ProfileBoxComponent from "@/components/ProfileBoxComponent";
import ReviewComponent from "@/components/RateReview/ReviewComponent";
import LeaseRequestComponent from "@/components/LeaseRequestComponent.vue";
import apiService from "@/services/apiService";
import priceService from "@/services/priceService";
import imageComponent from "@/components/imageComponent";
import axios from "axios";
export default {
  props: ["id"],

  components: {
    ProfileBoxComponent,
    RatingComponent,
    ReviewComponent,
    LeaseRequestComponent,
    imageComponent,
  },
  data() {
    return {
      leaseOverlay: false,
      item: { id: null, profileId: null, price: 0, priceType: null },
      profile: { id: 0 },
      reviews: [],
      url: null,
      profileLoaded: false,
      imgSource: null,
    };
  },
  computed: {
    displayPrice() {
      return priceService.displayPrice(this.item);
    },

    my() {
      return this.item.profileId === this.$store.state.loggedInUser;
    },
  },
  methods: {
    edit() {
      this.$router.push({ name: "editItem", params: { id: this.id } });
    },
    deleteItem() {
      let result = confirm("Are you sure you want to delete?");
      if (result) {
        apiService
          .deleteItem({
            listingId: this.id,
          })
          .catch((error) => {
            console.log(error);
          });
        setTimeout(() => {
          this.$router.push({ name: "myItems" });
        }, 300);
      }
    },
    changeStatus(status) {
      console.log(this.item);
      apiService
        .updateItem({
          listingId: this.id,
          isAvailable: true,
          active: status,
          address: this.item.address,
          price: this.item.price,
          priceType: this.item.priceType,
          description: this.item.description,
        })
        .catch((error) => {
          console.log(error);
        });
      setTimeout(() => {
        location.reload();
      }, 100);
    },
    dataUrl() {
      return btoa(this.item.image);
    },
  },
  created() {
    apiService
      .getItem(this.id)
      .then((response) => {
        this.item = response.data;
        setTimeout(() => {
          let image = this.item.image;
          this.imgSource = "data:image/jpeg;base64, " + image;
        }, 100);
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
      .then((response) => (this.reviews = response.data))
      .catch((error) => console.log(error));

    axios
      .get("https://picsum.photos/200/300")
      .then((response) => (this.url = response.request.responseURL));
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
  text-align: center;
  padding: 5px;
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

#items {
  display: flex;
  flex-direction: column;
  font-size: 20px;
}

#category label {
  display: inline;
}

#profilebox {
  text-decoration: none;
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

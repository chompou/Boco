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
      <div class="imageButtons">
        <img id="image3" alt="Vue logo" :src="imgSource" />
        <button
          class="editButtons boco-btn book"
          v-if="!my"
          @click="isBookingAvailable"
        >
          Book
        </button>
        <div v-else>
          <button
            class="editButtons boco-btn"
            :class="[active ? 'red' : 'boco-btn']"
            @click="toggle"
            id="status-btn"
          >
            {{ active ? "Set inactive" : "Set active" }}
          </button>
          <button @click="edit" class="editButtons boco-btn">Edit</button>
          <button class="editButtons boco-btn" @click="deleteItem">
            Delete
          </button>
        </div>
      </div>
      <div id="About">
        <div id="About11">
          <div id="About1">
            <h1>{{ item.name }}</h1>
            <div id="category">
              <h5>Category:</h5>
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
        <div id="About3">
          <h2>Description</h2>
          <label id="description">{{ item.description }}</label>
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
import axios from "axios";
import priceService from "@/services/priceService";
import { useToast } from "vue-toastification";

export default {
  setup() {
    const toast = useToast();
    return { toast };
  },
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
      profile: { id: 0 },
      reviews: [],
      url: null,
      profileLoaded: false,
      imgSource: null,
      active: null,
    };
  },
  computed: {
    displayPrice() {
      return priceService.formattedPrice(priceService.displayPrice(this.item));
    },

    my() {
      if (!this.$store.state.loggedIn) return false;
      return this.item.profileId === this.$store.state.loggedInUser;
    },
  },
  methods: {
    toggle() {
      this.active = !this.active;
      apiService
        .updateItem({ ...this.item, isActive: this.active })
        .catch((error) => {
          console.log(error);
        });
      let toastStatus = "";
      if (this.active) {
        toastStatus = "Active";
      } else toastStatus = "Inactive";

      this.toast.info("Listing is now " + toastStatus, {
        timeout: 2000,
      });
    },
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
        this.toast.success("Item was successfully deleted", {
          timeout: 2000,
        });
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
      this.toast.info("Listing is now " + status, {
        timeout: 2000,
      });
    },
    dataUrl() {
      return btoa(this.item.image);
    },
    isBookingAvailable() {
      if (this.$store.state.loggedIn) {
        this.leaseOverlay = true;
      } else {
        return this.$router.push({ name: "login" });
      }
    },
  },
  created() {
    apiService
      .getItem(this.id)
      .then((response) => {
        this.item = response.data;
        this.active = this.item.isActive;
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

.red {
  background-color: red;
}
.red:hover {
  background-color: #ac0000;
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
  height: 281px;
  min-width: 500px;
  width: 500px;
  border-radius: 20px;
}

.imageButtons {
  display: flex;
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
  text-align: center;
  flex-direction: column;
  font-size: 20px;
}

.book {
  min-width: 150px;
}

#category label {
  display: inline;
}

#description {
  max-width: 600px;
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

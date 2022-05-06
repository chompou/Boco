<template>
  <div class="profileBox">
    <div class="profile">
      <div class="profileBoxText">
        <router-link
          id="profilebox"
          class="link"
          :to="{ name: 'profile', params: { id: profile.id } }"
        >
          <h3>{{ profile.displayName }}</h3>
        </router-link>
        <p>Phone nr: {{ profile.tlf }}</p>
        <p>Email: {{ profile.email }}</p>
      </div>
      <div id="rating">
        <div id="items">
          <p id="ratingText">Rating:</p>
          <RatingComponent :rating="profile.ratingAsLease" />
        </div>
      </div>
    </div>
    <GMapMap :center="position" :zoom="9" style="width: 350px; height: 200px">
      <GMapMarker v-if="marker" :position="position" :clickable="true" />
      <GMapMarker
        v-if="marker"
        icon="http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
        :position="myPosition"
        :clickable="true"
      />
    </GMapMap>
  </div>
</template>

<script>
import RatingComponent from "@/components/RateReview/RatingComponent";
import axios from "axios";
import apiService from "@/services/apiService";
export default {
  props: ["profile"],
  data() {
    return {
      myProfile: null,
      marker: false,
      myPosition: {
        lat: 40,
        lng: 40,
      },
      position: {
        lat: 40,
        lng: 40,
      },
    };
  },
  components: { RatingComponent },
  methods: {
    async getPosition(position) {
      const { data } = await axios.get(
        "https://maps.googleapis.com/maps/api/geocode/json?address=" +
          position +
          "&key=AIzaSyDqtG0SjobFXqse13BVXAHPZPMQ87utTd4"
      );
      this.position.lat = data.results[0].geometry.location.lat;
      this.position.lng = data.results[0].geometry.location.lng;
      this.marker = true;
    },
    getMyPosition() {
      console.log(this.myProfile.location);
      let latLngList = this.myProfile.location.split(":");
      let latString = latLngList[0];
      let lngString = latLngList[1];
      let lat = parseFloat(latString);
      let lng = parseFloat(lngString);
      this.myPosition.lat = lat;
      this.myPosition.lng = lng;
    },
  },
  created() {
    apiService.getMyProfile().then((response) => {
      this.myProfile = response.data;
      console.log(this.myProfile);
    });
    setTimeout(() => {
      this.getPosition(this.profile.address);
      this.getMyPosition(this.myPosition);
    }, 1000);
  },
};
</script>

<style scoped>
.profileBox {
  font-size: 17px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 400px;
  min-height: 400px;
  color: #2c3e50;
  padding: 10px 28px;
  background-color: rgba(0, 139, 139, 0.4);
  margin: 20px;
}

.profile {
  display: flex;
}

.profileBoxText {
  text-align: left;
  min-width: 160px;
  min-height: 150px;
}

#rating {
  margin-left: 20px;
}

#items {
  display: flex;
  flex-direction: column;
  font-size: 20px;
}

#ratingText {
  text-align: center;
  margin-right: 30px;
}

#map {
  width: 350px;
  height: 200px;
}
</style>

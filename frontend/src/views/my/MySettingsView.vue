<template>
  <div class="settings-container">
    <h1>Settings</h1>
    <div class="input-container">
      <div id="information" class="input-grid">
        <label> Display Name </label>
        <input :value="profile.displayName" />

        <label> E-Mail </label>
        <input type="email" :value="profile.email" />

        <label> Address </label>
        <input v-model="profile.address" />

        <label> Telephone </label>
        <input :value="profile.tlf" />

        <label> Description </label>
        <input style="height: 100px" :value="profile.description" />

        <label> New Password </label>
        <input :value="newPass" />

        <label> Confirm New Password </label>
        <input :value="confirmPass" />
      </div>

      <div>
        <h3>Your location</h3>
        <div>
          <GMapMap
            :center="{ lat: position.lat, lng: position.lng }"
            :zoom="9"
            map-type-id="terrain"
            style="width: 600px; height: 400px"
          >
            <GMapMarker :position="position" :clickable="true" />
          </GMapMap>
          <button @click="getPoint">Check your address</button>
        </div>
      </div>
    </div>
    <div id="buttons" class="button-container">
      <button class="boco-btn" @click="onSave">Save</button>
      <button class="boco-btn" @click="onReset">Reset</button>
    </div>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
import axios from "axios";
export default {
  data() {
    return {
      profile: {
        displayName: null,
        email: null,
        address: null,
        tlf: null,
        description: null,
        password: null,
      },
      newPass: null,
      confirmPass: null,
      position: {
        lat: 63.3,
        lng: 10,
      },
    };
  },
  methods: {
    async getPoint() {
      console.log(this.profile.address);
      const { data } = await axios.get(
        "https://maps.googleapis.com/maps/api/geocode/json?address=" +
          this.profile.address +
          "&key=AIzaSyDqtG0SjobFXqse13BVXAHPZPMQ87utTd4"
      );
      this.position.lat = data.results[0].geometry.location.lat;
      this.position.lng = data.results[0].geometry.location.lng;
    },
    fetchProfile() {
      apiService
        .getProfile(this.$store.state.loggedInUser)
        .then((response) => (this.profile = response.data))
        .catch((error) => console.log(error));
    },

    onSave() {
      if (this.newPass != this.confirmPass) {
        return alert("Passwords do not match");
      }

      this.profile.password = this.newPass;
      apiService
        .updateMyProfile(this.profile)
        .catch((error) => console.log(error));
    },
  },
  created() {
    this.fetchProfile();
  },
};
</script>

<style scoped>
.settings-container {
  display: flex;
  flex-direction: column;
  gap: 50px;
}

.input-container {
  display: grid;
  grid-template-columns: 40% 40%;
  justify-content: space-around;
  grid-template-areas:
    "info pass"
    "info button";
}

#information {
  grid-area: info;
}

#buttons {
  grid-area: button;
  justify-self: center;
  align-self: center;
}

.input-grid {
  display: grid;
  grid-template-columns: auto auto;
  column-gap: 15px;
  row-gap: 10px;
}

.button-container {
  display: flex;
  gap: 40px;
}
</style>

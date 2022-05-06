<template>
  <div class="settings-container">
    <h1>Settings</h1>
    <div class="input-container">
      <div id="information" class="input-grid">
        <label> Display Name </label>
        <input v-model="profile.displayName" />

        <label> E-Mail </label>
        <input type="email" v-model="profile.email" />

        <label> Address </label>
        <input v-model="profile.address" />

        <label> Telephone </label>
        <input v-model="profile.tlf" />

        <label> Description </label>
        <input style="height: 100px" v-model="profile.description" />

        <label> New Password </label>
        <input type="password" v-model="newPass" />

        <label> Confirm New Password </label>
        <input type="password" v-model="confirmPass" />
      </div>

      <div>
        <h3>Your address</h3>
        <div>
          <GMapMap
            :center="{ lat: position.lat, lng: position.lng }"
            :zoom="9"
            style="width: 600px; height: 400px"
          >
            <GMapMarker :position="position" :clickable="true" />
          </GMapMap>
          <button v-if="profile.address !== null" @click="getPoint">
            Check your address
          </button>
        </div>
      </div>
    </div>
    <div id="buttons" class="button-container">
      <button class="boco-btn" @click="onSave">Save</button>
      <button id="reset-btn" class="boco-btn" @click="onReset">Reset</button>
      <button id="deactivate-btn" class="boco-btn" @click="onDeactivate">
        Deactivate account
      </button>
    </div>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
import axios from "axios";
import { useToast } from "vue-toastification";

export default {
  setup() {
    const toast = useToast();
    return { toast };
  },
  data() {
    return {
      profile: {
        displayName: null,
        email: null,
        address: null,
        tlf: null,
        description: null,
        password: null,
        latLng: null,
      },
      newPass: null,
      confirmPass: null,
      position: {
        lat: 63.4305149,
        lng: 10.3950528,
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
      console.log(data.results[0].geometry.location.lat);
      console.log(data.results[0].geometry.location.lng);
      this.position.lat = data.results[0].geometry.location.lat;
      this.position.lng = data.results[0].geometry.location.lng;
      this.profile.latLng =
        data.results[0].geometry.location.lat +
        ":" +
        data.results[0].geometry.location.lng;
      console.log(this.profile.latLng);
    },
    fetchProfile() {
      apiService
        .getProfile(this.$store.state.loggedInUser)
        .then((response) => (this.profile = response.data))
        .catch((error) => console.log(error));
    },
    updateProfile() {
      apiService.updateMyProfile(this.profile);
    },
    onSave() {
      if (this.newPass !== this.confirmPass) {
        return this.toast.error(
          "Passwords do not match! Changes were not saved",
          {
            timeout: 3000,
          }
        );
      }
      if (this.newPass === null) {
        apiService
          .updateMyProfile(this.profile)
          .catch((error) => console.log(error));
        this.toast.success("Changes were saved!", {
          timeout: 2000,
        });
      } else {
        this.profile.password = this.newPass;
        apiService
          .updateMyProfile(this.profile)
          .catch((error) => console.log(error));
        this.toast.success("Changes were saved!", {
          timeout: 2000,
        });
      }
    },
    onReset() {
      apiService
        .getMyProfile()
        .then((response) => (this.profile = response.data))
        .catch((error) => console.log(error));
      this.toast.error("Changes were discarded!", {
        timeout: 2000,
      });
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

#reset-btn {
  background-color: red;
}

#reset-btn:hover {
  background-color: #ac0000;
}

#deactivate-btn {
  background-color: black;
}
#deactivate-btn:hover {
  background-color: #232323;
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

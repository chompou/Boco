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
        <textarea style="height: 100px" v-model="profile.description" />

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
import storageService from "@/services/storageService";

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
        passwordHash: null,
        location: null,
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
    onDeactivate() {
      if (
        confirm(
          "Are you sure you want to deactivate your account? You will lose all access to this account. Data will be deleted in 90 days"
        )
      ) {
        apiService.deleteAccount().then(() => {
          storageService.clearToken();
          this.$store.state.loggedIn = false;
          this.$store.state.loggedInUser = null;
          this.$store.dispatch("RESET_COUNT_NOTIFICATION");
          this.$store.dispatch("CLEAR_NOTIFICATIONS");
          this.show = false;
          this.$router.push({ path: "/" });
          this.toast.info("Your account has been deactivated", {
            timeout: 2000,
          });
        });
      }
    },
    async getPoint() {
      console.log(this.profile.address);
      const { data } = await axios.get(
        "https://maps.googleapis.com/maps/api/geocode/json?address=" +
          this.profile.address +
          "&key=AIzaSyDqtG0SjobFXqse13BVXAHPZPMQ87utTd4"
      );
      this.position.lat = data.results[0].geometry.location.lat;
      this.position.lng = data.results[0].geometry.location.lng;
      this.profile.location =
        data.results[0].geometry.location.lat +
        ":" +
        data.results[0].geometry.location.lng;
      console.log(this.profile.location);
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
        this.profile.passwordHash = this.newPass;
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
    apiService
      .getMyProfile()
      .then((response) => (this.profile = response.data))
      .catch((error) => console.log(error));
    setTimeout(() => {
      this.getPoint();
    }, 1000);
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
  animation: shake 0.5s;
  animation-iteration-count: infinite;

  background-color: #232323;
}

@keyframes shake {
  0% {
    transform: translate(1px, 1px) rotate(0deg);
  }
  10% {
    transform: translate(-1px, -2px) rotate(-1deg);
  }
  20% {
    transform: translate(-3px, 0px) rotate(1deg);
  }
  30% {
    transform: translate(3px, 2px) rotate(0deg);
  }
  40% {
    transform: translate(1px, -1px) rotate(1deg);
  }
  50% {
    transform: translate(-1px, 2px) rotate(-1deg);
  }
  60% {
    transform: translate(-3px, 1px) rotate(0deg);
  }
  70% {
    transform: translate(3px, 1px) rotate(-1deg);
  }
  80% {
    transform: translate(-1px, -1px) rotate(1deg);
  }
  90% {
    transform: translate(1px, 2px) rotate(0deg);
  }
  100% {
    transform: translate(1px, -2px) rotate(-1deg);
  }
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

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
        <input :value="profile.address" />

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
          <ol-map
            :loadTilesWhileAnimating="true"
            :loadTilesWhileInteracting="true"
            style="height: 400px"
          >
            <ol-view
              ref="view"
              :center="center"
              :rotation="rotation"
              :zoom="zoom"
              :projection="projection"
            />

            <ol-tile-layer>
              <ol-source-osm />
            </ol-tile-layer>

            <ol-vector-layer>
              <ol-source-vector :projection="projection">
                <ol-interaction-draw
                  v-if="draw"
                  type="Point"
                  @drawstart="drawstart"
                >
                </ol-interaction-draw>
              </ol-source-vector>

              <ol-style>
                <ol-style-stroke color="red" :width="2"></ol-style-stroke>
                <ol-style-fill color="rgba(255,255,255,0.1)"></ol-style-fill>
                <ol-style-circle :radius="7">
                  <ol-style-fill color="blue"></ol-style-fill>
                </ol-style-circle>
              </ol-style>
            </ol-vector-layer>
          </ol-map>
          <p>{{ profile.coords }}</p>
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
import { ref } from "vue";
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
        coords: [0, 0],
      },
      newPass: null,
      confirmPass: null,
      draw: true,
    };
  },
  setup() {
    const center = ref([40, 40]);
    const projection = ref("EPSG:4326");
    const zoom = ref(8);
    const rotation = ref(0);
    const drawType = ref("Point");

    return {
      center,
      projection,
      zoom,
      rotation,
      drawType,
    };
  },
  methods: {
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

    onReset() {
      this.fetchProfile();
      this.oldPass = this.newPass = this.confirmPass = null;
    },
    drawstart(event) {
      this.draw = false;
      console.log(event.target.sketchCoords_);
      this.profile.coords = event.target.sketchCoords_;
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

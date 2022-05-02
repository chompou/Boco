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
      </div>

      <div id="password" class="input-grid" style="max-height: 0px">
        <label> Old Password </label>
        <input :value="oldPass" />

        <label> New Password </label>
        <input :value="newPass" />

        <label> Confirm New Password </label>
        <input :value="confirmPass" />
      </div>
      <div id="buttons" class="button-container">
        <button class="boco-btn" @click="onSave">Save</button>
        <button class="boco-btn" @click="onReset">Reset</button>
      </div>
    </div>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
export default {
  data() {
    return {
      profile: {
        displayName: null,
        email: null,
        address: null,
        tlf: null,
        description: null,
      },
      oldPass: null,
      newPass: null,
      confirmPass: null,
    };
  },

  methods: {
    fetchProfile() {
      apiService
        .getProfile(this.$store.state.loggedInUser)
        .then((response) => (this.profile = response.data))
        .catch((error) => console.log(error));
    },

    onSave() {},

    onReset() {
      this.fetchProfile();
      this.oldPass = this.newPass = this.confirmPass = null;
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

#password {
  grid-area: pass;
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

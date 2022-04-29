<template>
  <div class="profileBar_container">
    <profile-bar-component v-if="dataReady" :profile="profile" />
  </div>
  <router-view v-if="this.id == null"></router-view>
</template>
<script>
import ProfileBarComponent from "@/components/ProfileBarComponent.vue";
import apiService from "@/services/apiService";
export default {
  props: ["id"],
  components: { ProfileBarComponent },

  data() {
    return {
      profile: null,
      dataReady: false,
    };
  },

  created() {
    if (this.id == null) {
      apiService.getMyProfile().then((response) => {
        this.profile = response.data;
        this.dataReady = true;
      });
    } else {
      apiService.getProfile(this.id).then((response) => {
        this.profile = response.data;
        this.dataReady = true;
      });
    }
  },
};
</script>

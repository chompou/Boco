<template>
  <div class="item-list-container">
    <large-item class="item" v-for="i in 5" :key="i" />
  </div>
</template>

<script>
import LargeItem from "@/components/Items/LargeItem.vue";
import apiService from "@/services/apiService";

export default {
  components: { LargeItem },

  data() {
    return {
      items: [],
    };
  },

  created() {
    apiService
      .getItems({ profileId: this.$store.state.loggedInuser }, 0, 15)
      .then((response) => {
        this.items = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style scoped>
.item-list-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0px;
}
</style>

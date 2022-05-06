<template>
  <div class="item-list-container">
    <large-item
      class="item"
      v-for="item in items.slice().reverse()"
      :key="item"
      :item="item"
    />
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
      .getItems({ profileId: this.$store.state.loggedInUser }, 0, 15)
      .then((response) => {
        this.items = response.data.listingResponses;
        console.log(this.items);
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

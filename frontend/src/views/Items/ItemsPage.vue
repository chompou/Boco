<template>
  <div class="container">
    <Sidebar />
    <transition>
      <div id="items" :style="{ 'margin-left': sidebarWidth }">
        <LargeItem v-for="item in items" :key="item" :item="item" />
      </div>
    </transition>
  </div>
</template>

<script>
import Sidebar from "@/components/Sidebar/SidebarComponent";
import { sidebarWidth } from "@/components/Sidebar/state";
import LargeItem from "@/components/Items/LargeItem.vue";
import apiService from "@/services/apiService";
export default {
  components: { LargeItem, Sidebar },
  data() {
    return {
      items: [],
    };
  },
  setup() {
    return { sidebarWidth };
  },

  created() {
    apiService
      .getItems({}, 0, 15)
      .then((response) => {
        this.items = response.data;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style scoped>
#items {
  transition: 0.3s ease;
}
</style>

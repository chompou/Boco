<template>
  <div class="container">
    <Sidebar />
    <transition>
      <div id="items" :style="{ 'margin-left': sidebarWidth }">
        <LargeItem v-for="item in items" :key="item" />
      </div>
    </transition>
  </div>
</template>

<script>
import Sidebar from "@/components/Sidebar/SidebarComponent";
import { sidebarWidth } from "@/components/Sidebar/state";
import LargeItem from "@/components/Items/LargeItem";
export default {
  components: { LargeItem, Sidebar },
  data() {
    return {
      items: [
        {
          id: 1,
        },
        {
          id: 1,
        },
        {
          id: 1,
        },
        {
          id: 1,
        },
        {
          id: 1,
        },
      ],
    };
  },
  setup() {
    return { sidebarWidth };
  },
};
</script>

<style scoped>
#items {
  transition: 0.3s ease;
}
</style>

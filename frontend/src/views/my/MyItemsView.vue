<template>
  <div class="item-list-container">
    <large-item class="item" v-for="i in 5" :key="i" />
  </div>
</template>

<script>
import LargeItem from "@/components/Items/LargeItem.vue";

export default {
  components: { LargeItem },

  data() {
    return {
      items: [],
    };
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

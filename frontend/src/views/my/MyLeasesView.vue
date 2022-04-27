<template>
  <div class="lease-list-container">
    <div>
      <h2>Owned</h2>
      <lease-list-component class="lease-list-component" :personal="true" />
    </div>
    <div>
      <h2>Leases</h2>
      <lease-list-component class="lease-list-component" :personal="false" />
    </div>
  </div>
</template>

<script>
import LeaseListComponent from "@/components/LeaseListComponent.vue";
export default {
  components: { LeaseListComponent },

  data() {
    return {
      owned: [],
      leased: [],
    };
  },

  created() {},
};
</script>

<style scoped>
.lease-list-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
</style>

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
      owned: [
        {
          id: 1,
          title: "MyItem",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
        {
          id: 2,
          title: "MyItem",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
      ],
      leased: [
        {
          id: 3,
          title: "MyItem",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
        {
          id: 4,
          title: "MyItem",
          owner: "Jon Martin",
          from: "2022-05-15",
          to: "2022-05-20",
        },
      ],
    };
  },
};
</script>

<style scoped>
.lease-list-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
</style>

<template>
  <div class="lease-container">
    <div style="flex-direction: row">
      <h4>{{ lease.itemName }}</h4>
      <h6>{{ fromDate }}</h6>
      <h6>{{ toDate }}</h6>
    </div>
    <div>
      <h5>{{ status }}</h5>
    </div>
  </div>
</template>

<script>
import leaseService from "@/services/leaseService";
export default {
  props: ["lease"],

  computed: {
    fromDate() {
      return leaseService.displayDate(leaseService.fromDate(this.lease));
    },

    toDate() {
      return leaseService.displayDate(leaseService.toDate(this.lease));
    },

    status() {
      return leaseService.getStatus(this.lease);
    },
  },
};
</script>

<style scoped>
.lease-container {
  display: flex;
  border: 3px solid;
  border-radius: 10px;
  padding: 15px;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  text-align: left;
  gap: 50px;
  cursor: pointer;
}

.lease-container:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}
</style>

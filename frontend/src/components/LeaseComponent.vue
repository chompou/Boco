<template>
  <div class="lease-container">
    <div style="flex-direction: row">
      <h4>{{ lease.title }}</h4>
      <h6>Leased by {{ lease.owner }}</h6>
      <h6>{{ lease.from }} - {{ lease.to }}</h6>
    </div>
    <div>
      <h5>Lease Ended</h5>
    </div>
  </div>
</template>

<script>
export default {
  props: ["lease"],
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

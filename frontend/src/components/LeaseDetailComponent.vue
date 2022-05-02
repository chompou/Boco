<template>
  <div class="lease-detail-modal">
    <div class="lease-detail-container">
      <h2>Item: {{ lease.title }}</h2>
      <h3>Owner: {{ lease.owner }}</h3>
      <h3>Leaser: {{ lease.leaser }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  props: ["lease"],
};
</script>

<style scoped>
.lease-detail-modal {
  position: fixed;
  z-index: 9999;
  top: 20%;
  left: 50%;
  transform: translateX(-50%);

  height: 60%;
  width: 50%;

  border: solid 1px;
  border-radius: 5px;
  background-color: white;
}

.lease-detail-container {
  display: flex;
  justify-content: center;
}
</style>

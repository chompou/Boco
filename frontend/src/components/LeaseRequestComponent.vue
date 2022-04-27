<template>
  <div class="request-overlay request-modal">
    <p class="cross" @click="$emit('close-overlay')">X</p>
    <h1>Book</h1>
    <form class="lease-form">
      <input type="datetime-local" />
      <input type="datetime-local" />
      <button class="boco-btn" @click="$emit('close-overlay')">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style scoped>
.lease-form {
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.cross {
  border-radius: 1000%;
  position: fixed;
  top: 2%;
  right: 2%;
  font-size: 25px;
  cursor: default;
}

.request-modal {
  position: fixed;
  z-index: 9999;
  border: solid 2px;
  border-radius: 10px;
  top: 15%;
  left: 50%;
  transform: translateX(-50%);
  padding: 35px;
  background-color: white;
  box-shadow: 50px 50px 50px 1000vmax rgba(0, 0, 0, 0.5);
}
</style>

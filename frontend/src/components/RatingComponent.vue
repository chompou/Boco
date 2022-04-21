<template>
  <div id="items">
    <p>Rating:</p>
    <h2>{{ rating }}/5</h2>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rating: "4.2",
    };
  },
};
</script>

<style scoped>
#items {
  display: flex;
  flex-direction: column;
  align-items: center;
}

p {
  font-size: 20px;
}

h2 {
  border: 1px solid #39495c;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 20px 10px;
}
</style>

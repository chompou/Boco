<template>
  <router-link class="link" :to="{ name: 'item', params: { id: item.id } }">
    <div>
      <img alt="Vue logo" src="@/assets/service.png" />
      <h3 v-if="item.name.length < 8">{{ item.name }}</h3>
      <h3 v-else>{{ item.name.substring(0, 16) + ".." }}</h3>
    </div>
  </router-link>
</template>

<script>
export default {
  props: ["item"],

  data() {
    return {};
  },
};
</script>

<style scoped>
.link {
  color: #2c3e50;
  text-decoration: none;
}

img {
  width: 200px;
  height: 200px;
  margin: 8px;
  border: 1px solid #39495c;
}

div {
  border: 1px solid #39495c;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 250px;
  height: 250px;
  text-align: center;
  color: var(--text-color);
  background: white;
  margin: 20px;
}

div:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}
</style>

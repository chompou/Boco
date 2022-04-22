<template>
  <router-link class="link" :to="{ path: '/items/' + id, params: { id: id } }">
    <div>
      <img alt="Vue logo" src="@/assets/service.png" />
      <h3 v-if="title.length < 8">{{ title }}</h3>
      <h3 v-else>{{ title.substring(0, 16) + ".." }}</h3>
    </div>
  </router-link>
</template>

<script>
export default {
  data() {
    return {
      id: "1234",
      title: "jsalkjfkldsajflakdsjfløajsfløa",
    };
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
  color: #2c3e50;
  padding: 10px 28px;
  background: white;
  margin: 20px;
}

div:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}
</style>

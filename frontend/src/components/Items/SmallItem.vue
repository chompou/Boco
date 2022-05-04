<template>
  <router-link class="link" :to="{ name: 'item', params: { id: item.id } }">
    <div>
      <img
        v-if="imgSource === null"
        alt="Vue logo"
        src="@/assets/default.png"
      />
      <img v-else id="image" alt="Vue logo" :src="imgSource" />
      <h3 v-if="item.name.length < 8">{{ item.name }}</h3>
      <h3 v-else>{{ item.name.substring(0, 16) + ".." }}</h3>
      <p>{{ item.price }}kr/{{ item.priceType }}</p>
    </div>
  </router-link>
</template>

<script>
export default {
  props: ["item"],

  data() {
    return {
      imgSource: null,
    };
  },
  created() {
    let image = this.item.image;
    setTimeout(() => {
      this.imgSource = "data:image/jpeg;base64, " + image;
    }, 100);
  },
};
</script>

<style scoped>
.link {
  color: #2c3e50;
  text-decoration: none;
}

h3 {
  font-size: 23px;
}

img {
  width: 240px;
  height: 143px;
  margin: 8px;
}

div {
  border: 1px solid #39495c;
  border-radius: 10px;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 270px;
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

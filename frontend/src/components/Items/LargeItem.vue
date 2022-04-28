<template>
  <router-link class="link" :to="{ name: 'item', params: { id: item.id } }">
    <div id="main">
      <img :src="url" />
      <div id="texts">
        <div id="About11">
          <div id="About1">
            <h3 v-if="item.name.length < 8">{{ item.name }}</h3>
            <h3 v-else>{{ item.name.substring(0, 16) + ".." }}</h3>
            <p>Category: {{ categoryString }}</p>
            <p>Address: {{ item.address }}</p>
            <p>Price: {{ price }}kr/{{ item.priceType }}</p>
          </div>
          <div id="About2">
            <RatingComponent :rating="item.rating" />
          </div>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script>
import RatingComponent from "@/components/RatingComponent";
import axios from "axios";

export default {
  props: ["item"],
  components: { RatingComponent },
  computed: {
    price() {
      let actuallyPrice = this.item.price;
      if (this.item.priceType === "Week") {
        actuallyPrice = this.item.price * 7 * 24;
      }
      if (this.item.priceType === "Day") {
        actuallyPrice = this.item.price * 24;
      }
      return actuallyPrice;
    },

    categoryString() {
      return this.item.categoryTypes.map((c) => c.name).join(", ");
    },
  },

  data() {
    return {
      url: null,
    };
  },

  created() {
    axios.get("https://picsum.photos/200/300").then((response) => {
      this.url = response.request.responseURL;
    });
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

#main {
  display: flex;
  border: 1px solid #39495c;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 650px;
  height: 225px;
  color: var(--text-color);
  padding: 10px 28px;
  background: white;
  margin: 20px;
}

#main:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#About11 {
  display: flex;
}

#About1 {
  margin-left: 30px;
  text-align: left;
  width: 200px;
}

#About2 {
  margin-top: 30px;
  margin-left: 50px;
  width: 150px;
}

#texts {
  width: 500px;
}

button {
  border: 1px solid #39495c;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: var(--text-color);
  padding: 5px;
  background: white;
  margin: 20px;
}
</style>

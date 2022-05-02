<template>
  <router-link class="link" :to="{ name: 'item', params: { id: item.id } }">
    <div id="main">
      <img id="image3" alt="Vue logo" :src="imgSource" />
      <div id="texts">
        <div id="About11">
          <div id="About1">
            <h3 v-if="item.name.length < 8">{{ item.name }}</h3>
            <h3 v-else>{{ item.name.substring(0, 16) + ".." }}</h3>
            <p>Category: {{ categoryString }}</p>
            <p>Address: {{ item.address }}</p>
            <p>Price: {{ displayPrice }}kr/{{ item.priceType }}</p>
          </div>
          <div id="About2">
            <div id="items">
              <p id="ratingText">Rating:</p>
              <RatingComponent :rating="item.rating" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script>
import RatingComponent from "@/components/RateReview/RatingComponent";
import priceService from "@/services/priceService";

export default {
  props: ["item"],
  components: { RatingComponent },
  data() {
    return {
      imgSource: null,
    };
  },
  computed: {
    displayPrice() {
      return priceService.displayPrice(this.item);
    },

    categoryString() {
      return this.item.categoryTypes.map((c) => c.name).join(", ");
    },
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

#items {
  display: flex;
  flex-direction: column;
  font-size: 20px;
}

#ratingText {
  margin-right: 30px;
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

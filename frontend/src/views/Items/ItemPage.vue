<template>
  <div class="container">
    <Transition>
      <lease-request-component
        v-if="leaseOverlay"
        @close-overlay="leaseOverlay = false"
      />
    </Transition>

    <div>
      <div>
        <img alt="Vue logo" src="@/assets/service.png" />
        <button @click="leaseOverlay = true">Book leasing</button>
      </div>
      <div id="About">
        <div id="About11">
          <div id="About1">
            <h2>{{ title }}</h2>
            <p>Category: {{ category }}</p>
            <p>Address: {{ address }}</p>
            <p>Price: {{ price }}kr{{ leaseType }}</p>
          </div>
          <div id="About2">
            <RatingComponent />
          </div>
        </div>
        <h2>Description</h2>
        <p>{{ description }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RatingComponent";
import LeaseRequestComponent from "@/components/LeaseRequestComponent.vue";
export default {
  components: { RatingComponent, LeaseRequestComponent },
  data() {
    return {
      leaseOverlay: false,
      title: "Wrench",
      category: "Tools",
      address: "Gløsveien 1",
      price: 50,
      leaseType: "/Day",
      description:
        "In North American English, wrench is the standard term. The most\n" +
        "          common shapes are called open-end wrench and box-end wrench. In\n" +
        "          American English, spanner refers to a specialized wrench with a series\n" +
        "          of pins or tabs around the circumference. (These pins or tabs fit into\n" +
        "          the holes or notches cut into the object to be turned.) In American\n" +
        "          commerce, such a wrench may be called a spanner wrench to distinguish\n" +
        "          it from the British sense of spanner.",
    };
  },
};
</script>

<style scoped>
.container {
  width: 600px;
}

.my-overlay {
  position: fixed;
  display: none;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 2;
  cursor: pointer;
}

img {
  height: 300px;
  width: 300px;
  padding: 10px;
  border: 1px solid #39495c;
}

button {
  border: 1px solid #39495c;
  width: 150px;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 5px;
  background: white;
  margin: 50px;
}

button:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

#About {
  margin-top: 30px;
  text-align: left;
}

#About11 {
  display: flex;
}

#About1 {
  width: 200px;
}

#About2 {
  width: 100px;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.2s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>

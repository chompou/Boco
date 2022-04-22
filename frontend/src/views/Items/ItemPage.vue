<template>
  <div class="mainContainer">

     <Transition>
      <lease-request-component
        v-if="leaseOverlay"
        @close-overlay="leaseOverlay = false"
      />
    </Transition>

    <div class="container">
      <div>
        <div class="imageButtons">
          <img alt="Vue logo" src="@/assets/service.png" />
          <div v-if="my">
            <button class="editButtons">Set Active</button>
            <button class="editButtons">Edit</button>
            <button class="editButtons">Delete</button>
          </div>
          <button class="leaseButton" v-else>Book leasing</button>
        </div>
        <div id="About">
          <div id="About11">
            <div id="About1">
              <h2>{{ title }}</h2>
              <div id="category">
                <label>Category: </label>
                <label v-for="item in category" :key="item">{{ item }}, </label>
              </div>
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
    <div>
      <ProfileBoxComponent />
      <ReviewComponent />
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RatingComponent";
import ProfileBoxComponent from "@/components/ProfileBoxComponent";
import ReviewComponent from "@/components/ReviewComponent";
export default {
  components: { ProfileBoxComponent, RatingComponent, ReviewComponent },
  data() {
    return {
      leaseOverlay: false,
      my: true,
      id: 1234,
      title: "Wrench",
      category: ["tool", "electronic"],
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
.mainContainer {
  display: flex;
}

.container {
  margin-top: 20px;
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

.imageButtons {
  display: flex;
}

.leaseButton {
  border: 1px solid #39495c;
  width: 150px;
  height: 50px;
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

.editButtons {
  border: 1px solid #39495c;
  width: 150px;
  height: 50px;
  font-size: 20px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 5px;
  background: white;
  margin: 25px;
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

#category label {
  display: inline;

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

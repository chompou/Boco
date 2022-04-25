<template>
  <div class="profileBar">
    <div class="profileBarText">
      <h3 id="username">{{ username }}</h3>
      <p id="phoneNumber">Phone nr: {{ phoneNumber }}</p>
      <p id="email">Email: {{ email }}</p>
    </div>
    <div id="rating">
      <RatingComponent />
    </div>
    <div class="container">
      <div class="buttons">
        <button id="profile" type="submit">Profile</button>
        <button id="myItems" type="submit">My items</button>
        <button id="leasing" type="submit">Leasing</button>
        <button id="rating" type="submit">Rating/Reviews</button>
      </div>
    </div>
  </div>
</template>
<script>
import RatingComponent from "@/components/RatingComponent";
export default {
  components: { RatingComponent },
  data() {
    return {
      username: "Øyvind Bjøntegaard",
      phoneNumber: "48420178",
      email: "ØyvindBjørn@gmail.com",
    };
  },
};
</script>

<style scoped>
.profileBar {
  border-radius: 20px;
  display: flex;
  border: 1px solid #39495c;
  font-size: 17px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
  height: 300px;
  color: #2c3e50;
  padding: 10px 28px;
  background: #e0f2fe;
  margin: 20px;
}

.profileBarText {
  text-align: left;
}

#rating {
  margin-left: auto;
  margin-right: 20px;
}

#email {
  margin-top: -10px;
}

#phoneNumber {
  margin-bottom: -10px;
}

.buttons {
  align-self: auto;
  height: 100%;
}

.container {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-around;
  background-color: black;
  height: 35px;
}
</style>

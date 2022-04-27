<template>
  <div class="profileBox">
    <div class="profileBoxText">
      <h3>{{ profile.displayName }}</h3>
      <p>Phone nr: {{ profile.tlf }}</p>
      <p>Email: {{ profile.email }}</p>
      <h3>MAP</h3>
    </div>
    <div id="rating">
      <RatingComponent :rating="profile.rating" />
    </div>
  </div>
</template>

<script>
import RatingComponent from "@/components/RatingComponent";
export default {
  props: ["profile"],
  components: { RatingComponent },
  data() {
    return {
      username: "Øyvind Bjøntegaard",
      phoneNumber: "48420178",
      email: "ØyvindBjørn@gmail.com",
    };
  },
  created() {},
};
</script>

<style scoped>
.profileBox {
  display: flex;
  border: 1px solid #39495c;
  font-size: 17px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 400px;
  height: 400px;
  color: #2c3e50;
  padding: 10px 28px;
  background: var(--button-color);
  margin: 20px;
}

.profileBoxText {
  text-align: left;
}

#rating {
  margin-left: 20px;
}
</style>

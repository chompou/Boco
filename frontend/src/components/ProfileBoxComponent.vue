<template>
  <div class="profileBox">
    <div class="profile">
      <div class="profileBoxText">
        <h3>{{ profile.displayName }}</h3>
        <p>Phone nr: {{ profile.tlf }}</p>
        <p>Email: {{ profile.email }}</p>
      </div>
      <div id="rating">
        <RatingComponent :rating="profile.ratingProfile" />
      </div>
    </div>
    <img id="map" alt="Map" src="@/assets/map.png" />
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
};
</script>

<style scoped>
.profileBox {
  border: 1px solid #39495c;
  font-size: 17px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 400px;
  height: 400px;
  color: #2c3e50;
  padding: 10px 28px;
  background-color: rgba(0, 139, 139, 0.4);
  margin: 20px;
}

.profile {
  display: flex;
}

.profileBoxText {
  text-align: left;
}

#rating {
  margin-left: 20px;
}

#map {
  width: 350px;
  height: 200px;
}
</style>

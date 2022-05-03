<template>
  <div class="profileBox">
    <div class="profile">
      <div class="profileBoxText">
        <router-link
          id="profilebox"
          class="link"
          :to="{ name: 'profile', params: { id: profile.id } }"
        >
          <h3>{{ profile.displayName }}</h3>
        </router-link>
        <p>Phone nr: {{ profile.tlf }}</p>
        <p>Email: {{ profile.email }}</p>
      </div>
      <div id="rating">
        <div id="items">
          <p id="ratingText">Rating:</p>
          <RatingComponent :rating="profile.ratingProfile" />
        </div>
      </div>
    </div>
    <map-test></map-test>
  </div>
</template>

<script>
import RatingComponent from "@/components/RateReview/RatingComponent";
import "vue3-openlayers/dist/vue3-openlayers.css";
import MapTest from "@/components/Map/MapComponent";
export default {
  props: ["profile"],
  components: { MapTest, RatingComponent },
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
  min-width: 160px;
  min-height: 150px;
}

#rating {
  margin-left: 20px;
}

#items {
  display: flex;
  flex-direction: column;
  font-size: 20px;
}

#ratingText {
  margin-right: 30px;
}

#map {
  width: 350px;
  height: 200px;
}
</style>

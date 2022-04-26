<template>
  <div class="profile-bar">
    <div class="profile-bar-text">
      <h3 id="username">{{ username }}</h3>
      <p id="phone-number">Phone nr: {{ phoneNumber }}</p>
      <p id="email">Email: {{ email }}</p>
    </div>
    <div id="rating">
      <RatingComponent />
    </div>
    <div class="container">
      <div class="all-buttons">
        <button
          id="items-button"
          class="boco-btn"
          @click="this.$router.push('/my/items')"
          type="submit"
        >
          Items
        </button>
        <button
          id="leases-button"
          class="boco-btn"
          @click="this.$router.push('/my/leases')"
          type="submit"
        >
          Leases
        </button>
        <button
          id="reviews-button"
          class="boco-btn"
          @click="this.$router.push('/my/reviews')"
          type="submit"
        >
          Reviews
        </button>
        <button
          id="settings-button"
          class="boco-btn"
          @click="this.$router.push('/my/settings')"
          type="submit"
        >
          Settings
        </button>
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
.profile-bar {
  color: white;
  border-radius: 20px;
  display: flex;
  font-size: 17px;
  width: 100%;
  height: 200px;
  padding: 10px 28px;
  background: var(--main-color);
  margin: 20px;
  flex-wrap: wrap;
}

.profile-bar-text {
  text-align: left;
}

#rating {
  margin-left: auto;
  margin-right: 20px;
}

#email {
  margin-top: -5px;
}

#phone-number {
  margin-bottom: -5px;
}

.buttons:hover,
.buttons:focus {
  background-color: var(--button-hover);
}

.buttons:disabled {
  cursor: not-allowed;
  background: rgba(0, 0, 0, 0.08);
  color: rgba(0, 0, 0, 0.3);
}

.all-buttons {
  display: flex;
  justify-content: space-evenly;
}
</style>

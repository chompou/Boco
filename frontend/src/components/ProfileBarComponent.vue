<template>
  <div class="profile-bar">
    <div class="profile-bar-text">
      <h3 id="username">{{ profile.displayName }}</h3>
      <p id="phone-number">Phone nr: {{ profile.tlf }}</p>
      <p id="email">Email: {{ profile.email }}</p>
    </div>
    <div id="rating">
      <RatingComponent />
    </div>
    <div class="container">
      <div class="all-buttons" v-if="isMe">
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
  props: ["profile"],

  computed: {
    isMe() {
      return this.$store.state.loggedInUser == this.profile.id;
    },
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
  margin-top: 5px;
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

.boco-btn {
  align-items: center;
  background-color: var(--button-color);
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto,
    "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell,
    "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  justify-content: center;
  line-height: 20px;
  max-width: 480px;
  min-height: 40px;
  min-width: 0;
  overflow: hidden;
  padding: 0 20px;
  text-align: center;
  touch-action: manipulation;
  transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
}

.boco-btn:hover,
.boco-btn:focus {
  background-color: var(--button-hover);
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

.boco-btn:disabled {
  cursor: not-allowed;
  background: rgba(0, 0, 0, 0.08);
  color: rgba(0, 0, 0, 0.3);
}

.all-buttons {
  display: flex;
  justify-content: space-evenly;
}
</style>

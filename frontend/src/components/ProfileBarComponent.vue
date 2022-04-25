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
      <div class="buttons">
        <button id="profile-button" class="buttons" type="submit">
          Profile
        </button>
        <button id="my-items-button" class="buttons" type="submit">
          My items
        </button>
        <button id="leasing-button" class="buttons" type="submit">
          Leasing
        </button>
        <button id="rating-button" class="buttons" type="submit">
          Rating/Reviews
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
  border-radius: 20px;
  display: flex;
  border: 1px solid #39495c;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
  height: 200px;
  color: #2c3e50;
  padding: 10px 28px;
  background: #e0f2fe;
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

.buttons {
  margin-right: 60px;
  margin-left: 60px;
}

.container {
}
</style>

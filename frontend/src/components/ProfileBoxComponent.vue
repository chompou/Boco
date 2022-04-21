<template>
  <div class="profileBox">
    <h2>{{ username }}</h2>
    <p>{{ phoneNumber }} || {{ email }}</p>
  </div>
  <div>
    <p>Map</p>
    <!--MAP-->
    <p>Rating</p>
    <!--RATING-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "test",
      phoneNumber: "66666666",
      email: "test@test.com",
    };
  },
};
</script>

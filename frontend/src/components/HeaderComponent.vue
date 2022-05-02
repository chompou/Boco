<template>
  <div id="header" class="container-fluid text-center" v-if="isHome">
    <router-link to="/">
      <img src="@/assets/NyLogoBoCo.png" alt="Logo" />
    </router-link>
  </div>
</template>
<script>
export default {
  computed: {
    isHome() {
      return this.$route.name === "home";
    },
  },
};
</script>

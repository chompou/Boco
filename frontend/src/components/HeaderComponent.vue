<template>
  <div id="header" class="container-fluid text-center" v-if="isHome">
    <router-link to="/">
      <img id="mainLogo" src="@/assets/mainLogo.png" alt="Logo" />
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

<style>
#mainLogo {
  height: 100px;
  width: auto;
}
</style>

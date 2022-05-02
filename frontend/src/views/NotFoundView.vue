<template>
  <div class="text-center">
    <h1>404</h1>
    <h2>Page not found!</h2>
    <router-link :to="{ name: 'home' }">Back to home page</router-link>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

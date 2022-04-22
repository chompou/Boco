<template>
  <h3>Reviews</h3>
  <div v-for="user in users" :key="user">
    <b id="name">{{ user.name }}</b>
    <p>{{ user.rating }}/5 - {{ user.description }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      users: [
        {
          name: "Olav Eikrem",
          rating: 3,
          description: "Helt ok",
        },
        {
          name: "Øyvind Bjøntegaard",
          rating: 5,
          description: "Fantastisk!",
        },
      ],
    };
  },
};
</script>

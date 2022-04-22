<template>
  <div class="forgottenPwd">
    <h2>Getting back to you BoCo account</h2>
    <p>Tell us some more information about your account.</p>
    <form @submit.prevent="submit" id="input">
      <label for="email">Email </label>
      <input
        v-model="email"
        type="email"
        name="email"
        id="email"
        placeholder="example@gmail.com"
        required
      />
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
    };
  },
  methods: {
    submit() {
      console.log("nice");
    },
  },
};
</script>

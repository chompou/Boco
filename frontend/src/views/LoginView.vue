<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="submit" id="inputs">
      <label for="username">Username </label>
      <input
        v-model="username"
        type="text"
        name="username"
        id="username"
        placeholder="Username"
        required
      />
      <br />
      <label for="password">Password </label>
      <input
        v-model="password"
        type="password"
        name="password"
        id="password"
        placeholder="Password"
        required
      />
      <input type="checkbox" @click="togglePassword()" />Show Password
      <br />
      <button id="Login" type="submit">Login</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    togglePassword() {
      const x = document.getElementById("password");
      if (x.type === "password") {
        x.type = "text";
      } else {
        x.type = "password";
      }
    },
    submit() {
      console.log("nice");
    },
  },
};
</script>

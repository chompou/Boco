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
      <br />
      <input type="checkbox" @click="togglePassword()" />Show Password
      <br />
      <p><a href="/ForgottenPwd">Forgotten password?</a></p>
      <button id="Login" type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
import storageService from "@/services/storageService";
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    togglePassword() {
      const checkbox = document.getElementById("password");
      if (checkbox.type === "password") {
        checkbox.type = "text";
      } else {
        checkbox.type = "password";
      }
    },
    submit() {
      apiService
        .login(this.username, this.password)
        .then((response) => {
          storageService.setToken(response.data["jwt"]);
          storageService.setUser(this.username);
          this.$store.state.loggedInUser = response.data["userId"];
          this.$router.push("/");
        })
        .catch((error) => console.error(error));
    },
  },
};
</script>

<style scoped>
.login {
  width: 25%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
}
#Login {
  display: flex;
  margin: 10px auto 0;
}
label,
input {
  display: block;
  margin: auto;
  padding: 10px;
}
</style>

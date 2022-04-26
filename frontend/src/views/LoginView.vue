<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="submit" id="inputs">
      <label for="username">Username </label>
      <input
        class="input-field"
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
        class="input-field"
        v-model="password"
        type="password"
        name="password"
        id="password"
        placeholder="Password"
        required
      />
      <br />
      <input
        id="toggle-password-box"
        type="checkbox"
        @click="togglePassword()"
      />
      Show Password
      <br />
      <button id="login-button" type="submit">Login</button>
      <p>
        <a id="forgotten-pwd-link" class="hyperlink" href="/ForgottenPwd"
          >Forgotten password?</a
        >
      </p>
      <p>
        <a id="register-link" class="hyperlink" href="/Register"
          >Register a new user</a
        >
      </p>
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
          storageService.setToken(response.data);
          storageService.setUser(this.username);
          this.$router.push("/");
        })
        .catch((error) => console.error(error));
    },
  },
};
</script>

<style scoped>
.login {
  width: 50%;
  display: flex;
  flex-direction: column;
  margin: auto;
  justify-content: space-evenly;
}
#login-button {
  align-items: center;
  background-color: #0a66c2;
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  color: #ffffff;
  cursor: pointer;
  display: inline-flex;
  font-size: 16px;
  font-weight: 600;
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
  justify-content: space-evenly;
}

#login-button:hover,
#login-button:focus {
  background-color: #16437e;
  color: #ffffff;
}

label,
.input-field {
  display: flex;
  margin: auto;
  padding: 10px;
  border-color: #0a66c2;
  border-radius: 5px;
  justify-content: space-evenly;
}

.hyperlink {
  display: flex;
  justify-content: space-around;
  margin: auto;
}

#forgotten-pwd-link {
  margin-top: 15px;
  margin-bottom: -20px;
}

#toggle-password-box {
  margin-top: auto;
  margin-bottom: 20px;
}

#password {
  margin-bottom: -15px;
}
</style>

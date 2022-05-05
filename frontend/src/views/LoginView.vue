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
      <div id="false-cred-id" v-if="failedLogin">
        Wrong credentials. Try again
      </div>
      <input
        id="toggle-password-box"
        type="checkbox"
        @click="togglePassword()"
      />
      Show Password
      <br />
      <button id="login-button" class="boco-btn" type="submit">Login</button>
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
import { useToast } from "vue-toastification";

export default {
  setup() {
    const toast = useToast();
    return { toast };
  },
  data() {
    return {
      username: "",
      password: "",
      failedLogin: false,
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
          this.$router.push("/");
          this.toast.success("Successfully logged in as " + this.username, {
            timeout: 2000,
          });
        })
        .catch(() => {
          this.failedLogin = true;
          this.toast.error("Wrong credentials try again", {
            timeout: 2000,
          });
        });
    },
  },

  created() {
    let user = storageService.getUser();
    if (user != null) {
      this.username = user;
    }
  },
};
</script>

<style scoped>
.boco-btn {
  align-items: center;
  background-color: var(--button-color);
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto,
    "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell,
    "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  justify-content: center;
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
}

.boco-btn:hover,
.boco-btn:focus {
  background-color: var(--button-hover);
}

.login {
  width: 50%;
  display: flex;
  flex-direction: column;
  margin: auto;
  justify-content: space-evenly;
  text-align: center;
}

label,
.input-field {
  display: flex;
  margin: auto;
  padding: 10px;
  border-color: var(--button-color);
  border-radius: 5px;
  justify-content: space-evenly;
}
.input-field:hover,
.input-field:focus {
  border-color: var(--button-hover);
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

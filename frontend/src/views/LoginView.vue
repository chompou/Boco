<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="submit" id="inputs">
      <label for="username">Brukernavn </label>
      <input
        v-model="username"
        type="text"
        name="username"
        id="username"
        placeholder="Brukernavn"
      />
      <br />
      <label for="password">Passord </label>
      <input
        v-model="password"
        type="password"
        name="password"
        id="password"
        placeholder="Passord"
      />
      <input type="checkbox" @click="togglePassword()" />Show Password
      <br />
      <button id="Login" type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import { useField, useForm } from "vee-validate";
import * as yup from "yup";
//import axios from "axios";

export default {
  data() {
    return {
      wrongPassword: false,
      notRegistered: false,
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
      /*
      axios
        .post(
          "http://localhost:8087/api/users",
          {
            username: this.username,
            password: this.password,
          },
          {
            auth: {
              username: this.username,
              password: this.password,
            },
            "Content-type": "application/json; charset=UTF-8",
          }
        )
        .then((response) => {
          console.log(response.data);
          if (response.data) {
            this.$store.commit("LOGGED_IN", response.data);
            this.$store.commit("SET_CURRENT_USER", this.username);
            this.$store.commit("SET_CURRENT_PASSWORD", this.password);
            console.log(this.$store.getters.username);
            console.log(this.$store.getters.password);
            this.$router.push("/Calculator").catch((error) => {
              console.log(error);
            });
          } else {
            this.wrongPassword = true;
          }
        })
        .catch((e) => {
          console.log(e);
        });
   */
    },
  },
  setup: function () {
    const validation = yup.object({
      username: yup.string().required(),
    });
    const { errors } = useForm({
      validationSchema: validation,
      initialValues: {
        username: "",
        password: "",
      },
    });

    const { value: username } = useField("username");
    const { value: password } = useField("password");

    return {
      username,
      password,
      errors,
    };
  },
};
</script>

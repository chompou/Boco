<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="submit" id="inputs">
      <label for="username">Enter your username: </label>
      <input type="text" name="username" id="username" />
      <br />
      <label for="password">Enter your password: </label>
      <input type="text" name="password" id="password" />
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

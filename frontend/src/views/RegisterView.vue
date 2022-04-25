<template>
  <h2>Register new user</h2>
  <div class="registerContainer">
    <label>Username</label>
    <input
      v-model="username"
      type="text"
      placeholder="Enter username"
      name="username"
      id="username"
      required
    />
    <br />
    <label>Email</label>
    <input
      v-model="email"
      type="email"
      placeholder="Enter email"
      name="email"
      id="email"
      required
    />
    <br />
    <label>Phone number</label>
    <input
      v-model="phoneNumber"
      type="text"
      placeholder="Enter phone number"
      name="phoneNumber"
      id="phoneNumber"
      required
    />
    <br />
    <label>Password</label>
    <input
      v-model="password"
      type="password"
      placeholder="Enter password"
      name="password"
      id="password"
      required
    />
    <br />
    <label>Verify Password</label>
    <input
      v-model="verifyPassword"
      type="password"
      placeholder="Verify password"
      name="password"
      id="verifyPassword"
      required
    />
    <br />
    <button class="registerbutton" @click="onSubmit">Submit</button>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
export default {
  data() {
    return {
      username: "",
      email: "",
      phoneNumber: "",
      password: "",
      verifyPassword: "",
    };
  },

  methods: {
    onSubmit() {
      apiService
        .createProfile({
          username: this.username,
          email: this.email,
          tlf: this.phoneNumber,
          password: this.password,
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.registerContainer {
  width: 25%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
}
#registerbutton {
  display: flex;
  margin: 0 auto;
  margin-top: 10px;
}
</style>

<template>
  <h2>Register new user</h2>
  <div class="registerContainer">
    <label>Username</label>
    <input
      class="input-field"
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
      class="input-field"
      v-model="email"
      type="email"
      placeholder="example@gmail.com"
      name="email"
      id="email"
      required
    />
    <br />
    <label>Phone number</label>
    <input
      class="input-field"
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
      class="input-field"
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
      class="input-field"
      v-model="verifyPassword"
      type="password"
      placeholder="Verify password"
      name="password"
      id="verifyPassword"
      required
    />
    <br />
    <button class="boco-btn" @click="onSubmit">Submit</button>
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
  width: 30%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
}

label,
.input-field {
  width: 80%;
  display: flex;
  margin: auto;
  padding: 8px;
  border-color: var(--button-color);
  border-radius: 5px;
  justify-content: space-around;
}
.input-field:hover,
.input-field:focus {
  border-color: var(--button-hover);
}
</style>

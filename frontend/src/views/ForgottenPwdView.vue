<template>
  <div class="forgotten-pwd">
    <h2 id="getting-back" class="text">Getting back to your BoCo account</h2>
    <p id="tell-us" class="text">
      Tell us some more information about your account.
    </p>
    <form @submit.prevent="submit" id="input">
      <label for="email">Email </label>
      <input
        v-model="email"
        type="email"
        name="email"
        id="email"
        class="input-field"
        placeholder="example@gmail.com"
        required
      />
      <p v-if="emailSent">
        If your email exists in our system, we have sent you instructions on how
        to get back to your BoCo account
      </p>
      <br />
      <button id="submit" class="boco-btn" type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      emailSent: false,
    };
  },
  methods: {
    submit() {
      console.log("nice");
      this.emailSent = true;
    },
  },
};
</script>

<style scoped>
.boco-btn {
  margin: 20px;
}
.text {
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
.forgotten-pwd {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  align-content: flex-start;
  margin: auto;
  flex-direction: column;
}
</style>

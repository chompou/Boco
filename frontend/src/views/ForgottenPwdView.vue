<template>
  <div class="forgotten-pwd">
    <h2 id="getting-back" class="dropdownMenuText">
      Getting back to your BoCo account
    </h2>
    <p id="tell-us" class="dropdownMenuText">
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
      <br />
      <button id="submit" class="boco-btn" type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import { useToast } from "vue-toastification";
import apiService from "@/services/apiService";

export default {
  setup() {
    const toast = useToast();
    return { toast };
  },
  data() {
    return {
      email: "",
      emailSent: false,
    };
  },
  methods: {
    submit() {
      apiService.sendEmail(this.email);
      this.emailSent = true;
      this.toast.info("A mail has been sent to " + this.email, {
        timeout: 4000,
      });
      this.$router.push({ name: "newPwd", params: { email: this.email } });
    },
  },
};
</script>

<style scoped>
.boco-btn {
  margin: 20px;
}
.dropdownMenuText {
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

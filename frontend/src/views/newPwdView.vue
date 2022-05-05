<template>
  <div class="registerContainer">
    <h1>New password</h1>
    <form id="fields">
      <label>Email Code</label>
      <BaseInput
        class="register-input-field"
        v-model="code"
        type="text"
        placeholder="123456789"
        name="code"
        id="code"
        :error="errors.username"
      />
      <br />
      <label>New Password</label>
      <BaseInput
        class="register-input-field"
        v-model="password"
        type="password"
        placeholder="Enter password"
        name="password"
        id="password"
        :error="errors.password"
      />
      <br />
      <label>Verify Password</label>
      <BaseInput
        class="register-input-field"
        v-model="verifyPassword"
        type="password"
        placeholder="Verify password"
        name="password"
        id="verifyPassword"
        :error="errors.verifyPassword"
      />
      <button type="submit" class="boco-btn submit" @click="submit">
        Submit
      </button>
    </form>
  </div>
</template>

<script>
import apiService from "@/services/apiService";
import BaseInput from "@/components/base/BaseInput";
import { object, string } from "yup";
import { useField, useForm } from "vee-validate";
import * as Yup from "yup";
import router from "@/router";
import { useToast } from "vue-toastification";

export default {
  props: ["email"],
  components: {
    BaseInput,
  },
  setup() {
    setTimeout(() => {
      console.log(this);
    }, 1000);
    const validationSchema = object({
      code: string().required("This field is required!"),
      password: string().required("This field is required!"),
      verifyPassword: Yup.string().oneOf(
        [Yup.ref("password"), null],
        "Passwords must match"
      ),
    });

    const { handleSubmit, errors } = useForm({
      validationSchema,
    });
    const code = useField("code");
    const password = useField("password");
    const verifyPassword = useField("verifyPassword");
    const toast = useToast();

    const submit = handleSubmit(() => {
      apiService
        .newPassword(this.email, code.value, password.value.value)
        .catch((error) => {
          console.log(error);
        });
      router.push("/Login");
      toast.success("Account has been registered", {
        timeout: 3000,
      });
    });

    return {
      submit,
      code: code.value,
      verifyPassword: verifyPassword.value,
      password: password.value,
      errors,
      toast,
    };
  },
};
</script>

<style scoped>
.registerContainer {
  width: 30%;
  justify-items: center;
  text-align: center;
  display: grid;
  flex-direction: column;
  margin: 0 auto;
}

#is-personal-dropdown {
  margin-bottom: 20px;
}

#fields {
  margin-top: 30px;
}

.submit {
  margin-top: 30px;
}
</style>

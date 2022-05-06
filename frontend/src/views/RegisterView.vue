<template>
  <div class="registerContainer">
    <h1>Register new user</h1>
    <form>
      <label>Username</label>
      <BaseInput
        class="register-input-field"
        v-model="username"
        type="text"
        placeholder="Enter username"
        name="username"
        id="username"
        :error="errors.username"
      />
      <br />
      <label>Email</label>
      <BaseInput
        class="register-input-field"
        v-model="email"
        type="email"
        placeholder="example@gmail.com"
        name="email"
        id="email"
        :error="errors.email"
        @change="handleChange"
      />
      <br />
      <label>Address</label>
      <BaseInput
        class="register-input-field"
        v-model="address"
        type="text"
        placeholder="Enter address"
        name="address"
        id="address"
        :error="errors.address"
      />
      <br />
      <label>Phone number</label>
      <BaseInput
        class="register-input-field"
        v-model="phoneNumber"
        type="text"
        placeholder="Enter phone number"
        name="phoneNumber"
        id="phoneNumber"
        :error="errors.phoneNumber"
      />
      <br />
      <label>Password</label>
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
      <br />
      <button type="submit" class="boco-btn" @click="submit">Submit</button>
      <br />
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
  components: {
    BaseInput,
  },
  setup() {
    const phoneRegExp =
      /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;
    const validationSchema = object({
      username: string().required("This field is required!"),
      address: string().required("This field is required!"),
      email: string()
        .required("This field is required!")
        .email("Please provide a valid email"),
      phoneNumber: Yup.string().matches(
        phoneRegExp,
        "Phone number is not valid"
      ),
      password: string().required("This field is required!"),
      verifyPassword: Yup.string().oneOf(
        [Yup.ref("password"), null],
        "Passwords must match"
      ),
    });

    const { handleSubmit, errors, setFieldValue } = useForm({
      validationSchema,
    });

    const username = useField("username");
    const email = useField("email");
    const address = useField("address");
    const phoneNumber = useField("phoneNumber");
    const password = useField("password");
    const verifyPassword = useField("verifyPassword");
    const toast = useToast();

    const handleChange = (event) => {
      setFieldValue("email", event.target.value);
    };
    const submit = handleSubmit(() => {
      apiService
        .createProfile({
          username: username.value.value,
          email: email.value.value,
          description: "",
          displayName: username.value.value,
          passwordHash: password.value.value,
          address: address.value.value,
          tlf: phoneNumber.value.value,
          isPersonal: true,
        })
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
      phoneNumber: phoneNumber.value,
      verifyPassword: verifyPassword.value,
      password: password.value,
      address: address.value,
      email: email.value,
      username: username.value,
      errors,
      handleChange,
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
</style>

<template>
  <div class="row">
    <h1 class="text-center">Support</h1>
    <div class="col-6 mx-auto">
      <form @submit="submit">
        <div class="mb-3">
          <BaseInput
            class="form-control"
            v-model="name"
            label="Name"
            type="name"
            :error="errors.name"
          />
        </div>
        <div class="mb-3">
          <BaseInput
            class="form-control"
            label="Email"
            type="email"
            :modelValue="email"
            :error="errors.email"
            @change="handleChange"
          />
        </div>
        <div class="mb-3">
          <BaseTextArea
            class="form-control textArea"
            v-model="message"
            label="Issue"
            type="text"
          />
        </div>
        <button type="submit" class="btn btn-primary">Send</button>
      </form>
    </div>
  </div>
</template>
<script>
import BaseInput from "@/components/base/BaseInput";
import BaseTextArea from "@/components/base/BaseTextArea";
import { useField, useForm } from "vee-validate";
import { object, string } from "yup";
import { useToast } from "vue-toastification";

export default {
  components: {
    BaseTextArea,
    BaseInput,
  },
  setup() {
    const toast = useToast();
    const validationSchema = object({
      name: string().required("This field is required!"),
      email: string()
        .required("This field is required!")
        .email("Please provide a valid email!"),
    });

    const { handleSubmit, errors, setFieldValue } = useForm({
      validationSchema,
    });

    const name = useField("name");
    const email = useField("email");
    const message = useField("message");

    const handleChange = (event) => {
      setFieldValue("email", event.target.value);
    };

    //Handle submit, do something other than logging
    const submit = handleSubmit((values) => {
      console.log("submit", values);
      toast.info("Support ticket sent", {
        timeout: 2000,
      });
    });

    return {
      submit,
      name: name.value,
      email: email.value,
      message: message.value,
      errors,
      handleChange,
      toast,
    };
  },
};
</script>
<style scoped>
/*div {
  border: black solid 1px;
}*/
</style>

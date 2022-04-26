<template>
  <label class="form-label">{{ label }}</label>
  <textarea
    v-bind="$attrs"
    :placeholder="label"
    :value="modelValue"
    @input="$emit('update:modelValue', $event.target.value)"
    :aria-describedby="error ? error : null"
    :aria-invalid="error ? true : false"
    :class="{ error }"
  ></textarea>
  <BaseErrorMessage v-if="error" :id="error">
    {{ error }}
  </BaseErrorMessage>
</template>

<script>
import BaseErrorMessage from "@/components/base/BaseErrorMessage";
export default {
  components: {
    BaseErrorMessage,
  },
  props: {
    label: {
      type: String,
      default: "",
    },
    error: {
      type: String,
      default: "",
    },
    modelValue: {
      type: [String, Number],
      default: "",
    },
  },
};
</script>

<template>
  <div class="notificationWrapper">
    <div :id="id" class="left"></div>
    <div :id="id" class="notificationContent">{{ text }}</div>
  </div>
</template>
<script>
import apiService from "@/services/apiService";

export default {
  props: {
    text: String,
    id: Number,
  },
  mounted() {
    addEventListener("click", (event) => {
      let id = event.target.id;
      if (id !== null || id !== "") {
        if (!isNaN(id) && id !== "") {
          apiService.removeNotificationAfterRead(id);
        }
      }
    });
  },
};
</script>
<style scoped>
.notificationWrapper {
  display: flex;
  width: 400px;
  border-radius: 49px 50px 50px 49px;
}

.notificationWrapper:hover {
  background-color: #e1eeff;
  cursor: pointer;
}

.left {
  width: 20px;
  background-color: #0048ae;
  border-radius: 50px 0 0 50px;
}

.notificationContent {
  overflow-wrap: anywhere;
  flex-grow: 1;
  padding: 0.5rem;
}
</style>

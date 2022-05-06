<template>
  <div id="test" class="notificationWrapper" :style="notificationWrapperStyle">
    <div class="left" :style="leftDivStyle"></div>
    <div :id="id" class="notificationContent">{{ text }}</div>
  </div>
</template>
<script>
import apiService from "@/services/apiService";

export default {
  props: {
    text: String,
    id: Number,
    width: Number,
    height: Number,
    leftDivBgColor: {
      type: String,
      default: "blue",
    },
  },
  computed: {
    notificationWrapperStyle() {
      return {
        width: `${this.width}px`,
        height: `${this.height}px`,
      };
    },
    leftDivStyle() {
      return {
        backgroundColor: this.leftDivBgColor,
      };
    },
  },
  mounted() {
    document.getElementById(this.id).addEventListener("click", (event) => {
      let id = event.target.id;
      if (id !== null || id !== "") {
        if (!isNaN(id) && id !== "") {
          apiService.markNotificationAsRead([id]).catch((error) => {
            console.log(error);
          });
        }
      }
    });
  },
};
</script>
<style scoped>
.notificationWrapper {
  display: flex;
  border-radius: 20px 50px 50px 20px;
}

.notificationWrapper:hover {
  background-color: #e1eeff;
  cursor: pointer;
}

.left {
  width: 10px;
  border-radius: 50px 0 0 50px;
}

.notificationContent {
  overflow-wrap: anywhere;
  flex-grow: 1;
  padding: 0.5rem;
}
</style>

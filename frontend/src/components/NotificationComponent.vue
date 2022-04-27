<template>
  <p>notification: {{ $store.state.countNotifications }}</p>
</template>

<script>
import { onMounted } from "vue";
import store from "@/store";
export default {
  setup() {
    const id = store.state.loggedInUser;
    const wsURL = "ws://localhost:8080/websocket/" + id;

    const webSocket = new WebSocket(wsURL);

    onMounted(() => {
      webSocket.addEventListener("open", () => {
        console.log("WebSocket connected");
        webSocket.send("Hei from Vue");
      });
      webSocket.addEventListener("message", (event) => {
        console.log("Incoming data");
        console.log(event.data);
        store.dispatch("UPDATE_COUNT_NOTIFICATION");
      });
    });
  },
};
</script>

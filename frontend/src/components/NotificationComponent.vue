<template>
  <p>notification: {{ $store.state.countNotifications }}</p>
</template>

<script>
import { onMounted } from "vue";
import store from "@/store";
export default {
  setup() {
    const webSocket = new WebSocket("ws://localhost:8080/websocket/2");
    //const test = ref("");
    let numberNotifications = 0;

    onMounted(() => {
      webSocket.addEventListener("open", () => {
        console.log("Conn ok");
        webSocket.send("Hei from Vue");
      });
      webSocket.addEventListener("message", (event) => {
        console.log("Incoming data");
        console.log(event.data);
        numberNotifications++;
        store.dispatch("UPDATE_COUNT_NOTIFICATION");
        console.log(numberNotifications);
      });
    });
    return { numberNotifications };
  },
};
</script>

<template>
  <div>notification</div>
  <p>{{ numberNotifications }}</p>
</template>

<script>
import { onMounted } from "vue";
export default {
  setup() {
    const webSocket = new WebSocket("ws://localhost:8080/websocket/6");
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
        console.log(numberNotifications);
      });
    });
    return { numberNotifications };
  },
};
</script>

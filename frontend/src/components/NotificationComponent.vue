<template>
  <div class="notif">
    <div id="count">{{ $store.state.countNotifications }}</div>
    <div>
      <font-awesome-icon icon="bell" class="icon">Test</font-awesome-icon>
    </div>
  </div>
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
<style scoped>
.notif {
  width: 100px;
  display: flex;
}

.icon {
  height: 30px;
  width: 30px;
  font-size: 2vw;
  color: #0048ae;
}

#count {
  color: red;
  font-size: 20px;
  font-weight: bold;
}
</style>

<template>
  <button class="notificationButton" key="on" @click="show = !show">
    <div class="notification" :style="notificationStyle">
      <div :width="size" :height="size">
        <font-awesome-icon icon="bell" class="bellIcon" />
      </div>
      <div
        class="notificationCounter"
        v-if="$store.state.countNotifications > 0"
        :style="notificationCounterLocation"
      >
        <div class="counterWrapper">
          <span v-if="$store.state.countNotifications <= 10">{{
            $store.state.countNotifications
          }}</span>
          <span v-if="$store.state.countNotifications > 10"> 10+</span>
        </div>
      </div>
    </div>
  </button>
  <transition name="bounce">
    <div class="dropdownMenu" v-if="show">
      <div class="gridContainer">
        <div>
          <p class="title">Notifications</p>
        </div>
      </div>
      <hr />
    </div>
  </transition>
</template>
<script>
import store from "@/store";
import { onMounted } from "vue";

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
  data() {
    return {
      show: false,
    };
  },
  props: {
    size: {
      type: Number,
      default: 30,
    },
  },
  computed: {
    notificationStyle() {
      return {
        display: "grid",
        position: "relative",
        width: `${this.size}px`,
        height: `${this.size}px`,
      };
    },
    notificationCounterLocation() {
      return {
        position: "absolute",
        left: `calc(100% - ${this.size * 0.45}px)`,
        transform: "translateY(-40%)",
        fontSize: `${this.size * 0.5}px`,
      };
    },
  },
  watch: {
    $route: {
      handler() {
        //Close notification box at route change
        this.show = false;
      },
    },
  },
};
</script>
<style scoped>
.notificationButton {
  align-items: center;
  margin-top: 0.1rem;
  border: none;
  background-color: var(--background-color-header-nav-footer);
}

button:hover {
  transform: scale(1.2);
  box-shadow: 0 3px 12px 0 var(--background-color-header-nav-footer);
}

.counterWrapper {
  display: grid;
  grid-auto-flow: column;
}

.bellIcon {
  color: var(--navbar-icons);
  height: 40px;
  width: 40px;
}

.notificationCounter {
  width: 30px;
  text-align: center;
  border-radius: 100%;
  background-color: red;
  color: white;
}

/* Dropdown menu styling*/
.dropdownMenu {
  right: 0;
  position: absolute;
  z-index: 10;
  height: 25rem;
  min-width: 30rem;
  margin-top: 1rem;
  overflow-y: auto;
  padding: 2rem 1rem 2rem 0;
  border-radius: 12px;
  background-color: white;
  border: 1px solid black;
  background-clip: padding-box;
}

.title {
  margin-left: 10px;
  margin-top: 10px;
}

/*Dropdown Menu Animation */
.bounce-enter-active {
  animation: bounce-in 0.3s;
}
.bounce-leave-active {
  animation: bounce-in 0.2s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0.8);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}
</style>

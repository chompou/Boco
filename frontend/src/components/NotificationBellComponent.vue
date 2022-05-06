<template>
  <button
    class="notificationButton"
    key="on"
    @click="toggle"
    v-click-outside="close"
  >
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
    <div id="dropdownMenu" class="dropdownMenu" v-if="show">
      <div class="header">
        <div>
          <p class="title">Notifications</p>
        </div>
        <div>
          <p class="markAsRead" @click="readAllNotifications">
            Mark all as read
          </p>
        </div>
        <div>
          <router-link to="/my/notifications"
            ><p class="viewAll">View all notifications</p></router-link
          >
        </div>
      </div>
      <hr />
      <div class="content">
        <ul>
          <li
            v-for="notification in $store.getters.getUnreadNotifications"
            :key="notification.id"
          >
            <router-link to="/my/notifications">
              <NotificationComponent
                :text="notification.message"
                :id="notification.id"
            /></router-link>
          </li>
        </ul>
      </div>
    </div>
  </transition>
</template>
<script>
import store from "@/store";
import { onMounted, onUnmounted } from "vue";
import apiService from "@/services/apiService";
import NotificationComponent from "@/components/NotificationComponent";

export default {
  components: { NotificationComponent },
  setup() {
    let id = store.state.loggedInUser;
    const wsURL = "ws://localhost:8080/websocket/" + id;

    const webSocket = new WebSocket(wsURL);
    /*Lifecycle hook to start websocket that handles notifications*/
    onMounted(() => {
      webSocket.addEventListener("open", () => {
        apiService
          .getNotifications()
          .then((response) => {
            response.data.unread.forEach((notification) => {
              store.dispatch("ADD_UNREAD_NOTIFICATION", notification);
              store.dispatch(
                "UPDATE_COUNT_NOTIFICATION",
                store.getters.getUnreadNotifications.length
              );
            });
            response.data.read.forEach((notification) => {
              store.dispatch("ADD_READ_NOTIFICATION", notification);
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
      webSocket.addEventListener("message", (event) => {
        store.dispatch("UPDATE_COUNT_NOTIFICATION", event.data);
        apiService
          .getNotifications()
          .then((response) => {
            store.dispatch("CLEAR_NOTIFICATIONS");
            response.data.unread.forEach((notification) => {
              store.dispatch("ADD_UNREAD_NOTIFICATION", notification);
            });
            response.data.read.forEach((notification) => {
              store.dispatch("ADD_READ_NOTIFICATION", notification);
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
    });
    /*Lifecycle hook to close websocket when notification component gets unmounted*/
    onUnmounted(() => {
      store.dispatch("RESET_COUNT_NOTIFICATION");
      store.dispatch("CLEAR_NOTIFICATIONS");
      webSocket.close();
    });
  },
  data() {
    return {
      show: false,
    };
  },
  methods: {
    readAllNotifications() {
      let id = [];
      store.getters.getUnreadNotifications.forEach((notification) => {
        id.push(notification.id);
      });
      apiService.markNotificationAsRead(id).catch((error) => {
        console.log(error);
      });
    },
    toggle() {
      this.show = !this.show;
    },
    close() {
      this.show = false;
    },
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
ul {
  list-style-type: none;
}

ul li {
  margin: 8px;
}

hr {
  margin-left: 10px;
}

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
  height: 38px;
  width: 38px;
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
  padding: 2rem 1rem 2rem 0;
  border-radius: 12px;
  background-color: white;
  border: 1px solid black;
  background-clip: padding-box;
}

.header {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  width: 100%;
}

.content {
  overflow-x: auto;
  overflow-y: auto;
  height: 15rem;
  max-width: 30rem;
}

.title {
  margin-left: 10px;
  margin-top: 10px;
}

.viewAll,
.markAsRead {
  margin-top: 10px;
}

.viewAll:hover,
.markAsRead:hover {
  cursor: pointer;
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

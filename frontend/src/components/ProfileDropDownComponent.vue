<template>
  <div v-if="!$store.state.loggedIn && isOnNonAuthorizationPage">
    <button class="loginButton" @click="routeToLogin()">
      <font-awesome-icon icon="user" />
      Sign in
    </button>
  </div>
  <div v-if="$store.state.loggedIn">
    <button class="profileButtons" v-if="show" key="on" @click="show = false">
      <font-awesome-icon icon="user" />
    </button>
    <button
      id="profileOpenButton"
      class="profileButtons"
      v-else
      key="off"
      @click="show = true"
    >
      <font-awesome-icon icon="user" class="test" />
    </button>
    <transition name="bounce">
      <div class="dropdownMenu" v-if="show">
        <div class="gridContainer">
          <div>
            <p class="username">{{ $store.getters.getDisplayName }}</p>
          </div>
          <div>
            <button class="logoutButton" @click="logout()">
              <font-awesome-icon class="signOutIcon" icon="sign-out-alt" />
            </button>
          </div>
        </div>
        <hr />
        <ul class="dropdownMenuNav">
          <li>
            <router-link to="/my/items" class="dropdownMenuLink">
              <font-awesome-icon class="dropdownMenuIcons" icon="user" />
              <div class="dropdownMenuText">Account</div>
            </router-link>
          </li>
          <li>
            <router-link to="/create" class="dropdownMenuLink">
              <font-awesome-icon class="dropdownMenuIcons" icon="plus" />
              <div class="dropdownMenuText">Add item</div>
            </router-link>
          </li>
          <li>
            <router-link to="/support" class="dropdownMenuLink">
              <font-awesome-icon class="dropdownMenuIcons" icon="info" />
              <div class="dropdownMenuText">Support</div>
            </router-link>
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script>
import storageService from "@/services/storageService";

export default {
  name: "ProfileComponent",
  data() {
    return {
      show: false,
    };
  },
  methods: {
    routeToLogin() {
      return this.$router.push({ path: "/login" });
    },
    logout() {
      storageService.clearToken();
      this.$store.state.loggedIn = false;
      this.$store.state.loggedInUser = null;
      this.$store.dispatch("RESET_NOTIFICATION");
      this.show = false;
      this.$router.push({ path: "/" });
    },
  },
  computed: {
    //show login on all pages that does not require authorization
    isOnNonAuthorizationPage() {
      return (
        this.$route.path === "/" ||
        this.$route.name === "items" ||
        this.$route.name === "item" ||
        this.$route.name === "support" ||
        this.$route.name === "profile"
      );
    },
  },
  watch: {
    $route: {
      handler() {
        //Close profile box at route change
        this.show = false;
      },
    },
  },
};
</script>
<style scoped>
/*Style login button */
.loginButton {
  align-items: center;
  background-color: var(--button-color);
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto,
    "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell,
    "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  line-height: 20px;
  max-width: 480px;
  min-height: 40px;
  min-width: 0;
  overflow: hidden;
  padding: 0 20px;
  text-align: center;
  touch-action: manipulation;
  transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s,
    color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
}

.loginButton:hover,
.loginButton:focus {
  transform: scale(1.2);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2);
}

/*Style profile button (buttons that come after login) */
.profileButtons {
  display: block;
  height: 40px;
  width: 40px;
  border-radius: 50%;
  border: none;
  background-color: #5eeb5b;
}

.profileButtons:hover,
.profileButtons:focus {
  background-color: DodgerBlue;
  transform: scale(1.2);
}

/* Dropdown menu styling*/
.dropdownMenu {
  right: 0;
  position: absolute;
  z-index: 10;
  height: 25rem;
  min-width: 300px;
  margin-top: 1rem;
  overflow-y: auto;
  padding: 2rem 1rem 2rem 0;
  border-radius: 12px;
  background-color: white;
  border: 1px solid black;
  background-clip: padding-box;
}

.dropdownMenuNav {
  list-style-type: none;
}

.dropdownMenuLink {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: rgba(0, 0, 0, 0.6);
  padding: 0.8rem 0 0.8rem 2rem;
  margin-top: 0.2rem;
  margin-bottom: 0.2rem;
  border-radius: 0 50px 50px 0;
}

.dropdownMenuLink:hover {
  color: #17bf63;
  background-color: rgba(79, 192, 141, 0.1);
  border-radius: 50px;
}

.dropdownMenuText {
  font-weight: 300;
  font-size: 1.3rem;
  margin-left: 1rem;
  margin-right: 1rem;
}

.signOutIcon {
  color: var(--main-color);
}

.dropdownMenuIcons {
  color: var(--main-color);
  height: 25px;
  width: 25px;
}

.username {
  margin-left: 10px;
  margin-top: 10px;
}

.logoutButton {
  right: 10px;
  position: absolute;
  border: none;
  background-color: white;
  font-size: 2em;
}

.gridContainer {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-bottom: -15px;
  margin-top: -15px;
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

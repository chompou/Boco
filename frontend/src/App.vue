<template>
  <nav class="navbar sticky-top navbar-expand-sm navbar-light bg-light">
    <div class="container-fluid">
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#collapsedNav"
        aria-label="Navigation toggle"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <img id="logo" src="@/assets/mainLogo.png" alt="logo" />
      <div
        id="collapsedNav"
        class="collapse navbar-collapse justify-content-center bg-light"
      >
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="login" v-if="!loggedIn"
                ><font-awesome-icon icon="right-to-bracket"
              /></router-link>
              <button id="log-out-button" v-if="loggedIn">
                <font-awesome-icon icon="sign-out-alt" />
              </button>
            </a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/my/items"
                ><font-awesome-icon class="icons" icon="user"
              /></router-link>
            </a>
          </li>
        </ul>

        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/create"
                ><font-awesome-icon class="icons" icon="plus"
              /></router-link>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/"
                ><font-awesome-icon class="icons" icon="house"
              /></router-link>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/info"
                ><font-awesome-icon class="icons" icon="info"
              /></router-link>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
    <router-view />
    <div style="height: 20px" />
  </div>
</template>

<script>
import store from "@/store/index.js";
export default {
  computed: {
    loggedIn() {
      return store.state.loggedIn;
    },
  },
};
</script>

<style>
:root {
  --main-color: #008b8b;
  --button-hover: #006c6c;
  --button-color: #00a5a5;
  --text-color: #2c3e50;
  --text-color2: white;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

/*Front page style*/
#frontPageSearchField {
  width: 200px;
}

.icon {
  font-size: 2vw;
}
/*Support form style*/
.textArea {
  height: 150px;
}

.errorMessage {
  color: red;
}

/* Navbar styling */
li a {
  text-decoration: none;
}

li a.router-link-exact-active {
  color: lightgray;
}

li a.router-link-exact-active:hover {
  color: lightgray;
}

li a:hover {
  color: lightgray;
}

.nav-item {
  font-size: x-large;
}

/*
.navbar-toggler .navbar-toggler-icon {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg viewBox='0 0 32 32' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath stroke='white' stroke-width='2' stroke-linecap='round' stroke-miterlimit='10' d='M4 8h24M4 16h24M4 24h24'/%3E%3C/svg%3E");
}*/

/* Navbar styling end*/

/* Make navbar slide from left on small screens*/
@media (max-width: 479px) {
  .navbar-collapse {
    position: fixed;
    top: 20px;
    left: 0;
    padding-left: 15px;
    padding-right: 15px;
    padding-bottom: 15px;
    width: 45%;
    height: 100%;
    /*background-color: #343a40;*/
    background-color: gray;
    margin-top: 35px;
  }

  .navbar-collapse.collapsing {
    left: -75%;
    transition: height 0s ease;
  }

  .navbar-collapse.show {
    left: 0;
    transition: left 300ms ease-in-out;
  }

  .navbar-toggler.collapsed ~ .navbar-collapse {
    transition: left 500ms ease-in-out;
  }
}

#log-out-button {
  background: none;
  color: inherit;
  border: none;
  padding: 0;
  font: inherit;
  cursor: pointer;
  outline: inherit;
}

.boco-btn {
  align-items: center;
  background-color: var(--button-color);
  border: 0;
  border-radius: 100px;
  box-sizing: border-box;
  color: #ffffff;
  cursor: pointer;
  display: inline-flex;
  font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto,
    "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell,
    "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    "Lucida Grande", Helvetica, Arial, sans-serif;
  font-size: 16px;
  font-weight: 600;
  justify-content: center;
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

.boco-btn:hover,
.boco-btn:focus {
  background-color: var(--button-hover);
}

.boco-btn:active {
  background: #09223b;
  color: rgb(255, 255, 255, 0.7);
}

.boco-btn:disabled {
  cursor: not-allowed;
  background: rgba(0, 0, 0, 0.08);
  color: rgba(0, 0, 0, 0.3);
}

#logo {
  height: 70px;
  width: auto;
}

.icons {
  color: var(--main-color);
}

.icons:hover {
  color: gray;
}
</style>

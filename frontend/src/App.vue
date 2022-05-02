<template>
  <header-component />
  <nav id="navbar" class="navbar sticky-top">
    <div class="container-fluid">
      <router-link to="/">
        <img id="logo" src="@/assets/mainLogo.png" alt="logo"
      /></router-link>
      <NotificationComponent v-if="$store.state.loggedIn" />
      <ProfileDropDownComponent />
    </div>
  </nav>

  <div class="container-fluid">
    <router-view />
    <div style="height: 20px" />
  </div>
  <div id="footer">
    <footer>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-sm-4 col-md-3 item">
            <h4><a href="#">About us</a></h4>
          </div>
          <div class="col-sm-4 col-md-3 item">
            <router-link to="/support"><h4>Contact us</h4></router-link>
          </div>
          <div class="col-sm-4 col-md-3 item">
            <h4><a href="#">F.A.Q</a></h4>
          </div>
          <div class="col-lg-3 item social">
            <p class="copyright">Borrow Community © 2022</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import NotificationComponent from "@/components/NotificationComponent";
import HeaderComponent from "@/components/HeaderComponent";
import ProfileDropDownComponent from "@/components/ProfileDropDownComponent";
export default {
  components: {
    NotificationComponent,
    HeaderComponent,
    ProfileDropDownComponent,
  },
  methods: {},
};
</script>

<style>
:root {
  --main-color: #008b8b;
  --button-hover: #006c6c;
  --button-color: #00a5a5;
  --text-color: #2c3e50;
  --text-color2: white;
  --background-color-header-nav-footer: #ececec;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#footer,
#header {
  background-color: #ececec;
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
#navbar {
  background-color: #ececec;
}
/* Navbar styling end*/

/* FOOTER style */

#footer {
  align-items: center;
  display: flex;
  width: 100%;
  padding: 2rem 2rem 0;
  flex-direction: column;
  flex-wrap: nowrap;
}

.footer-row {
  display: flex;
}

#footer-ul {
  display: inline-grid;
  grid-auto-flow: row;
  grid-gap: 24px;
  justify-items: center;
  margin: auto;
}

/* FOOTER style END */

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

.register-input-field {
  display: flex;
  margin: auto;
  padding: 8px;
  border-color: var(--button-color);
  border-radius: 5px;
  justify-content: space-evenly;
}
.register-input-field:hover,
.register-input-field:focus {
  border-color: var(--button-hover);
}

#logo {
  height: 40px;
  width: auto;
}
</style>

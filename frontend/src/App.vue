<template>
  <nav class="navbar sticky-top navbar-expand-sm">
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

      <!--<a class="navbar-brand" href="/">
        <img src="../src/assets/logo.png" alt="Logo" style="width: 50px"
      /></a>-->
      <div
        id="collapsedNav"
        class="collapse navbar-collapse justify-content-center"
      >
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="login"
                ><font-awesome-icon icon="right-to-bracket"
              /></router-link>
              <p>Login</p>
            </a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="#"
                ><font-awesome-icon icon="user-secret"
              /></router-link>
              <p>User</p>
            </a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="#"
                ><font-awesome-icon icon="sign-out-alt"
              /></router-link>
              <p>Logout</p>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/createItem"
                ><font-awesome-icon icon="plus"
              /></router-link>
              <p>Create Item</p>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/"
                ><font-awesome-icon icon="house"
              /></router-link>
              <p>Home</p>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/about"
                ><font-awesome-icon icon="info"
              /></router-link>
              <p>About</p>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
    <router-view />
  </div>
  <div class="longDiv"></div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.longDiv {
  height: 2000px;
}

/* Navbar styling */
li a {
  text-decoration: none;
}

li a.router-link-exact-active {
  color: gray;
}

li a.router-link-exact-active:hover {
  color: #ffc370;
}

li a:hover {
  color: lightgray;
}

.nav-item {
  font-size: x-large;
}

.navbar {
  /*background-color: #343a40;*/
  background-color: white;
}

/* Navbar styling end*/

/* Make navbar slide from left on small screens*/
@media (max-width: 479px) {
  .navbar-collapse {
    position: fixed;
    top: 40px;
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
</style>

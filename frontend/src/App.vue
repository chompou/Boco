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
      <div
        id="collapsedNav"
        class="collapse navbar-collapse justify-content-center bg-light"
      >
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="login" v-if="!$store.state.loggedIn"
                ><font-awesome-icon icon="right-to-bracket"
              /></router-link>
              <button class="log-out-button" v-if="$store.state.loggedIn">
                <font-awesome-icon icon="sign-out-alt" />
              </button>
            </a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/user"
                ><font-awesome-icon icon="user"
              /></router-link>
            </a>
          </li>
        </ul>

        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/create"
                ><font-awesome-icon icon="plus"
              /></router-link>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/"
                ><font-awesome-icon icon="house"
              /></router-link>
            </a>
          </li>
        </ul>
        <ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/info"
                ><font-awesome-icon icon="info"
              /></router-link>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
    <router-view />
    <div class="longDiv"></div>
  </div>
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

button {
  background: none;
  color: inherit;
  border: none;
  padding: 0;
  font: inherit;
  cursor: pointer;
  outline: inherit;
}
</style>

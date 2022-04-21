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
              <router-link to="#"
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
        <!--<ul id="nav" class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/">Home</router-link>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link">
              <router-link to="/about">About</router-link>
            </a>
          </li>
        </ul>-->
      </div>
    </div>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link to="/createItem">CreateItem</router-link>
  </nav>
  <router-view />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>

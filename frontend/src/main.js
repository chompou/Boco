import "bootstrap/dist/css/bootstrap.css";
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faRightToBracket,
  faUserSecret,
  faSignOutAlt,
  faPlus,
  faHouse,
  faInfo,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(
  faRightToBracket,
  faUserSecret,
  faSignOutAlt,
  faPlus,
  faHouse,
  faInfo
);

createApp(App)
  .component("font-awesome-icon", FontAwesomeIcon)
  .use(store)
  .use(router)
  .mount("#app");

import "bootstrap/dist/js/bootstrap.js";

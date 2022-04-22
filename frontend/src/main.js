import "bootstrap/dist/css/bootstrap.css";
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faRightToBracket,
  faUser,
  faSignOutAlt,
  faPlus,
  faHouse,
  faInfo,
  faCar,
  faWrench,
  faUmbrella,
  faLemon,
  faChildren,
  faToiletPaper,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(
  faRightToBracket,
  faUser,
  faSignOutAlt,
  faPlus,
  faHouse,
  faInfo,
  faCar,
  faWrench,
  faUmbrella,
  faLemon,
  faChildren,
  faToiletPaper
);

createApp(App)
  .component("font-awesome-icon", FontAwesomeIcon)
  .use(store)
  .use(router)
  .mount("#app");

import "bootstrap/dist/js/bootstrap.js";

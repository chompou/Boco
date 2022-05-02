import "bootstrap/dist/css/bootstrap.css";
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import OpenLayersMap from "vue3-openlayers";
import "vue3-openlayers/dist/vue3-openlayers.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
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
  faBell,
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
  faToiletPaper,
  faBell
);

const app = createApp(App);

app.component("font-awesome-icon", FontAwesomeIcon);
app.use(store);
app.use(Toast, {
  transition: "Vue-Toastification__bounce",
  maxToasts: 20,
  newestOnTop: true,
});
app.use(router);
app.use(OpenLayersMap);
app.mount("#app");

import "bootstrap/dist/js/bootstrap.js";

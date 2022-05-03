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
import VueGoogleMaps from "@fawmi/vue-google-maps";
import {
  faRightToBracket,
  faUser,
  faSignOutAlt,
  faPlus,
  faHouse,
  faInfo,
  faBasketball,
  faCampground,
  faGraduationCap,
  faCouch,
  faBolt,
  faShirt,
  faGuitar,
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
  faBasketball,
  faCampground,
  faGraduationCap,
  faBolt,
  faCouch,
  faShirt,
  faGuitar,
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
app.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyDqtG0SjobFXqse13BVXAHPZPMQ87utTd4",
  },
});
app.mount("#app");

import "bootstrap/dist/js/bootstrap.js";

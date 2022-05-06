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
  faAnglesLeft,
  faUser,
  faSignOutAlt,
  faPlus,
  faInfo,
  faShirt,
  faUmbrella,
  faLemon,
  faChildren,
  faToiletPaper,
  faBell,
  faCircleCheck,
  /**ICONS main categories*/
  faWrench,
  faPlug,
  faPalette,
  faGuitar,
  faCar,
  faBasketball,
  faCampground,
  faCouch,
  faGraduationCap,
  faHouse,
  faArrowDown,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(
  faArrowDown,
  faRightToBracket,
  faAnglesLeft,
  faUser,
  faSignOutAlt,
  faPlus,
  faInfo,
  faShirt,
  faUmbrella,
  faLemon,
  faChildren,
  faToiletPaper,
  faBell,
  faCircleCheck,
  /**ICONS main categories*/
  faWrench,
  faPlug,
  faPalette,
  faGuitar,
  faCar,
  faBasketball,
  faCampground,
  faCouch,
  faGraduationCap,
  faHouse
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

const clickOutside = {
  beforeMount: (el, binding) => {
    el.clickOutsideEvent = (event) => {
      // here I check that click was outside the el and his children
      if (!(el === event.target || el.contains(event.target))) {
        // and if it did, call method provided in attribute value
        binding.value();
      }
    };
    document.addEventListener("click", el.clickOutsideEvent);
  },
  unmounted: (el) => {
    document.removeEventListener("click", el.clickOutsideEvent);
  },
};

app.directive("click-outside", clickOutside);
import "bootstrap/dist/js/bootstrap.js";

import { createRouter, createWebHistory } from "vue-router";
import ItemCreationPage from "@/views/Items/ItemCreationPage";
import FrontPage from "../views/FrontPageView.vue";
import About from "../views/AboutView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: FrontPage,
  },
  {
    path: "/about",
    name: "about",
    component: About,
  },
  {
    path: "/createItem",
    name: "createItem",
    component: ItemCreationPage,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

import { createRouter, createWebHistory } from "vue-router";
import ItemCreationPage from "@/views/Items/ItemCreationPage";
import FrontPage from "../views/FrontPageView.vue";
import ItemPage from "@/views/Items/ItemPage";

const routes = [
  {
    path: "/",
    name: "home",
    component: FrontPage,
  },
  {
    path: "/about",
    name: "about",
    component: ItemPage,
  },
  {
    path: "/createItem",
    name: "createItem",
    component: ItemCreationPage,
  },
  {
    path: "/itemPage",
    name: "itemPage",
    component: ItemPage,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

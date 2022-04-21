import { createRouter, createWebHistory } from "vue-router";
import FrontPage from "../views/FrontPageView.vue";
import LoginView from "@/views/LoginView";
import ItemCreationPage from "@/views/Items/ItemCreationPage";

const routes = [
  {
    path: "/",
    name: "home",
    component: FrontPage,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/register",
    name: "register",
  },
  {
    path: "/profile/:id",
    name: "profile",
    props: true,
  },
  {
    path: "/my",
    children: [
      {
        path: "items",
        name: "myItems",
      },
      {
        path: "leases",
        name: "myLeases",
      },
      {
        path: "reviews",
        name: "myReviews",
      },
      {
        path: "settings",
        name: "mySettings",
      },
    ],
  },
  {
    path: "/createItem",
    name: "createItem",
    component: ItemCreationPage,
  },
  {
    path: "/edit",
    name: "editItem",
  },
  {
    path: "/items",
    name: "items",
  },
  {
    path: "/items/:id",
    name: "item",
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

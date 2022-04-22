import { createRouter, createWebHistory } from "vue-router";
import FrontPage from "../views/FrontPageView.vue";
import LoginView from "@/views/LoginView";
import ItemCreationPage from "@/views/Items/ItemCreationPage";
import RegisterView from "@/views/RegisterView";
import ItemPage from "@/views/Items/ItemPage";
import NotFoundView from "@/views/NotFoundView";

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
    component: RegisterView,
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
    path: "/create",
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
    component: ItemPage,
  },
  {
    path: "/items/:id",
    name: "item",
    props: true,
  },
  {
    path: "/:catchAll(.*)",
    name: "NotFound",
    component: NotFoundView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

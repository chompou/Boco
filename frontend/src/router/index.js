import { createRouter, createWebHistory } from "vue-router";
import store from "@/store/index.js";
import FrontPage from "../views/FrontPageView.vue";
import LoginView from "@/views/LoginView";
import ItemCreationPage from "@/views/Items/ItemCreationPage";
import RegisterView from "@/views/RegisterView";
import ItemPage from "@/views/Items/ItemPage";
import NotFoundView from "@/views/NotFoundView";
import MyProfileView from "@/views/my/MyProfileView";
import MyItemsView from "@/views/my/MyItemsView";
import MyLeasesView from "@/views/my/MyLeasesView";
import MyReviewView from "@/views/my/MyReviewView";
import MySettingsView from "@/views/my/MySettingsView";
import itemEditPage from "@/views/Items/ItemEditPage";

const routerGuard = {
  beforeEnter: (to, from) => {
    console.log(from.name);
    if (!store.state.loggedIn && to.name !== "login") {
      return { name: "login" };
    }
  },
};

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
    ...routerGuard,
    path: "/my",
    component: MyProfileView,
    children: [
      {
        path: "items",
        name: "myItems",
        component: MyItemsView,
      },
      {
        path: "leases",
        name: "myLeases",
        component: MyLeasesView,
      },
      {
        path: "reviews",
        name: "myReviews",
        component: MyReviewView,
      },
      {
        path: "settings",
        name: "mySettings",
        component: MySettingsView,
      },
    ],
  },
  {
    ...routerGuard,
    path: "/create",
    name: "createItem",
    component: ItemCreationPage,
  },
  {
    ...routerGuard,
    path: "/edit",
    name: "editItem",
    component: itemEditPage,
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

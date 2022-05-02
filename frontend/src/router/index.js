import apiService from "@/services/apiService";
import storageService from "@/services/storageService";
import store from "@/store/index.js";
import forgottenPwdView from "@/views/ForgottenPwdView";
import ItemCreationPage from "@/views/Items/ItemCreationPage";
import itemEditPage from "@/views/Items/ItemEditPage";
import ItemPage from "@/views/Items/ItemPage";
import itemsPage from "@/views/Items/ItemsPage";
import LoginView from "@/views/LoginView";
import MyItemsView from "@/views/my/MyItemsView";
import MyLeasesView from "@/views/my/MyLeasesView";
import MyProfileView from "@/views/my/MyProfileView";
import MyReviewView from "@/views/my/MyReviewView";
import MySettingsView from "@/views/my/MySettingsView";
import NotFoundView from "@/views/NotFoundView";
import RegisterView from "@/views/RegisterView";
import SupportFormView from "@/views/SupportFormView";
import { createRouter, createWebHistory } from "vue-router";
import FrontPage from "../views/FrontPageView.vue";

// const routerGuard = {
// beforeEnter: (to, from) => {
//   console.log(from.name);
//   if (!store.state.loggedIn && to.name !== "login") {
//     return { name: "login" };
//   }
// },
// };

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
    path: "/forgottenPwd",
    name: "forgottenPwd",
    component: forgottenPwdView,
  },
  {
    path: "/support",
    name: "support",
    component: SupportFormView,
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
    component: MyProfileView,
  },
  {
    path: "/my",
    name: "my",
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
    path: "/create",
    name: "createItem",
    component: ItemCreationPage,
  },
  {
    path: "/edit/:id",
    name: "editItem",
    props: true,
    component: itemEditPage,
  },
  {
    path: "/items",
    name: "items",
    component: itemsPage,
  },
  {
    path: "/items/:id",
    name: "item",
    props: true,
    component: ItemPage,
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

router.beforeEach(async function (to) {
  if (!store.state.loggedIn && storageService.getToken() != null) {
    try {
      let response = await apiService.getMyProfile();
      store.state.loggedIn = true;
      store.state.loggedInUser = response.data.id;
      store.dispatch("UPDATE_USERNAME", response.data.displayName);
    } catch (error) {
      console.log(error);
    }
  }

  if (!store.state.loggedIn) {
    switch (to.path) {
      case "/my/items":
      case "/my/leases":
      case "/my/reviews":
      case "/my/settings":
      case "/create":
      case "/edit":
        return { name: "login" };
      default:
    }
  } else {
    if (to.path == "/login") {
      return { name: "home" };
    }
  }
});

export default router;

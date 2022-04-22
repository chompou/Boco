import { createRouter, createWebHistory } from "vue-router";
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
import forgottenPwdView from "@/views/ForgottenPwdView";

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
  {
    path: "/item",
    name: "item",
    component: ItemPage,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

/**
router.beforeEach(async (to, from) => {
  const canUserAccess = this.$store.state.loggedIn;
  const canAccess = await canUserAccess(to);
  if (!canAccess) return "/login";
});

router.beforeResolve(async to => {
  if (to.meta.requiresCamera)
})*/

export default router;

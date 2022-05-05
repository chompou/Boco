import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: false,
    loggedInUser: null,
    loggedInDisplayName: "",
    countNotifications: 0,
    unreadNotification: [],
  },
  getters: {
    getDisplayName: (state) => {
      return state.loggedInDisplayName;
    },
    getUnreadNotifications: (state) => {
      return state.unreadNotification;
    },
  },
  mutations: {
    UPDATE_COUNT_NOTIFICATION(state, value) {
      state.countNotifications = value;
    },
    ADD_NOTIFICATION(state, data) {
      state.unreadNotification.push(data);
    },
    UPDATE_NOTIFICATIONS(state, data) {
      state.unreadNotification = data;
    },
    RESET_COUNT_NOTIFICATION(state) {
      state.countNotifications = 0;
    },
    CLEAR_NOTIFICATIONS(state) {
      state.unreadNotification.length = 0;
    },
    ADD_USERNAME(state, username) {
      state.loggedInDisplayName = username;
    },
  },
  actions: {
    UPDATE_COUNT_NOTIFICATION({ commit }, value) {
      commit("UPDATE_COUNT_NOTIFICATION", value);
    },
    ADD_NOTIFICATION({ commit }, data) {
      commit("ADD_NOTIFICATION", data);
    },
    UPDATE_NOTIFICATIONS({ commit }, data) {
      commit("UPDATE_NOTIFICATIONS", data);
    },
    RESET_COUNT_NOTIFICATION({ commit }) {
      commit("RESET_COUNT_NOTIFICATION");
    },
    CLEAR_NOTIFICATIONS({ commit }) {
      commit("CLEAR_NOTIFICATIONS");
    },
    UPDATE_USERNAME({ commit }, username) {
      commit("ADD_USERNAME", username);
    },
  },
  modules: {},
});

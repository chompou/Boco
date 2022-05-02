import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: false,
    loggedInUser: null,
    loggedInDisplayName: "",
    countNotifications: 0,
  },
  getters: {
    getDisplayName: (state) => {
      return state.loggedInDisplayName;
    },
  },
  mutations: {
    UPDATE_COUNT_NOTIFICATION(state) {
      state.countNotifications++;
    },
    RESET_NOTIFICATION(state) {
      state.countNotifications = 0;
    },
    ADD_USERNAME(state, username) {
      state.loggedInDisplayName = username;
    },
  },
  actions: {
    UPDATE_COUNT_NOTIFICATION({ commit }) {
      commit("UPDATE_COUNT_NOTIFICATION");
    },
    UPDATE_USERNAME({ commit }, username) {
      commit("ADD_USERNAME", username);
    },
    RESET_NOTIFICATION({ commit }) {
      commit("RESET_NOTIFICATION");
    },
  },
  modules: {},
});

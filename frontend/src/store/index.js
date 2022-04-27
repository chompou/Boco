import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: true,
    loggedInUser: null,
    countNotifications: 0,
  },
  getters: {},
  mutations: {
    UPDATE_COUNT_NOTIFICATION(state) {
      state.countNotifications++;
    },
  },
  actions: {
    UPDATE_COUNT_NOTIFICATION({ commit }) {
      commit("UPDATE_COUNT_NOTIFICATION");
    },
  },
  modules: {},
});

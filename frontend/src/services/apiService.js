import axios from "axios";
import storageService from "./storageService";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 1000,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

axios.interceptors.request.use((config) => {
  config.headers.Authorization = storageService.getToken();
});

export default {
  getItem(itemId) {
    return apiClient.get("/listing/" + itemId);
  },

  getItems(filters, page, perPage) {
    return apiClient.get("/listing", {
      params: { ...filters, page: page, perPage: perPage },
    });
  },

  getReviews(filters, page, perPage) {
    return apiClient.get("/reviews", {
      params: { ...filters, page: page, perPage: perPage },
    });
  },

  getProfile(profileId) {
    return apiClient.get("/profile/" + profileId);
  },

  createProfile(profile) {
    return apiClient.post("/profile", profile);
  },

  login(username, password) {
    return apiClient.post("/login", { username: username, password: password });
  },

  getMyProfile() {
    return apiClient.get("/my/profile");
  },
};

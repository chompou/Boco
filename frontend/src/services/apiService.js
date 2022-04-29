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

apiClient.interceptors.request.use((config) => {
  let token = storageService.getToken();
  if (token != null) config.headers.Authorization = "Bearer " + token;
  return config;
});

export default {
  getItem(itemId) {
    return apiClient.get("/listing/" + itemId);
  },

  createItem(item) {
    return apiClient.post("/my/listing", item);
  },

  updateItem(item) {
    return apiClient.put("my/listing", item);
  },

  getItems(filters, page, perPage) {
    return apiClient.get("/listing", {
      params: { ...filters, page: page, perPage: perPage },
    });
  },

  getReviews(filters, page, perPage) {
    return apiClient.get("/review", {
      params: { ...filters, page: page, perPage: perPage },
    });
  },

  getCategories() {
    return apiClient.get("/category");
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

  createLease(lease) {
    return apiClient.post("/my/lease", lease);
  },
};

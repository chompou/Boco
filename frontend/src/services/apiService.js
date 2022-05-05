import axios from "axios";
import storageService from "./storageService";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 1000,
  headers: {
    Accept: "application/json",
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
    return apiClient.put("/my/listing", item);
  },

  deleteItem(listingId) {
    return apiClient.delete("my/listing/" + listingId.listingId, listingId);
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

  updateMyProfile(profile) {
    return apiClient.put("my/profile", profile);
  },

  createLease(lease) {
    return apiClient.post("/my/lease", lease);
  },

  getMyLeases(owned) {
    return apiClient.get("/my/lease", { params: { is_owner: owned } });
  },

  updateMyLease(lease) {
    return apiClient.put("/my/lease", lease);
  },

  deleteMyLease(leaseId) {
    return apiClient.delete("my/lease/" + leaseId);
  },

  getNotifications() {
    return apiClient.get("/my/notifications");
  },
  removeNotificationAfterRead(notification) {
    return apiClient.put("my/notifications", { toBeRead: [notification] });
  },
  newPassword(email, generatedCode, passwordHash) {
    console.log(email + generatedCode + passwordHash);
    return apiClient.put("/forgot-password/change/" + email, {
      generatedCode,
      passwordHash,
    });
  },
  sendEmail(email) {
    return apiClient.get("/forgot-password/" + email);
  },
};

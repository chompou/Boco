import axios from "axios";
import storageService from "./storageService";

const apiClient = axios.create({
  baseURL: "http://localhost:8888",
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
  getProfile(profileID) {
    return apiClient.get("/profile/" + profileID);
  },

  login(username, password) {
    return apiClient.post("/login", { username: username, password: password });
  },
};

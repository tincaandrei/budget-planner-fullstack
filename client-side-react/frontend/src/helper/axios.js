import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "", // will be overridden dynamically per request
  headers: {
    post: {
      "Content-Type": "application/json",
    },
  },
});

// Add interceptor to modify baseURL before request is sent
axiosInstance.interceptors.request.use((config) => {
  if (config.url.startsWith("/login")) {
    config.baseURL = "http://localhost:8082";
  } else {
    config.baseURL = "http://localhost:8080";
  }
  return config;
});

export default axiosInstance;

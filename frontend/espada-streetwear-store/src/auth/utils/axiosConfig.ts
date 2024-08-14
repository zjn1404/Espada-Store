import axios from "axios";
import { refresh, parseJwt } from "./auth"

axios.interceptors.request.use(
  async (config) => {
    let accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      const tokenExpiration = parseJwt(accessToken).exp * 1000;
      const now = new Date().getTime();
      if (now >= tokenExpiration - 10 * 60 * 1000) {
        accessToken = await refresh();
      }
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
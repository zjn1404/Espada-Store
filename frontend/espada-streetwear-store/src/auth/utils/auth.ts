import axios from "axios";
import { jwtDecode, JwtPayload } from "jwt-decode";

interface CustomJwtPayload extends JwtPayload {
  scope: string;
}

export const refresh = async (): Promise<string> => {
  try {
    const oldRefreshToken = localStorage.getItem("refreshToken");
    const response = await axios.post("http://localhost:8080/api/auth/refresh", {
      token: oldRefreshToken,
    });
    const tokens = response.data.result;
    localStorage.setItem("accessToken", tokens.accessToken);
    localStorage.setItem("refreshToken", tokens.refreshToken);
    return tokens.accessToken;
  } catch (err: any) {
    if (err.response && err.response.data.code === 1008) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      window.location.href = "/sign-in";
    }
    throw err;
  }
};

export const scheduleTokenRefresh = (expiresIn: number) => {
  const refreshTime = expiresIn - 10 * 60 * 1000; // 20 minutes before expiry
  setTimeout(() => refresh(), refreshTime);
};

export const parseJwt = (token: string): any => {
  try {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error("Failed to parse JWT", e);
    return null;
  }
};

export const decodeJwt = (token: string): CustomJwtPayload | null => {
  try {
    const decoded = jwtDecode<CustomJwtPayload>(token);
    return decoded;
  } catch (error) {
    console.error("Failed to decode JWT", error);
    return null;
  }
};

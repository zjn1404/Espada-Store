import axios from "axios";

export const refreshToken = async (): Promise<string> => {
  try {
    const refreshToken = localStorage.getItem("refreshToken");
    const response = await axios.post("http://localhost:8080/api/auth/refresh", {
      refreshToken,
    });
    const { accessToken, refreshToken: newRefreshToken } = response.data;
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", newRefreshToken);
    return accessToken;
  } catch (err: any) {
    if (err.response && err.response.data.code === 1008) {
      // Handle refresh token expired case
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      // Redirect to login page
      window.location.href = "/login";
    }
    throw err;
  }
};

export const scheduleTokenRefresh = (expiresIn: number) => {
  const refreshTime = expiresIn - 10 * 60 * 1000; // 10 minutes before expiry
  setTimeout(refreshToken, refreshTime);
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
import React, { useState } from "react";
import axios from "axios";
import "./sign-in.css";
import { useHistory} from "react-router-dom";

export const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const history = useHistory();

  const handleLogin = async (e: any) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/token",
        {
          username,
          password,
        }
      );
      
      const { accessToken, refreshToken } = response.data.result;
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      setError(null);
      history.push("/");
      window.location.reload()
    } catch (err: any) {
      setError("Invalid credentials");
    }
  };

  return (
    <div className="form-signin w-100 m-auto">
      <form className="mt-5" onSubmit={handleLogin}>
        <h1 className="h3 mb-3 fw-normal text-body-secondary">Sign in</h1>

        <div className="form-floating text-body-secondary mb-3">
          <input
            type="text"
            className="form-control"
            id="username"
            placeholder="Username"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <label htmlFor="username">Username</label>
        </div>

        <div className="form-floating text-body-secondary">
          <input
            type="password"
            className="form-control"
            id="password"
            placeholder="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <label htmlFor="password">Password</label>
        </div>

        <div className="form-check text-start my-3">
          <input
            className="form-check-input"
            type="checkbox"
            value="remember-me"
            id="flexCheckDefault"
          />
          <label
            className="form-check-label text-body-secondary"
            htmlFor="flexCheckDefault"
          >
            Remember me
          </label>
        </div>
        <button
          className="btn btn-primary w-100 py-2 btn-secondary mb-2"
          type="submit"
        >
          Sign in
        </button>
        <div className="text-center mb-5">
          <a
            href="sign-up.jsp"
            className="text-body-secondary"
            style={{ textDecoration: "none" }}
          >
            Sign up a new account
          </a>
        </div>
      </form>
      {error && <p>{error}</p>}
    </div>
  );
};

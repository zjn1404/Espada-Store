import axios from "axios";
import React, { useState } from "react";
import notSignedInImg from "../img/error/have-not-signed-in.jpg"

export const ChangePasswordPage = () => {
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [code, setCode] = useState(0);
  const [msg, SetMsg] = useState("");
  const [accessToken, setAccessToken] = useState(
    localStorage.getItem("accessToken") ?? ""
  );

  const validatePassword = () => {
    const lowerCaseLetters = /[a-z]/g;
    const upperCaseLetters = /[A-Z]/g;
    const numbers = /[0-9]/g;
    let error = "";

    var hasError = false;

    if (newPassword.length < 8) {
      error = "Password should be minimum 8 characters!";
      hasError = true;
    }

    if (!newPassword.match(lowerCaseLetters)) {
      if (hasError) {
        error += "<br>";
      }
      error += "Password should contain lowercase letter!";
      hasError = true;
    }

    if (!newPassword.match(upperCaseLetters)) {
      if (hasError) {
        error += "<br>";
      }
      error += "Password should contain uppercase letter!";
      hasError = true;
    }

    if (!newPassword.match(numbers)) {
      if (hasError) {
        error += "<br>";
      }
      error += "Password should contain number!";
      hasError = true;
    }

    document.getElementById("password-error")!.innerHTML = error;
  };

  const handleChangePassword = async (e: any) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/change-password",
        {
          oldPassword,
          newPassword,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      setCode(response.data.code);
      SetMsg(response.data.message);
    } catch (err: any) {
      setError(err.response.data.message);
    }
  };

  return (
    <div>
      {accessToken !== "" && (
        <div className="form-signin w-100 m-auto">
          <form className="mt-5" onSubmit={handleChangePassword}>
            <h1 className="h3 mb-3 fw-normal text-body-secondary">
              Change Password
            </h1>

            <div className="form-floating text-body-secondary mb-3">
              <input
                type="password"
                className="form-control"
                id="oldPassword"
                placeholder="Password"
                name="oldPassword"
                value={oldPassword}
                onChange={(e) => setOldPassword(e.target.value)}
                required
              />
              <label htmlFor="oldPassword">Password</label>
            </div>

            <div className="form-floating text-body-secondary">
              <input
                type="password"
                className="form-control"
                id="newPassword"
                placeholder="New Password"
                name="newPassword"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                onKeyUp={validatePassword}
                required
              />
              <label htmlFor="newPassword">New Password</label>
            </div>

            <button
              className="btn btn-primary w-100 py-2 btn-secondary mb-2"
              type="submit"
            >
              Change password
            </button>
          </form>
          {error && (
            <p className="text-center" style={{ color: "red" }}>
              {error}
            </p>
          )}
          {code === 1000 && <p className="text-center text-success">{msg}</p>}
        </div>
      )}
      {accessToken === "" &&

        <img style={{width: "100%"}} src={`${notSignedInImg}`}/>
      
      }
    </div>
  );
};

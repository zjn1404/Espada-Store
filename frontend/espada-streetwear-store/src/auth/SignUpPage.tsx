import React, { useState } from "react";
import axios, { AxiosResponse } from "axios";
import "./sign-in.css";
import { useHistory, useParams } from "react-router-dom";

export const SignUpPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [gender, setGender] = useState("Male");
  const [address, setAddress] = useState("");
  const [deliveryAddress, setDeliveryAddress] = useState("");
  const [dob, setDob] = useState("");
  const [registerToGetMail, setRegisterToGetMail] = useState(false);
  const [termsAccepted, setTermsAccepted] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const history = useHistory();

  const role = window.location.pathname.split("/")[2].toUpperCase();
  let response: AxiosResponse<any, any>;

  console.log(role);

  const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      response = await axios.post("http://localhost:8080/api/user", {
        username,
        password,
        email,
        phoneNumber,
        firstName,
        lastName,
        roles: [role],
      });

      if (role === "USER") {
        await axios.post("http://localhost:8080/api/customer-info", {
          username,
          gender,
          address,
          deliveryAddress,
          dob,
          registerToGetMail,
        });
      }

      setError(null);
      history.push("/sign-up-success");

    } catch (err: any) {
      setError(err.response.data.message);
    }
  };

  const validatePassword = () => {
    const lowerCaseLetters = /[a-z]/g;
    const upperCaseLetters = /[A-Z]/g;
    const numbers = /[0-9]/g;
    let error = "";

    var hasError = false;

    if (password.length < 8) {
      error = "Password should be minimum 8 characters!";
      hasError = true;
    }

    if (!password.match(lowerCaseLetters)) {
      if (hasError) {
        error += "<br>";
      }
      error += "Password should contain lowercase letter!";
      hasError = true;
    }

    if (!password.match(upperCaseLetters)) {
      if (hasError) {
        error += "<br>";
      }
      error += "Password should contain uppercase letter!";
      hasError = true;
    }

    if (!password.match(numbers)) {
      if (hasError) {
        error += "<br>";
      }
      error += "Password should contain number!";
      hasError = true;
    }

    document.getElementById("password-error")!.innerHTML = error;
  };

  const validateRePassword = () => {
    const rePassword = (
      document.getElementById("re-password") as HTMLInputElement
    ).value;

    if (password !== rePassword) {
      document.getElementById("re-password-error")!.innerHTML =
        "Passwords do not match!";
    } else {
      document.getElementById("re-password-error")!.innerHTML = "";
    }
  };

  const checkAcceptTerms = () => {
    setTermsAccepted(!termsAccepted);
  };

  return (
    <div className="w-100 m-auto">
      <div className="container w-100 text-body-secondary">
        <h1 className="mt-3 mb-3 text-center fw-bold text-body-secondary">
          SIGN UP
        </h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handleRegister}>
          <input type="hidden" name="action" value="sign-up" />
          <div className="row">
            <div className="col-sm-6">
              <h3>Account</h3>
              <div className="mb-3">
                <label htmlFor="username" className="form-label">
                  Username<span className="require-area">*</span>
                </label>
                <input
                  type="text"
                  className="form-control text-body-secondary"
                  id="username"
                  name="username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Password<span className="require-area">*</span>
                </label>
                <input
                  type="password"
                  className="form-control text-body-secondary"
                  id="password"
                  name="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  onKeyUp={validatePassword}
                  required
                />
                <p id="password-error" style={{ color: "red" }}></p>
              </div>
              <div className="mb-3">
                <label htmlFor="re-password" className="form-label">
                  Confirm password<span className="require-area">*</span>
                  <span id="re-password-error" style={{ color: "red" }}></span>
                </label>
                <input
                  type="password"
                  className="form-control text-body-secondary"
                  id="re-password"
                  name="rePassword"
                  onKeyUp={validateRePassword}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="phone" className="form-label">
                  Phone
                </label>
                <input
                  type="text"
                  className="form-control text-body-secondary"
                  id="phone"
                  name="phone"
                  value={phoneNumber}
                  onChange={(e) => setPhoneNumber(e.target.value)}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="email" className="form-label">
                  Email<span className="require-area">*</span>
                </label>
                <input
                  type="email"
                  className="form-control text-body-secondary"
                  id="email"
                  name="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
            </div>
            <div className="col-sm-6">
              <h3>Information</h3>
              <div className="mb-3">
                <label htmlFor="firstName" className="form-label">
                  First Name
                </label>
                <input
                  type="text"
                  className="form-control text-body-secondary"
                  id="firstName"
                  name="firstName"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="lastName" className="form-label">
                  Last Name
                </label>
                <input
                  type="text"
                  className="form-control text-body-secondary"
                  id="lastName"
                  name="lastName"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                />
              </div>
              {role === "USER" && (
                <>
                  <div className="mb-3">
                    <label htmlFor="customerAddress" className="form-label">
                      Customer Address
                    </label>
                    <input
                      type="text"
                      className="form-control text-body-secondary"
                      id="customerAddress"
                      name="customerAddress"
                      value={address}
                      onChange={(e) => setAddress(e.target.value)}
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="deliveryAddress" className="form-label">
                      Delivery Address
                    </label>
                    <input
                      type="text"
                      className="form-control text-body-secondary"
                      id="deliveryAddress"
                      name="deliveryAddress"
                      value={deliveryAddress}
                      onChange={(e) => setDeliveryAddress(e.target.value)}
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="gender" className="form-label">
                      Gender
                    </label>
                    <select
                      className="form-control text-body-secondary"
                      id="gender"
                      name="gender"
                      value={gender}
                      onChange={(e) => setGender(e.target.value)}
                    >
                      <option value="Male">Male</option>
                      <option value="Female">Female</option>
                      <option value="Other">Other</option>
                    </select>
                  </div>
                  <div className="mb-3">
                    <label htmlFor="dob" className="form-label">
                      Date of birth
                    </label>
                    <input
                      type="date"
                      className="form-control text-body-secondary"
                      id="dob"
                      name="dob"
                      value={dob}
                      onChange={(e) => setDob(e.target.value)}
                    />
                  </div>
                </>
              )}
              <div className="mb-3 form-check">
                {role === "USER" && (
                  <>
                    <div className="mb-3 form-check">
                      <input
                        type="checkbox"
                        className="form-check-input"
                        id="term-checkbox"
                        name="readTerm"
                        checked={termsAccepted}
                        onChange={checkAcceptTerms}
                      />
                      <label
                        className="form-check-label"
                        htmlFor="term-checkbox"
                      >
                        I accept all terms & conditions.
                        <span className="require-area">*</span>
                      </label>
                    </div>

                    <div className="mb-3 form-check">
                      <input
                        type="checkbox"
                        className="form-check-input"
                        id="get-mail-checkbox"
                        name="getMail"
                        checked={registerToGetMail}
                        onChange={(e) => setRegisterToGetMail(e.target.checked)}
                      />
                      <label
                        className="form-check-label"
                        htmlFor="get-mail-checkbox"
                      >
                        I agree to receive general emails and product offers.
                      </label>
                    </div>
                  </>
                )}
              </div>
              <input
                id="register-btn"
                type="submit"
                className="btn btn-secondary form-control"
                style={{
                  visibility:
                    termsAccepted || role == "ADMIN" ? "visible" : "hidden",
                }}
                value="Register"
                name="register"
              />
            </div>
          </div>

          <hr />
        </form>
      </div>
    </div>
  );
};

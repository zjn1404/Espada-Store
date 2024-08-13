import React, { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import "./sign-in.css";
import { decodeJwt } from "./utils/auth";

export const UpdateInformationPage = () => {
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [gender, setGender] = useState("Male");
  const [address, setAddress] = useState("");
  const [deliveryAddress, setDeliveryAddress] = useState("");
  const [dob, setDob] = useState("");
  const [registerToGetMail, setRegisterToGetMail] = useState(false);
  const [error, setError] = useState<string | null>(null);

  let response: AxiosResponse<any, any>;
  const role = decodeJwt(localStorage.getItem("accessToken")!)?.scope.includes(
    "ADMIN"
  )
    ? "ADMIN"
    : "USER";

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const userResponse = await axios.get("http://localhost:8080/api/user/my-info", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        });

        const userData = userResponse.data.result;
        setEmail(userData.email);
        setPhoneNumber(userData.phoneNumber);
        setFirstName(userData.firstName);
        setLastName(userData.lastName);

        if (role === "USER") {
          const customerResponse = await axios.get(
            "http://localhost:8080/api/customer-info",
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
              },
            }
          );

          const customerData = customerResponse.data.result;
          setGender(customerData.gender);
          setAddress(customerData.address);
          setDeliveryAddress(customerData.deliveryAddress);
          setDob(customerData.dob.substring(0, 10));
          setRegisterToGetMail(customerData.registerToGetMail);
        }

        setError("");
      } catch (err: any) {
        setError("Failed to fetch user data");
      }
    };

    fetchUserData();
  }, [role]);

  const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      response = await axios.put(
        "http://localhost:8080/api/user",
        {
          email,
          phoneNumber,
          firstName,
          lastName,
          roles: [role],
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (role === "USER") {
        await axios.put(
          "http://localhost:8080/api/customer-info",
          {
            gender,
            address,
            deliveryAddress,
            dob,
            registerToGetMail,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
          }
        );
      }

      setError(null);
    } catch (err: any) {
      setError(err.response.data.message);
    }
  };

  return (
    <div className="w-100 m-auto">
      <div className="container w-100 text-body-secondary mb-5">
        <h1 className="mt-3 mb-3 text-center fw-bold text-body-secondary">
          CHANGE INFORMATION
        </h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handleRegister}>
          <div className="row">
            <div className="col-sm-6">
              <h3>Account</h3>
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
              <div className="mb-3 form-check">
                {role === "USER" && (
                  <>
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
                value="Change Information"
                name="change-information"
              />
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
            </div>
          </div>
        </form>
      </div>
      {error === null && (
        <p className="mb-5 text-success text-center">
          Change Information Successfully
        </p>
      )}
      <hr />
    </div>
  );
};

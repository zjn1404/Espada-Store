import React from "react";
import { Link } from "react-router-dom";
import SignUpSuccessImg from "../img/welcome/signUpSuccess.jpg";

export const SignUpSuccessPage = () => {
  return (
    <div className="success-container">
      <img
        src={SignUpSuccessImg}
        alt="Sign Up Success"
        className="background-image"
      />
      <div className="overlay"></div>
      <div className="content">
        <h2>Sign Up Success</h2>
        <p>
          Thank you for signing up. Please check your email to verify your
          account.
        </p>
        <Link to="/home" className="btn btn-outline-light">
          Home
        </Link>
      </div>
    </div>
  );
};

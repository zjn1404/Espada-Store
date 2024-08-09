import axios from "axios";
import React, { useState } from "react";

export const ChangePasswordPage = () => {
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");

  const handleChangePassword = async (e: any) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/change-password",
        {
          oldPassword,
          newPassword,
        }
      );

      console.log(response.data);
    } catch (err: any) {
      console.error(err.response.data.message);
    }
  }

  return (
    <div>
      

    </div>
  );
}
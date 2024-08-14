import axios from "axios";
import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";

export const UpdateOrderPage = () => {
  const location = useLocation();
  const param = (location.state || {}) as {
    orderId: string;
    state: string;
    paymentState: boolean;
    shippingDate: Date;
  };

  const STATE_CHOICES = [
    "DEFAULT_STATE",
    "PREPARING",
    "SHIPPING",
    "TO_CUSTOMER",
    "DELIVERED",
  ];

  const [orderId] = useState(param.orderId);
  const [state, setState] = useState(STATE_CHOICES[0]);
  const [paymentState, setPaymentState] = useState(param.paymentState);
  const [shippingDate, setShippingDate] = useState(param.shippingDate);
  const [err, setError] = useState<string | null>("");
  const [isLoading, setIsLoading] = useState(false);

  const handleUpdateOrder = async () => {
    setIsLoading(true);
    try {
      const response = await axios.put(
        `http://localhost:8080/api/order/${orderId}`,
        {
          state,
          paymentState,
          shippingDate,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (response.data.code !== 1000) {
        setError(response.data.message);
      } else {
        setError(null);
      }
    } catch (error: any) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) {
    return <SpinnerLoading />;
  }

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Update Order</h1>
      {err && <div className="alert alert-danger">{err}</div>}
      <div className="mb-3">
        <label htmlFor="state" className="form-label">
          State:
        </label>
        <select
          id="state"
          className="form-select"
          value={state}
          onChange={(e) => setState(e.target.value)}
        >
          {STATE_CHOICES.map((choice) => (
            <option key={choice} value={choice}>
              {choice}
            </option>
          ))}
        </select>
      </div>
      <div className="mb-3">
        <label htmlFor="paymentState" className="form-label">
          Payment State:
        </label>
        <div className="form-check">
          <input
            type="checkbox"
            id="paymentState"
            className="form-check-input"
            checked={paymentState}
            onChange={(e) =>
              setPaymentState(e.target.checked ? true : false)
            }
          />
          <label htmlFor="paymentState" className="form-check-label">
            Paid
          </label>
        </div>
      </div>
      <div className="mb-3">
        <label htmlFor="shippingDate" className="form-label">
          Shipping Date:
        </label>
        <input
          type="date"
          id="shippingDate"
          className="form-control"
          value={
            shippingDate ? shippingDate.toString().substring(0, 10) : ""
          }
          onChange={(e) => setShippingDate(new Date(e.target.value))}
        />
      </div>
      <button className="btn btn-secondary" onClick={handleUpdateOrder}>
        Update Order
      </button>
      {err === null && (
        <div>
          <p className="mb-3 text-success text-center">
            Order placed successfully
          </p>
          <Link
            className="d-flex justify-content-center"
            style={{ textDecoration: "NONE" }}
            to="/home"
          >
            <p className="text-secondary">Back to Home</p>
          </Link>
        </div>
      )}
      <hr />
    </div>
  );
};

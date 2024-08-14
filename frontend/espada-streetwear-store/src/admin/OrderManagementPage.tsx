import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";

export const OrderManagementPage = () => {
  const [orders, setOrders] = useState([
    {
      id: String,
      deliveryAddress: String,
      phoneNumber: String,
      state: String,
      payment: Number,
      paymentState: Boolean,
      orderingDate: Date,
      shippingDate: Date,
      userId: String,
    },
  ]);
  const [err, setError] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const fetchOrders = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/order", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
      });

      setOrders(response.data.result);
    } catch (error: any) {
      setError(error.response.data.message);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async (orderId: string) => {
    setIsLoading(true);
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/order/${orderId}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (response.data.code === 1000) {
        fetchOrders();
      } else {
        console.error("Failed to delete cart item:", response.data.message);
      }
    } catch (error) {
      console.error("Failed to delete cart item:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  return (
    <div className="container mt-5">
      <h1 className="mb-5">
        <strong>Order</strong>
      </h1>
      {err && <p style={{ color: "red" }}>{err}</p>}
      <div className="row">
        <div className="col-12 mb-3">
          <div className="d-flex border-bottom pb-2">
            <div className="col-md-3 text-secondary">Contact</div>
            <div className="col-md-3 text-secondary">Payment</div>
            <div className="col-md-4 text-secondary">State</div>
            <div className="col-md-2 text-end text-secondary">Manage</div>
          </div>
        </div>
        {orders.length == 0 ? (
          <p>Don't have any order.</p>
        ) : (
          orders.map((item, index) => (
            <div className="col-12 mb-3" key={`${index}-${item.id}`}>
              <div className="d-flex border-bottom pb-2">
                <div className="col-md-3 d-flex align-items-center">
                  <div>
                    <p className="mb-0">
                      Delivery Address: {`${item.deliveryAddress}`}
                    </p>
                    <p className="mb-0">
                      Phone Number: {`${item.phoneNumber}`}
                    </p>
                  </div>
                </div>
                <div className="col-md-3 d-flex align-items-center">
                  <div>
                    <p className="mb-0">Payment: {`${item.payment}`}</p>
                    <p className="mb-0">
                      Payment state: {`${item.paymentState}`}
                    </p>
                  </div>
                </div>
                <div className="col-md-4 d-flex align-items-center">
                  <div>
                    <p className="mb-0">
                      Ordering Date:{" "}
                      {`${item.orderingDate.toString().substring(0, 10)}`}
                    </p>
                    <p className="mb-0">
                      Shipping Date:{" "}
                      {`${
                        item.shippingDate
                          ? item.shippingDate.toString().substring(0, 10)
                          : ""
                      }`}
                    </p>
                    <p className="mb-0">State: {`${item.state}`}</p>
                  </div>
                </div>
                <div className="col-md-2 d-flex justify-content-end">
                  <div>
                    <Link to="/home" className="btn ms-2">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="16"
                        height="16"
                        fill="currentColor"
                        className="bi bi-cart4"
                        viewBox="0 0 16 16"
                      >
                        <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5M3.14 5l.5 2H5V5zM6 5v2h2V5zm3 0v2h2V5zm3 0v2h1.36l.5-2zm1.11 3H12v2h.61zM11 8H9v2h2zM8 8H6v2h2zM5 8H3.89l.5 2H5zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0m9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0" />
                      </svg>
                    </Link>
                    <Link
                      to={{
                        pathname: "/update-order",
                        state: {
                          orderId: item.id,
                          state: item.state,
                          paymentState: item.paymentState,
                          shippingDate: item.shippingDate,
                        },
                      }}
                      className="btn ms-2"
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="16"
                        height="16"
                        fill="currentColor"
                        className="bi bi-pencil-square"
                        viewBox="0 0 16 16"
                      >
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                        <path
                          fill-rule="evenodd"
                          d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"
                        />
                      </svg>
                    </Link>
                    <button
                      className="btn ms-2"
                      onClick={() => handleDelete(item.id.toString())}
                      disabled={isLoading}
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="16"
                        height="16"
                        fill="currentColor"
                        className="bi bi-trash"
                        viewBox="0 0 16 16"
                      >
                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z" />
                        <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z" />
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

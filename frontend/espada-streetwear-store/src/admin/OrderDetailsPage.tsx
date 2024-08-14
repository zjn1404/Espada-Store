import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";

// http://localhost:8080/api/order/order-detail

export const OrderDetailsPage = () => {
  const [orderDetails, setOrderDetails] = useState([
    {
      productId: String,
      size: String,
      quantity: Number,
    },
  ]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const location = useLocation();
  const param = (location.state || {}) as {
    orderId: any;
  };

  const fetchOrderDetails = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:8080/api/order/order-detail/${param.orderId}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      setOrderDetails(response.data.result);
    } catch (error: any) {
      setError(error.response.data.message);
    } finally {
      setIsLoading(false);
    }
  };


  useEffect(() => {
    fetchOrderDetails();
  }, [param.orderId]);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  return (
    <div className="container mt-5">
      <h1 className="mb-5">
        <strong>{param.orderId} Order Details</strong>
      </h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div className="row">
        <div className="col-12 mb-3">
          <div className="d-flex border-bottom pb-2">
            <div className="col-md-6 text-secondary">Product Id</div>
            <div className="col-md-3 text-secondary">Size</div>
            <div className="col-md-3 text-secondary">Quantity</div>
          </div>
        </div>
        {orderDetails.length == 0 ? (
          <p>Don't have any order details.</p>
        ) : (
          orderDetails.map((item, index) => (
            <div className="col-12 mb-3" key={`${index}-${item.productId}`}>
              <div className="d-flex border-bottom pb-2">
                <div className="col-md-6 d-flex align-items-center">
                  <div>
                    <p className="mb-0">{`${item.productId}`}</p>
                  </div>
                </div>
                <div className="col-md-3 d-flex align-items-center">
                  <div>
                    <p className="mb-0">{`${item.size}`}</p>
                  </div>
                </div>
                <div className="col-md-3 d-flex align-items-center">
                  <div>
                    <p className="mb-0">{`${item.quantity}`}</p>
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

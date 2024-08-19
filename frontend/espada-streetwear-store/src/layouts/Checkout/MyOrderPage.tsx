import axios from "axios";
import React, { useEffect, useState } from "react";

export const MyOrderPage = () => {
  const [orders, setOrders] = useState([
    {
      id: "",
      deliveryAddress: "",
      phoneNumber: "",
      state: "",
      payment: 0,
      paymentState: false,
      orderingDate: new Date(),
      shippingDate: new Date(),
      userId: "",
    },
  ]);
  const [err, setError] = useState("");

  const fetchOrders = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/order/my-order",
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      const formattedOrders = response.data.result.map((order: any) => ({
        ...order,
        orderingDate: new Date(order.orderingDate),
        shippingDate: order.shippingDate ? new Date(order.shippingDate) : null,
      }));

      setOrders(formattedOrders);
    } catch (error: any) {
      setError(error.response?.data?.message || "An error occurred");
    }
  };

  const formatedTimestamp = (d: Date) => {
    const date = d.toISOString().split("T")[0];
    const time = d.toTimeString().split(" ")[0];
    return `${date} ${time}`;
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div className="container mt-5">
      <h1 className="mb-5">
        <strong>Your Order</strong>
      </h1>
      <div className="row">
        <div className="col-12 mb-3">
          <div className="d-flex border-bottom pb-2">
            <div className="col-md-4 text-secondary">Contact</div>
            <div className="col-md-3 text-secondary">Payment</div>
            <div className="col-md-5 text-end text-secondary">State</div>
          </div>
        </div>
        {orders.length === 0 ? (
          <p>You don't have any orders.</p>
        ) : (
          orders.map((item, index) => (
            <div className="col-12 mb-3" key={`${index}-${item.id}`}>
              <div className="d-flex border-bottom pb-2">
                <div className="col-md-4 d-flex align-items-center">
                  <div>
                    <p className="mb-0">
                      Delivery Address: {item.deliveryAddress}
                    </p>
                    <p className="mb-0">Phone Number: {item.phoneNumber}</p>
                  </div>
                </div>
                <div className="col-md-3 d-flex align-items-center">
                  <div>
                    <p className="mb-0">Payment: ${item.payment}</p>
                    <p className="mb-0">
                      Payment state: {`${item.paymentState}`}
                    </p>
                  </div>
                </div>
                <div className="col-md-5 d-flex justify-content-end">
                  <div>
                    <p className="mb-0">
                      Ordering Date: {formatedTimestamp(item.orderingDate)}
                    </p>
                    <p className="mb-0">
                      Shipping Date:{" "}
                      {item.shippingDate
                        ? item.shippingDate.toString().substring(0, 15)
                        : ""}
                    </p>
                    <p className="mb-0">State: {item.state}</p>
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

import React, { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { Link, useLocation } from "react-router-dom";

interface ProductSizeQuantity {
  [productId: string]: { [size: string]: number };
}

export const CheckOutPage: React.FC<{}> = () => {
  const PAYMENT_METHOD = [
    "Cash On Delivery",
    "NCB",
    "VISA",
    "MasterCard",
    "JCB",
  ];

  const SUCCESS_CODE = 1000;
  const USD_VND_EXCHANGE_RATE = 25000;

  const [deliveryAddress, setDeliveryAddress] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [paymentState, setPaymentState] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState("Cash On Delivery");
  const [error, setError] = useState<string | null>(null);
  const [isOrdering, setIsOrdering] = useState(false);
  const [isPaying, setIsPaying] = useState(false);

  const location = useLocation();

  const param = (location.state || {}) as {
    productSizeQuantity: ProductSizeQuantity;
    payment: number;
  };
  const [productSizeQuantity, setProductSizeQuantity] =
    useState<ProductSizeQuantity>(param.productSizeQuantity || {});
  const payment = param.payment;
  let response: AxiosResponse<any, any>;

  useEffect(() => {
    const fetchUserData = async () => {
      if (!productSizeQuantity) {
        return;
      }

      try {
        const userResponse = await axios.get(
          "http://localhost:8080/api/user/my-info",
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
          }
        );

        const userData = userResponse.data.result;
        setPhoneNumber(userData.phoneNumber);

        const customerResponse = await axios.get(
          "http://localhost:8080/api/customer-info",
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
          }
        );

        const customerData = customerResponse.data.result;
        setDeliveryAddress(customerData.deliveryAddress);

        setError("");
      } catch (err: any) {
        setError("Failed to fetch user data");
      }
    };

    fetchUserData();
  }, []);

  const handlePlaceOrder = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsOrdering(true);

    try {
      response = await axios.post(
        "http://localhost:8080/api/order",
        {
          deliveryAddress,
          phoneNumber,
          payment,
          paymentState,
          productSizeQuantity,
        },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (response.data.code === SUCCESS_CODE) {
        await axios.delete("http://localhost:8080/api/cart/delete-all", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        });

        setProductSizeQuantity({});
        setError(null);
      }
    } catch (err: any) {
      setError(err.response.data.message);
    } finally {
      setIsOrdering(false);
    }
  };

  const handlePayment = async () => {
    setIsPaying(true);

    if (paymentMethod === PAYMENT_METHOD[0]) {
      setPaymentState(false);
    } else {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/payment/vnpay/create-payment?amount=${payment*USD_VND_EXCHANGE_RATE}&bankCode=${paymentMethod}`,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
          }
        );

        if (response.data.code === SUCCESS_CODE) {
          const paymentUrl = response.data.result.paymentUrl;
          window.open(paymentUrl, "_blank");
        } else {
          setError("Payment failed! Please try again");
        }
      } catch (error) {
        setError("An error occurred. Please try again");
      }
    }
  };

  return (
    <div className="w-100 m-auto">
      <div className="container w-100 text-body-secondary mb-5">
        <h1 className="mt-3 mb-3 text-center fw-bold text-body-secondary">
          PLACE ORDER
        </h1>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <form onSubmit={handlePlaceOrder}>
          <div className="row">
            <div className="col-sm-6">
              <h3>DELIVERY INFORMATION</h3>
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
              <div>
                <h4>Payment: ${payment ? `${payment.toFixed(2)}` : 0}</h4>
              </div>
              {paymentMethod === PAYMENT_METHOD[0] || paymentState ? (
                <input
                  id="register-btn"
                  type="submit"
                  className="btn btn-secondary form-control"
                  value="Order"
                  name="order"
                  disabled={isOrdering}
                />
              ) : (
                <input
                  className="btn btn-secondary form-control"
                  type="submit"
                  onClick={handlePayment}
                  disabled={isOrdering}
                  value="Pay For The Order"
                />
              )}
            </div>
            <div className="col-sm-6">
              <h3>PAYMENT</h3>
              <div className="mb-3">
                <label htmlFor="paymentState" className="form-label">
                  Payment Method
                </label>
                <select
                  className="form-select text-body-secondary"
                  id="paymentMethod"
                  name="paymentMethod"
                  value={paymentMethod}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                >
                  {PAYMENT_METHOD.map((method) => (
                    <option key={method} value={method}>
                      {method}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>
        </form>
      </div>
      {error === null && (
        <div>
          <p className="mb-3 text-success text-center">
            Order placed successfully
          </p>
          <Link
            className="d-flex justify-content-center"
            style={{ textDecoration: "NONE" }}
            to="/home"
          >
            <button className="btn btn-secondary">Back to Home</button>
          </Link>
        </div>
      )}
      <hr />
    </div>
  );
};

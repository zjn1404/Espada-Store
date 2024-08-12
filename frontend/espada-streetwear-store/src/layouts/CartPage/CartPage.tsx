import React, { useEffect, useState } from "react";
import axios from "axios";

export const CartPage = () => {
  const [cartItems, setCartItems] = useState<
    {
      product: {
        id: string;
        image?: string;
        name: string;
        price: number;
        color: string;
        form: string;
        material: string;
        size: string;
        gender: string;
        description: string;
        stock: number;
      };
      cartDetails: {
        cartDetailId: { cartId: string; size: string };
        quantity: number;
      }[];
    }[]
  >([]);
  const [loading, setLoading] = useState(false);

  const getImageSrc = (base64: string) => {
    return `data:image/jpeg;base64,${base64}`;
  };

  const fetchCartItems = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/cart", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
      });

      if (response.data.code === 1000) {
        setCartItems(response.data.result);
      } else {
        console.error("Failed to load cart items:", response.data.message);
      }
    } catch (error) {
      console.error("Failed to fetch cart items:", error);
    }
  };

  useEffect(() => {
    fetchCartItems();
  }, []);

  const handleQuantityChange = async (
    productId: string,
    newQuantity: number,
    size: string
  ) => {
    if (newQuantity < 1) return;

    setLoading(true);
    try {
      const response = await axios.put(
        `http://localhost:8080/api/cart/${productId}/${size}/${newQuantity}`,
        null,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (response.data.code === 1000) {
        fetchCartItems();
      } else {
        console.error("Failed to update cart item:", response.data.message);
      }
    } catch (error) {
      console.error("Failed to update cart item:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (productId: string, size: string) => {
    setLoading(true);
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/cart/${productId}/${size}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (response.data.code === 1000) {
        fetchCartItems();
      } else {
        console.error("Failed to delete cart item:", response.data.message);
      }
    } catch (error) {
      console.error("Failed to delete cart item:", error);
    } finally {
      setLoading(false);
    }
  };

  // Calculate the total price of all items in the cart
  const totalPrice = cartItems.reduce((total, item) => {
    const itemTotal = item.cartDetails.reduce(
      (sum, detail) => sum + item.product.price * detail.quantity,
      0
    );
    return total + itemTotal;
  }, 0);

  return (
    <div className="container mt-5">
      <h1 className="mb-5">
        <strong>Your Cart</strong>
      </h1>
      <div className="row">
        <div className="col-12 mb-3">
          <div className="d-flex border-bottom pb-2">
            <div className="col-md-6 text-secondary">Product</div>
            <div className="col-md-3 text-secondary">Quantity</div>
            <div className="col-md-3 text-end text-secondary">Total</div>
          </div>
        </div>
        {cartItems.length == 0 ? (
          <p>Your cart is empty.</p>
        ) : (
          cartItems.map((item, index) =>
            item.cartDetails ? (
              item.cartDetails.map((detail, detailIndex) => (
                <div className="col-12 mb-3" key={`${index}-${detailIndex}`}>
                  <div className="d-flex border-bottom pb-2">
                    <div className="col-md-6 d-flex align-items-center">
                      {item.product.image && (
                        <img
                          src={getImageSrc(item.product.image)}
                          className="img-fluid rounded-start me-2"
                          alt={item.product.name}
                          style={{ width: "80px", height: "80px" }}
                        />
                      )}
                      <div>
                        <h5 className="card-title mb-0">{item.product.name}</h5>
                        <p className="mb-0">Size: {detail.cartDetailId.size}</p>
                        <p className="mb-0">Price: ${item.product.price.toFixed(2)}</p>
                      </div>
                    </div>
                    <div className="col-md-3 d-flex align-items-center">
                      <div
                        style={{
                          border: "1px solid #ced4da",
                          padding: "0.375rem 0.75rem",
                          borderRadius: "0.25rem",
                        }}
                      >
                        <button
                          className="btn me-2"
                          onClick={() =>
                            handleQuantityChange(
                              item.product.id,
                              detail.quantity - 1,
                              detail.cartDetailId.size
                            )
                          }
                          style={{ border: "none" }}
                          disabled={loading || detail.quantity <= 1}
                        >
                          -
                        </button>
                        <span>{detail.quantity}</span>
                        <button
                          className="btn ms-2"
                          onClick={() =>
                            handleQuantityChange(
                              item.product.id,
                              detail.quantity + 1,
                              detail.cartDetailId.size
                            )
                          }
                          style={{ border: "none" }}
                          disabled={loading}
                        >
                          +
                        </button>
                      </div>
                      <button
                        className="btn ms-2"
                        onClick={() =>
                          handleDelete(
                            item.product.id,
                            detail.cartDetailId.size
                          )
                        }
                        disabled={loading}
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
                    <div className="col-md-3 d-flex align-items-center justify-content-end">
                      <p className="mb-0">
                        <strong>
                          ${(item.product.price * detail.quantity).toFixed(2)}
                        </strong>
                      </p>
                    </div>
                  </div>
                </div>
              ))
            ) : (
              <p key={index}>No details available for this product.</p>
            )
          )
        )}
      </div>

      <div className="mt-5 d-flex justify-content-between">
        <h4>
          <strong>Estimated Total:</strong> ${totalPrice.toFixed(2)}
        </h4>
        <button
          className="btn btn-dark btn-lg"
          disabled={cartItems.length === 0 || loading}
        >
          Checkout
        </button>
      </div>
    </div>
  );
};

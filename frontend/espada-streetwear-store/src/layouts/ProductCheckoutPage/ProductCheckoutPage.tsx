import React, { useEffect, useState } from "react";
import ProductModel from "../../model/ProductModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import failToFetchImg from "../../img/error/fail-to-fetch.jpg";
import defaultImg from "../../img/logo/espada.png";
import { DisplayProduct } from "../ProductsPage/DisplayProducts";
import { StarsReview } from "../Utils/StarReview";
import { Link } from "react-router-dom";
import axios from "axios";

export const ProductCheckoutPage = () => {
  const [product, setProduct] = useState<ProductModel>();
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);
  const [selectedSize, setSelectedSize] = useState<string | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [alert, setAlert] = useState({ show: false, type: "", message: "" });

  const productId = window.location.pathname.split("/")[2];
  const bestSellerUrl = `http://localhost:8080/api/product/subtype/${product?.subtype}`;
  const getImageSrc = (base64: string) => {
    return `data:image/jpeg;base64,${base64}`;
  };

  const url = `http://localhost:8080/api/product/${productId}`;

  const handleAddToCart = async () => {
    try {
      if (!selectedSize) {
        setAlert({
          show: true,
          type: "danger",
          message: "Please select a size before adding to cart.",
        });
        return;
      }

      const response = await axios.get(
        `http://localhost:8080/api/cart/${productId}/${selectedSize}/${
          quantity || 1
        }`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (response.data.code === 1000) {
        setAlert({
          show: true,
          type: "success",
          message: "Item added to cart successfully!",
        });
      } else {
        setAlert({
          show: true,
          type: "danger",
          message: `Failed to add item: ${response.data.message}`,
        });
      }
    } catch (error) {
      setAlert({
        show: true,
        type: "danger",
        message: "An error occurred. Please try again.",
      });
    }
  };

  useEffect(() => {
    const fetchProducts = async () => {
      const response = await fetch(url);

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      const responseData = responseJson.result;

      const loadedProduct: ProductModel = {
        id: responseData.id,
        name: responseData.name,
        price: responseData.price,
        color: responseData.color,
        material: responseData.material,
        size: responseData.size,
        form: responseData.form,
        gender: responseData.gender,
        stock: responseData.stock,
        subtype: responseData.subtype.name,
        image: responseData.image,
        description: responseData.description,
      };

      setProduct(loadedProduct);
      setIsLoading(false);
    };

    fetchProducts().catch((error) => {
      setHttpError(error.message);
      setIsLoading(false);
    });

    window.scrollTo(0, 0);
  }, [url]);

  const handleSizeSelection = (size: string) => {
    setSelectedSize(size);
  };

  const handleQuantityChange = (increment: number) => {
    setQuantity((prevQuantity) =>
      Math.max(1, Math.min(prevQuantity + increment, product?.stock || 0))
    );
  };

  if (isLoading) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <section
        className="d-flex justify-content-center align-items-center"
        style={{ position: "relative", width: "100%", height: "100%" }}
      >
        <img
          src={failToFetchImg}
          alt="Failed to fetch"
          style={{ width: "100%", height: "100%" }}
        />
        <div
          style={{
            position: "absolute",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0, 0, 0, 0.7)",
            color: "white",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            fontSize: "4rem",
            fontWeight: "bold",
            textShadow: "2px 2px 4px rgba(0, 0, 0, 0.7)",
          }}
        >
          FAIL TO LOAD PRODUCT
        </div>
      </section>
    );
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center mb-3">
        <div className="col-sm-12 col-md-6 text-center">
          {product?.image ? (
            <img
              src={getImageSrc(product.image)}
              className="img-fluid"
              alt={product.id}
            />
          ) : (
            <img src={defaultImg} className="img-fluid" alt="default" />
          )}
        </div>
        <div className="col-sm-12 col-md-6">
          <h2
            className="display-4"
            style={{ fontSize: "3rem", fontWeight: "400" }}
          >
            <strong>{product?.name}</strong>
          </h2>
          <h3>{product?.price}$</h3>

          <div className="mt-3">
            <p style={{ fontSize: "1.2rem" }}>Size:</p>
            {product?.size.split("/").map((size) => (
              <button
                key={size}
                className={`btn btn-outline-secondary me-2 ${
                  selectedSize === size ? "active" : ""
                }`}
                onClick={() => handleSizeSelection(size)}
              >
                {size}
              </button>
            ))}
          </div>

          <div className="mt-3">
            <p style={{ fontSize: "1.2rem" }}>Quantity:</p>
            <div className="d-flex align-items-center">
              <div
                style={{
                  border: "1px solid #ced4da",
                  padding: "0.375rem 0.75rem",
                  borderRadius: "0.25rem",
                }}
              >
                <button
                  className="btn"
                  onClick={() => handleQuantityChange(-1)}
                  style={{ border: "none" }}
                >
                  -
                </button>
                <span className="ms-3 me-3">{quantity}</span>
                <button
                  className="btn"
                  onClick={() => handleQuantityChange(1)}
                  style={{ border: "none" }}
                >
                  +
                </button>
              </div>
            </div>
          </div>
          {localStorage.getItem("accessToken") ? (
            <div className="mt-3 mb-3">
              <Link
                to="#"
                className="btn btn-outline-secondary mt-3"
                style={{ width: "50%" }}
                onClick={handleAddToCart}
              >
                Add to Cart
              </Link>
              {alert.show && (
                <div
                  className={`alert alert-${alert.type} mt-3`}
                  role="alert"
                  style={{ width: "50%" }}
                >
                  {alert.message}
                </div>
              )}
            </div>
          ) : (
            <div className="mt-3 mb-3">
              <Link
                to="/sign-in"
                className="btn btn-outline-secondary mt-3"
                style={{ width: "50%" }}
              >
                Sign In
              </Link>
            </div>
          )}

          <p>Material: {product?.material}</p>
          <p>Form: {product?.form}</p>
          <p>Color: {product?.color}</p>
          <p>Gender: {product?.gender}</p>
          <p>Stock: {product?.stock}</p>
          <p>Description: {product?.description}</p>
          <StarsReview rating={0} size={32} />
        </div>
      </div>
      <h2 className="display-4" style={{ fontSize: "3rem", fontWeight: "400" }}>
        <strong>You may also like</strong>
      </h2>
      <div className="row justify-content-center">
        <DisplayProduct
          baseUrl={bestSellerUrl}
          notDisplayedProduct={product?.id}
        />
      </div>
    </div>
  );
};

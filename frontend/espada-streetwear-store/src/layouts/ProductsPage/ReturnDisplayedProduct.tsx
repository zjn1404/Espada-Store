import React from "react";
import ProductModel from "../../model/ProductModel";
import defaultImg from "../../img/logo/espada.png";
import { Link } from "react-router-dom";

export const ReturnDisplayProduct: React.FC<{
  product: ProductModel;
  alt: string;
}> = (props) => {
  const getImageSrc = (base64: string) => {
    return `data:image/jpeg;base64,${base64}`;
  };

  return (
    <div className="card p-2 product mt-3 me-3" style={{ width: "18rem" }}>
      <Link to={`/product/${props.product.id}`}>
        {props.product.image ? (
          <img
            src={getImageSrc(props.product.image)}
            className="card-img-top displayed-product-img"
            alt={props.alt}
            onError={(e) => {
              e.currentTarget.src = defaultImg;
            }}
          />
        ) : (
          <img
            src={defaultImg}
            className="card-img-top displayed-product-img"
            alt={props.alt}
          />
        )}
      </Link>
      <div className="card-body">
        <Link to={`/checkout/${props.product.id}`} style={{ color: "black", textDecoration: "none" }}>
          <h5 className="card-title text-body-secondary">
            {props.product.name}
          </h5>
        </Link>
        <p
          className="card-text text-body-secondary"
          style={{ fontSize: "20px" }}
        >
          ${props.product.price.toFixed(2)}
        </p>
      </div>
    </div>
  );
};
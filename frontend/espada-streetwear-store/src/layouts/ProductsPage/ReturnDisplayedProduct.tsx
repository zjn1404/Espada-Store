import React from "react";
import ProductModel from "../../model/ProductModel";
import defaultImg from "../../img/logo/espada.png";

export const ReturnDisplayProduct: React.FC<{
  product: ProductModel;
  alt: string;
}> = (props) => {
  const getImageSrc = (base64: string) => {
    return `data:image/jpeg;base64,${base64}`;
  };

  return (
    <div className="card p-2 product mt-3 me-3" style={{ width: "18rem" }}>
      <a href="#">
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
      </a>
      <div className="card-body">
        <a href="#" style={{ color: "black", textDecoration: "none" }}>
          <h5 className="card-title text-body-secondary">
            {props.product.name}
          </h5>
        </a>
        <p
          className="card-text text-body-secondary"
          style={{ fontSize: "20px" }}
        >
          {props.product.price}
        </p>
      </div>
    </div>
  );
};

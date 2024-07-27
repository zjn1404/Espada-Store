import React from "react";

export const ReturnDisplayProduct: React.FC<{
  productImgUrl: string;
  productName: string;
  productPrice: string;
  alt: string;
}> = (props) => {
  return (
    <div className="card p-2 flex-fill product" style={{ width: "18rem" }}>
      <a href="#">
        <img
          src={props.productImgUrl}
          className="card-img-top displayed-product-img"
          alt={props.alt}
        />
      </a>
      <div className="card-body">
        <a href="#" style={{ color: "black", textDecoration: "none" }}>
          <h5 className="card-title text-body-secondary">
            {props.productName}
          </h5>
        </a>
        <p
          className="card-text text-body-secondary"
          style={{ fontSize: "20px" }}
        >
          {props.productPrice}
        </p>
      </div>
    </div>
  );
};

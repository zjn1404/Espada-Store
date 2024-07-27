import React from "react";

import product1 from "../../../img/product/p1.jpeg";
import product2 from "../../../img/product/p2.jpeg";
import product3 from "../../../img/product/p3.jpeg";
import product4 from "../../../img/product/p4.jpeg";
import { ReturnDisplayProduct } from "./ReturnDisplayedProduct";

export const DisplayProduct: React.FC<{}> = (props) => {
  return (
    <div className="container mt-5">
      <div className="row">
        <ReturnDisplayProduct productImgUrl={product1} productName="1729 Tee" productPrice="20$" alt="product-1" />
        <ReturnDisplayProduct productImgUrl={product2} productName="Classic Tee" productPrice="25$" alt="product-2" />
        <ReturnDisplayProduct productImgUrl={product3} productName="MadeByIlsn Tee" productPrice="30$" alt="product-3" />
        <ReturnDisplayProduct productImgUrl={product4} productName="MadeByIlsn Tee" productPrice="30$" alt="product-4" />
      </div>
    </div>
  );
};

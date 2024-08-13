import React from "react";
import { DisplayProduct } from "./DisplayProducts";

export const BestSeller: React.FC<{}> = (props) => {
  return (
    <div>
      <DisplayProduct baseUrl="http://localhost:8080/api/order/best-seller" />
    </div>
  )
};

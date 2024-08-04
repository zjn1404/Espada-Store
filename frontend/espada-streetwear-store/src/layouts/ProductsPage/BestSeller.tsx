import React from "react";
import { DisplayProduct } from "./DisplayProducts";

export const BestSeller: React.FC<{}> = (props) => {
  return (
    <div>
      {/* TODO: Change to order/bestseller */}
      <DisplayProduct baseUrl="http://localhost:8080/api/product"/>
    </div>
  )
};

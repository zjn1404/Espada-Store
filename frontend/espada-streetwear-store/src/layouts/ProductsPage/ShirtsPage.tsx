import React from "react";
import { DisplayProduct } from "./DisplayProducts";

export const ShirtsPage: React.FC<{}> = (props) => {
  return (
    <div>
      <DisplayProduct baseUrl="http://localhost:8080/api/product/subtype/shirt" />
    </div>
  );
};

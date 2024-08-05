import React from "react";
import { DisplayProduct } from "./DisplayProducts";

export const PantsPage: React.FC<{}> = (props) => {
  return (
    <div>
      <DisplayProduct baseUrl="http://localhost:8080/api/product/subtype/pants" />
    </div>
  );
};

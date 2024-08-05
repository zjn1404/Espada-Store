import React from "react";
import { DisplayProduct } from "./DisplayProducts";

export const JacketsPage: React.FC = () => {

  return (
    <div>
      <DisplayProduct baseUrl={"http://localhost:8080/api/product/subtype/jacket"} />
    </div>
  );
};

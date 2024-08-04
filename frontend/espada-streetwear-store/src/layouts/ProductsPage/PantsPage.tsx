import React from "react";
import { DisplayProduct } from "./DisplayProducts";
import { Navbar } from "../NavbarAndFooter/Navbar";

export const PantsPage: React.FC<{}> = (props) => {
  return (
    <div>
      <DisplayProduct baseUrl="http://localhost:8080/api/product/subtype/pants" />
    </div>
  );
};

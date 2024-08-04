import React from "react";
import { DisplayProduct } from "./DisplayProducts";
import { Navbar } from "../NavbarAndFooter/Navbar";

export const ShortsPage: React.FC<{}> = (props) => {
  return (
    <div>
      <DisplayProduct baseUrl="http://localhost:8080/api/product/subtype/shorts"/>
    </div>
  )
};

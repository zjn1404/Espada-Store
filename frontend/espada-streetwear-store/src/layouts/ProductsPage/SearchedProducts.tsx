import React from "react";
import { DisplayProduct } from "./DisplayProducts";
import { Navbar } from "../NavbarAndFooter/Navbar";

export const BestSeller: React.FC<{
  search : string
}> = (props) => {
  const baseUrl = "http://localhost:8080/api/product/search?input=" + props.search;
  return (
    <div>
      <Navbar />
      <DisplayProduct baseUrl={baseUrl}/>
    </div>
  )
};

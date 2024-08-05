import React from "react";
import { useLocation } from "react-router-dom";
import { DisplayProduct } from "./DisplayProducts";

export const SearchPage: React.FC = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const searchQuery = queryParams.get("input") || "";

  const baseUrl = `http://localhost:8080/api/product/search?input=${searchQuery}`;

  return (
    <div>
      <DisplayProduct baseUrl={baseUrl} />
    </div>
  );
};
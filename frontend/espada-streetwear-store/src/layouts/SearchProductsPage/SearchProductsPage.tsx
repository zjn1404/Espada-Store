import React from "react";
import { useEffect, useState } from "react";
import ProductModel from "../../model/ProductModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";

export const searchBooksPage = () => {
  const [products, setProducts] = useState<ProductModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      // TODO: this url will be changed to api/order/bestsellers
      const baseUrl = "http://localhost:8080/api/product";

      const response = await fetch(baseUrl);

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      console.log(responseJson);

      const responseData = responseJson.result.content;

      const loadedProducts: ProductModel[] = [];

      for (const key in responseData) {
        loadedProducts.push({
          id: responseData[key].id,
          name: responseData[key].name,
          price: responseData[key].price,
          color: responseData[key].color,
          material: responseData[key].material,
          size: responseData[key].size,
          gender: responseData[key].gender,
          stock: responseData[key].stock,
          subtype: responseData[key].subtype.name,
          image: responseData[key].image,
          description: responseData[key].description,
        });
      }

      setProducts(loadedProducts);
      setIsLoading(false);
      console.log(loadedProducts);
    };
    fetchProducts().catch((error) => {
      setError(error.message);
      setIsLoading(false);
    });
  }, []);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <section>
        <p>{httpError}</p>
      </section>
    );
  }

  return (
    <div>

    </div>
  );
}

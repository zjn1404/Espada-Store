import React, { useEffect, useState } from "react";
import ProductModel from "../../model/ProductModel";
import { ReturnDisplayProduct } from "./ReturnDisplayedProduct";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { Pagination } from "../Utils/Pagination";

export const DisplayProduct: React.FC<{ baseUrl: string }> = ({ baseUrl }) => {
  const [products, setProducts] = useState<ProductModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState<string | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [productsPerPage] = useState(12);
  const [totalAmountOfProducts, setTotalAmountOfProducts] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    const fetchProducts = async () => {
      const response = await fetch(
        `${baseUrl}?page=${currentPage - 1}&size=${productsPerPage}`
      );

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      const responseData = responseJson.result.content;

      setTotalAmountOfProducts(responseJson.result.page.totalElements);
      setTotalPages(responseJson.result.page.totalPages);

      const loadedProducts: ProductModel[] = responseData.map(
        (product: any) => ({
          id: product.id,
          name: product.name,
          price: product.price,
          color: product.color,
          material: product.material,
          size: product.size,
          gender: product.gender,
          stock: product.stock,
          subtype: product.subtype.name,
          image: product.image,
          description: product.description,
        })
      );

      setProducts(loadedProducts);
      setIsLoading(false);
    };

    fetchProducts().catch((error) => {
      setHttpError(error.message);
      setIsLoading(false);
    });

    window.scrollTo(0, 0);
  }, [currentPage, baseUrl, productsPerPage]);

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

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

  return (
    <div className="container mt-5">
      <div className="row">
        {products.map((product) => (
          <ReturnDisplayProduct
            product={product}
            key={product.id}
            alt={`product ${product.id}`}
          />
        ))}
      </div>
      <div className="mt-5 d-flex justify-content-center">
        {totalPages > 1 && (
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            paginate={paginate}
          />
        )}
      </div>
    </div>
  );
};

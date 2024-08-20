import React, { useEffect, useState } from "react";
import ProductModel from "../../model/ProductModel";
import { ReturnDisplayProduct } from "./ReturnDisplayedProduct";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { Pagination } from "../Utils/Pagination";
import failToFetchImg from "../../img/error/fail-to-fetch.jpg";

export const DisplayProduct: React.FC<{ 
  baseUrl: string;
  notDisplayedProduct ?: string;
  displayedAmount ?: number;
}> = (props) => {
  const [products, setProducts] = useState<ProductModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState<string | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [productsPerPage] = useState(12);
  const [displayedBestSellerAmount] = useState(8);
  const [totalAmountOfProducts, setTotalAmountOfProducts] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    const fetchProducts = async () => {
      let url;

      if (props.baseUrl.search("best-seller") !== -1) {
        url = `${props.baseUrl}?page=${currentPage - 1}&size=${displayedBestSellerAmount}`;
      } else if (props.displayedAmount !== undefined) {
        url = `${props.baseUrl}?page=${currentPage - 1}&size=${props.displayedAmount}`;
      }
       else {
        url = `${props.baseUrl}?page=${currentPage - 1}&size=${productsPerPage}`;
      }

      const response = await fetch(
        url
      );

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      const responseData = responseJson.result.content;

      setTotalAmountOfProducts(responseJson.result.page.totalElements);
      setTotalPages(responseJson.result.page.totalPages);

      const loadedProducts: ProductModel[] = responseData
      .filter((product: any) => product.id !== props.notDisplayedProduct)
      .map(
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
  }, [currentPage, props.baseUrl, productsPerPage, props.notDisplayedProduct]);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <section className="d-flex justify-content-center align-items-center" style={{ position: 'relative', width: '100%', height: '100%' }}>
        <img src={failToFetchImg} alt="Failed to fetch" style={{width: '100%', 
          height: '100%'}} />
        <div style={{ 
          position: 'absolute', 
          top: 0, 
          left: 0, 
          width: '100%', 
          height: '100%', 
          backgroundColor: 'rgba(0, 0, 0, 0.7)',
          color: 'white', 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center', 
          fontSize: '4rem', 
          fontWeight: 'bold', 
          textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)'
        }}>
          FAIL TO LOAD PRODUCT
        </div>
      </section>
    );
  }

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        {products.map((product) => (
          <ReturnDisplayProduct
            product={product}
            key={product.id}
            alt={`product ${product.id}`}
          />
        ))}
      </div>
      <div className="mt-5 d-flex justify-content-center">
        {totalPages > 1 && props.displayedAmount === undefined && (
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

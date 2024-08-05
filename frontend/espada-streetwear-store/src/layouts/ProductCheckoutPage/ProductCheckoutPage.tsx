import React, { useEffect, useState } from "react";
import ProductModel from "../../model/ProductModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import failToFetchImg from "../../img/error/fail-to-fetch.jpg";
import defaultImg from "../../img/logo/espada.png";
import { DisplayProduct } from "../ProductsPage/DisplayProducts";

export const ProductCheckoutPage = () => {
  const [product, setProduct] = useState<ProductModel>();
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);
  
  const productId = (window.location.pathname).split('/')[2];
  // TODO: it will be changed to api/order/bestsellers
  const bestSellerUrl = `http://localhost:8080/api/product/subtype/${product?.subtype}`;
  const getImageSrc = (base64: string) => {
    return `data:image/jpeg;base64,${base64}`;
  };

  const url = `http://localhost:8080/api/product/${productId}`;

  useEffect(() => {
    const fetchProducts = async () => {    

      const response = await fetch(url);

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      const responseData = responseJson.result;

      const loadedProduct : ProductModel = {
          id: responseData.id,
          name: responseData.name,
          price: responseData.price,
          color: responseData.color,
          material: responseData.material,
          size: responseData.size,
          gender: responseData.gender,
          stock: responseData.stock,
          subtype: responseData.subtype.name,
          image: responseData.image,
          description: responseData.description,
        };

      setProduct(loadedProduct);
      setIsLoading(false);
    };

    fetchProducts().catch((error) => {
      setHttpError(error.message);
      setIsLoading(false);
    });

    window.scrollTo(0, 0);
  }, [url]);

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

  return (
    <div className="container mt-5">
      <div className="row justify-content-center mb-3">
        <div className="col-sm-3 col-md-3">
        {product?.image ?
          <img src={getImageSrc(product.image)} width='500' height='600' alt={product.id}/>
          :
          <img src={defaultImg} width='226' height='349' alt="default image"/>
        }
        </div>
        <div className="col-3 col-md-3 container">
          <h2 style={{fontSize: '3rem'}}>{product?.name}</h2>
          <h3>{product?.price}$</h3>
          <p>Material: {product?.material}</p>
          <p>Size: {product?.size}</p>
          <p>Color: {product?.color}</p>
          <p>Gender: {product?.gender}</p>
          <p>Stock: {product?.stock}</p>
          <p>Description: {product?.description}</p>
        </div>
      </div>
      <h2 style={{fontSize:'3rem'}}>You may also like</h2>
      <div className="row justify-content-center">
        <DisplayProduct baseUrl={bestSellerUrl} notDisplayedProduct={product?.id}/>
      </div>
    </div>
  );
}
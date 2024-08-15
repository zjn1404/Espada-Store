import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";

export const UpdateProductDetailPage = () => {
  const location = useLocation();
  const productId = location.pathname.split("/")[4];

  const [productData, setProductData] = useState({
    name: "",
    price: 0,
    color: "",
    material: "",
    size: "",
    gender: "",
    description: "",
    stock: 0,
    form: "",
    image: null as File | null,
  });

  const [isLoading, setIsLoading] = useState(false);
  const [alert, setAlert] = useState({ show: false, type: "", message: "" });

  const fetchProductDetails = async () => {
    setIsLoading(true);
    try {
      const fetchProductResponse = await axios.get(
        `http://localhost:8080/api/product/${productId}`
      );

      const data = fetchProductResponse.data.result;
      setProductData({
        name: data.name,
        price: data.price,
        color: data.color,
        material: data.material,
        size: data.size,
        gender: data.gender,
        description: data.description,
        stock: data.stock,
        form: data.form,
        image: null, 
      });
    } catch (error) {
      setAlert({
        show: true,
        type: "danger",
        message: "An error occurred. Please try again.",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleUpdateProduct = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const formData = new FormData();

      formData.append("name", productData.name);
      formData.append("price", productData.price.toString());
      formData.append("color", productData.color);
      formData.append("material", productData.material);
      formData.append("size", productData.size);
      formData.append("gender", productData.gender);
      formData.append("description", productData.description);
      formData.append("stock", productData.stock.toString());
      formData.append("form", productData.form);

      if (productData.image) {
        formData.append("image", productData.image);
      }

      const response = await axios.put(
        `http://localhost:8080/api/product/${productId}`,
        formData, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (response.data.code === 1000) {
        setAlert({
          show: true,
          type: "success",
          message: "Product updated successfully!",
        });
      } else {
        setAlert({
          show: true,
          type: "danger",
          message: `Failed to update product: ${response.data.message}`,
        });
      }
    } catch (error) {
      setAlert({
        show: true,
        type: "danger",
        message: "An error occurred. Please try again.",
      });
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProductDetails();
  }, [productId]);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  return (
    <div className="container mt-5">
      <h1 className="mb-4">Update Product {productId}</h1>
      {alert.show && (
        <div className={`alert alert-${alert.type} mt-3`} role="alert">
          {alert.message}
        </div>
      )}
      <form onSubmit={handleUpdateProduct}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Product Name:
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            value={productData.name}
            onChange={(e) =>
              setProductData({ ...productData, name: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="price" className="form-label">
            Price:
          </label>
          <input
            type="number"
            className="form-control"
            id="price"
            value={productData.price}
            onChange={(e) =>
              setProductData({
                ...productData,
                price: parseFloat(e.target.value),
              })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="color" className="form-label">
            Color:
          </label>
          <input
            type="text"
            className="form-control"
            id="color"
            value={productData.color}
            onChange={(e) =>
              setProductData({ ...productData, color: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="material" className="form-label">
            Material:
          </label>
          <input
            type="text"
            className="form-control"
            id="material"
            value={productData.material}
            onChange={(e) =>
              setProductData({ ...productData, material: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="size" className="form-label">
            Size:
          </label>
          <input
            type="text"
            className="form-control"
            id="size"
            value={productData.size}
            onChange={(e) =>
              setProductData({ ...productData, size: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="gender" className="form-label">
            Gender:
          </label>
          <input
            type="text"
            className="form-control"
            id="gender"
            value={productData.gender}
            onChange={(e) =>
              setProductData({ ...productData, gender: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">
            Description:
          </label>
          <textarea
            className="form-control"
            id="description"
            rows={3}
            value={productData.description}
            onChange={(e) =>
              setProductData({ ...productData, description: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="stock" className="form-label">
            Stock:
          </label>
          <input
            type="number"
            className="form-control"
            id="stock"
            value={productData.stock}
            onChange={(e) =>
              setProductData({
                ...productData,
                stock: parseInt(e.target.value),
              })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="form" className="form-label">
            Form:
          </label>
          <input
            type="text"
            className="form-control"
            id="form"
            value={productData.form}
            onChange={(e) =>
              setProductData({ ...productData, form: e.target.value })
            }
          />
        </div>
        <div className="mb-3">
          <label htmlFor="image" className="form-label">
            Image:
          </label>
          <input
            type="file"
            className="form-control"
            id="image"
            accept="image/*"
            onChange={(e) =>
              setProductData({
                ...productData,
                image: e.target.files?.[0] || null,
              })
            }
          />
        </div>
        <button type="submit" className="btn btn-secondary">
          Update Product
        </button>
      </form>
    </div>
  );
};

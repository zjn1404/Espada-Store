import axios from "axios";
import React, { useState } from "react";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";

export const ProductCreationPage = () => {
  const SUBTYPE = [
    "Bag",
    "Headwear",
    "Pants",
    "Shorts",
    "Skirt",
    "Hoodie",
    "Jacket",
    "Shirt",
    "Tee",
  ];

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
    subtype: "",
    image: null as File | null,
  });

  const [isLoading, setIsLoading] = useState(false);
  const [alert, setAlert] = useState({ show: false, type: "", message: "" });

  const handleAddProduct = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const formData = new FormData();
      if (productData.name) formData.append("name", productData.name);
      if (productData.price)
        formData.append("price", productData.price.toString());
      if (productData.color) formData.append("color", productData.color);
      if (productData.material)
        formData.append("material", productData.material);
      if (productData.size) formData.append("size", productData.size);
      if (productData.gender) formData.append("gender", productData.gender);
      if (productData.description)
        formData.append("description", productData.description);
      if (productData.stock)
        formData.append("stock", productData.stock.toString());
      if (productData.form) formData.append("form", productData.form);
      if (productData.subtype) formData.append("subtype", productData.subtype);
      if (productData.image) formData.append("image", productData.image);

      const response = await axios.post(
        `http://localhost:8080/api/product`,
        formData,
        {
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
          message: "Product added successfully!",
        });
      } else {
        setAlert({
          show: true,
          type: "danger",
          message: `Failed to add product: ${response.data.message}`,
        });
      }
    } catch (error: any) {
      setAlert({
        show: true,
        type: "danger",
        message: "An error occurred. Please try again.",
      });
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) {
    return <SpinnerLoading />;
  }

  return (
    <div className="container mt-5">
      <h1 className="mb-4">Add Product</h1>
      {alert.show && (
        <div
          className={`alert alert-${alert.type} mt-3`}
          role="alert"
          style={{ width: "50%" }}
        >
          {alert.message}
        </div>
      )}
      <form onSubmit={handleAddProduct}>
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
          <label htmlFor="subtype" className="form-label">
            Subtype:
          </label>
          <select
            className="form-control"
            id="subtype"
            value={productData.subtype}
            onChange={(e) =>
              setProductData({ ...productData, subtype: e.target.value })
            }
          >
            <option value="">Select Subtype</option>
            {SUBTYPE.map((subtype) => (
              <option key={subtype} value={subtype}>
                {subtype}
              </option>
            ))}
          </select>
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
          Add Product
        </button>
      </form>
    </div>
  );
};

import React, { useEffect, useState } from "react";
import userLogo from "../../img/logo/user-logo.jpeg";
import logo from "../../img/logo/espada.png";
import { NavLink, useHistory } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../../auth/utils/AuthContext";

export const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  // Initializing the state for types as an empty array of objects
const [types, setTypes] = useState<{ name: string }[]>([]); 

// Initializing the state for subtypes as an empty object where each key corresponds to a type name
const [subtypes, setSubtypes] = useState<{ [key: string]: { name: string; type: { name: string } }[] }>({});
  const role = useAuth().role;
  const history = useHistory();

  useEffect(() => {
    // Fetch types
    axios.get("http://localhost:8080/api/type")
      .then(response => {
        if (response.data.code === 1000) {
          setTypes(response.data.result);
          response.data.result.forEach((type: { name: any; }) => {
            // Fetch subtypes for each type
            axios.get(`http://localhost:8080/api/subtype/by-type?type=${type.name}`)
              .then(subtypeResponse => {
                if (subtypeResponse.data.code === 1000) {
                  setSubtypes(prevSubtypes => ({
                    ...prevSubtypes,
                    [type.name]: subtypeResponse.data.result
                  }));
                }
              })
              .catch(err => console.error(err));
          });
        }
      })
      .catch(err => console.error(err));
  }, []);

  const handleSearchSubmit = (event: { preventDefault: () => void }) => {
    event.preventDefault();
    history.push(`/product/search?input=${searchInput}`);
    setSearchInput("");
  };

  const handleSignOut = async () => {
    const accessToken = localStorage.getItem("accessToken");
    axios.post("http://localhost:8080/api/auth/logout", { token: accessToken });

    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    setIsLoggedIn(false);
    history.push("/home");
  };

  useEffect(() => {
    const accessToken = localStorage.getItem("accessToken");
    setIsLoggedIn(!!accessToken);
  }, []);

  return (
    <nav className="navbar navbar-expand-lg navbar-light" style={{ backgroundColor: "white" }}>
      <div className="container text-center" style={{ backgroundColor: "white" }}>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 p-2 flex-fill">
            {types.map(type => (
              <li className="nav-item dropdown" key={type.name}>
                <a
                  className="nav-link dropdown-toggle"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  {type.name}
                </a>
                <ul className="dropdown-menu">
                  {subtypes[type.name]?.map(subtype => (
                    <li key={subtype.name}>
                      <NavLink className="dropdown-item" to={`/product/${subtype.name.toLowerCase()}`}>
                        {subtype.name}
                      </NavLink>
                    </li>
                  ))}
                </ul>
              </li>
            ))}
            {role?.includes("ADMIN") && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/admin/product/create">Add Product</NavLink>
              </li>
            )}
            {!isLoggedIn && (
              <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  Join us
                </a>
                <ul className="dropdown-menu">
                  <li>
                    <NavLink className="dropdown-item text-body-secondary" to="/sign-in">Sign in</NavLink>
                  </li>
                  <li>
                    <NavLink className="dropdown-item text-body-secondary" to="/sign-up/user">Sign up</NavLink>
                  </li>
                </ul>
              </li>
            )}
          </ul>
          <NavLink className="navbar-brand p-2 flex-fill" to="/home">
            <img src={logo} alt="Espada-logo" height="50" />
          </NavLink>
          <form className="d-flex p-2 flex-fill" role="search" onSubmit={handleSearchSubmit}>
            <input
              className="form-control me-2 p-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
            />
            <button className="btn btn-outline-secondary" type="submit">Search</button>
          </form>
          {isLoggedIn && (
            <div className="nav-item dropdown ms-3 dropstart">
              <a
                className="nav-link dropdown-toggle text-body-secondary"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <img src={userLogo} style={{ borderRadius: "50%", height: 50 }} alt="user-logo" />
              </a>
              <ul className="dropdown-menu">
                {role?.includes("USER") ? (
                  <>
                    <li>
                      <NavLink className="dropdown-item text-body-secondary" to="/cart">Cart</NavLink>
                    </li>
                    <li>
                      <NavLink className="dropdown-item text-body-secondary" to="/my-order">My Order</NavLink>
                    </li>
                  </>
                ) : (
                  <li>
                    <NavLink className="dropdown-item text-body-secondary" to="/admin/order">Order Management</NavLink>
                  </li>
                )}
                <li>
                  <NavLink className="dropdown-item text-body-secondary" to="/change-password">Change password</NavLink>
                </li>
                <li>
                  <NavLink className="dropdown-item text-body-secondary" to="/update-info">Update information</NavLink>
                </li>
                <li>
                  <button className="dropdown-item text-body-secondary" onClick={handleSignOut}>Sign out</button>
                </li>
              </ul>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};
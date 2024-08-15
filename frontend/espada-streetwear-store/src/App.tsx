import React, { useRef, useEffect } from "react";
import "./App.css";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
} from "react-router-dom";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { HomePage } from "./layouts/HomePage/HomePage";
import { TeesPage } from "./layouts/ProductsPage/TeesPage";
import { PantsPage } from "./layouts/ProductsPage/PantsPage";
import { ShortsPage } from "./layouts/ProductsPage/ShortsPage";
import { SearchPage } from "./layouts/ProductsPage/SearchPage";
import { BagsPage } from "./layouts/ProductsPage/BagsPage";
import { HeadwearPage } from "./layouts/ProductsPage/HeadwearPage";
import { JacketsPage } from "./layouts/ProductsPage/JacketsPage";
import { SkirtsPage } from "./layouts/ProductsPage/SkirtsPage";
import { HoodiesPage } from "./layouts/ProductsPage/HoodiesPage";
import { ShirtsPage } from "./layouts/ProductsPage/ShirtsPage";
import { ProductDetailPage } from "./layouts/ProductDetailPage/ProductDetailPage";
import { LoginPage } from "./auth/LoginPage";
import { parseJwt, refresh } from "./auth/utils/auth";
import { SignUpPage } from "./auth/SignUpPage";
import { SignUpSuccessPage } from "./auth/SignUpSuccessPage";
import { VerifySuccessPage } from "./auth/VerifySuccessPage";
import { ChangePasswordPage } from "./auth/ChangePasswordPage";
import { UpdateInformationPage } from "./auth/UpdateInformationPage";
import { CartPage } from "./layouts/CartPage/CartPage";
import { CheckOutPage } from "./layouts/Checkout/CheckOutPage";
import { MyOrderPage } from "./layouts/Checkout/MyOrderPage";
import { OrderManagementPage } from "./admin/OrderManagementPage";
import { UpdateOrderPage } from "./admin/UpdateOrderPage";
import { OrderDetailsPage } from "./admin/OrderDetailsPage";
import { UpdateProductDetailPage } from "./admin/UpdateProductDetailPage";
import { ProductCreationPage } from "./admin/ProductCreationPage";


function App() {
  const displayProductRef = useRef<HTMLDivElement>(null);

  const handleFindOutClick = () => {
    if (displayProductRef.current) {
      displayProductRef.current.scrollIntoView({ behavior: "smooth" });
    }
  };

  useEffect(() => {
    const checkToken = async () => {
      const accessToken = localStorage.getItem("accessToken");
      const refreshToken = localStorage.getItem("refreshToken");

      if (accessToken && refreshToken) {
        try {
          const parsedToken = parseJwt(accessToken);
          const tokenExpiration = parsedToken?.exp * 1000;
          const now = new Date().getTime();

          if (now >= tokenExpiration - 10 * 60 * 1000) {
            await refresh();
          }
        } catch (error) {
          console.error("Error during initial token check:", error);
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          window.location.href = "/sign-in";
        }
      }
    };

    checkToken();

    const intervalId = setInterval(async () => {
      const accessToken = localStorage.getItem("accessToken");
      if (accessToken) {
        const parsedToken = parseJwt(accessToken);
        const tokenExpiration = parsedToken?.exp * 1000;
        const now = new Date().getTime();

        if (now >= tokenExpiration - 10 * 60 * 1000) {
          await refresh();
        }
      }
    }, 30 * 60 * 1000); 
    
    return () => clearInterval(intervalId); 
  }, []);


  return (
    <Router>
      <div>
        <Navbar />
        <Switch>
          <Route path="/" exact>
            <Redirect to="/home" />
          </Route>
          <Route
            path="/home"
            render={() => (
              <HomePage
                displayProductRef={displayProductRef}
                handleFindOutClick={handleFindOutClick}
              />
            )}
          />
          {/* PRODUCT */}
          <Route path="/product/tee" component={TeesPage} />
          <Route path="/product/shirt" component={ShirtsPage} />
          <Route path="/product/jacket" component={JacketsPage} />
          <Route path="/product/hoodie" component={HoodiesPage} />
          <Route path="/product/pants" component={PantsPage} />
          <Route path="/product/shorts" component={ShortsPage} />
          <Route path="/product/skirt" component={SkirtsPage} />
          <Route path="/product/bag" component={BagsPage} />
          <Route path="/product/headwear" component={HeadwearPage} />
          <Route path="/product/search" component={SearchPage} />
          {/* END PRODUCT */}
          <Route path="/product/:productId" component={ProductDetailPage} />
          {/* USER */}
          <Route path="/sign-in" component={LoginPage} />
          <Route path="/sign-up/:role" component={SignUpPage} />
          <Route path="/sign-up-success" component={SignUpSuccessPage} />
          <Route path="/verification-success" component={VerifySuccessPage} />
          <Route path="/change-password" component={ChangePasswordPage} />
          <Route path="/update-info" component={UpdateInformationPage} />
          {/* END USER */}
          {/* CHECK OUT */}
          <Route path="/cart" component={CartPage} />
          <Route path="/check-out" component={CheckOutPage} />
          <Route path="/my-order" component={MyOrderPage} />
          {/* END CHECK OUT */}
          {/* ADMIN */}
          <Route path="/admin/order" component={OrderManagementPage} />
          <Route path="/update-order" component={UpdateOrderPage} />
          <Route path="/order-details" component={OrderDetailsPage} />
          <Route path="/admin/product-detail/update/:productId" component={UpdateProductDetailPage} />
          <Route path="/admin/product/create" component={ProductCreationPage}/>
          {/* END ADMIN */}
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;

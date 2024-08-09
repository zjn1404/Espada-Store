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
import { JacketsPage } from "./layouts/ProductsPage/JacketsPage";
import { SkirtsPage } from "./layouts/ProductsPage/SkirtsPage";
import { HoodiesPage } from "./layouts/ProductsPage/HoodiesPage";
import { ShirtsPage } from "./layouts/ProductsPage/ShirtsPage";
import { ProductCheckoutPage } from "./layouts/ProductCheckoutPage/ProductCheckoutPage";
import { LoginPage } from "./auth/LoginPage";
import { parseJwt, scheduleTokenRefresh } from "./auth/utils/auth";
import { SignUpPage } from "./auth/SignUpPage";
import { SignUpSuccessPage } from "./auth/SignUpSuccessPage";
import { VerifySuccessPage } from "./auth/VerifySuccessPage";

function App() {
  const displayProductRef = useRef<HTMLDivElement>(null);

  const handleFindOutClick = () => {
    if (displayProductRef.current) {
      displayProductRef.current.scrollIntoView({ behavior: "smooth" });
    }
  };

  useEffect(() => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      const parsedToken = parseJwt(accessToken);
      if (parsedToken) {
        const tokenExpiration = parsedToken.exp * 1000;
        const now = new Date().getTime();
        if (now < tokenExpiration) {
          scheduleTokenRefresh(tokenExpiration - now);
        } else {
          // Token is expired, prompt login
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          window.location.href = "/sign-in";
        }
      } else {
        // Invalid token, prompt loginf
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        window.location.href = "/login";
      }
    }
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
          <Route path="/product/search" component={SearchPage} />
          {/* END PRODUCT */}
          <Route path="/checkout/:productId" component={ProductCheckoutPage} />
          {/* AUTH */}
          <Route path="/sign-in" component={LoginPage} />
          <Route path="/sign-up/:role" component={SignUpPage} />
          <Route path="/sign-up-success" component={SignUpSuccessPage} />
          <Route path="/verification-success" component={VerifySuccessPage} />
          {/* END AUTH */}

          {/* ADMIN */}
          {/* END ADMIN */}
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;

import React, { useRef } from "react";
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


function App() {
  const displayProductRef = useRef<HTMLDivElement>(null);

  const handleFindOutClick = () => {
    if (displayProductRef.current) {
      displayProductRef.current.scrollIntoView({ behavior: "smooth" });
    }
  };

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
          <Route path="/product/tee" component={TeesPage} />
          <Route path="/product/jacket" component={JacketsPage} />
          <Route path="/product/hoodie" component={HoodiesPage} />
          <Route path="/product/pants" component={PantsPage} />
          <Route path="/product/shorts" component={ShortsPage} />
          <Route path="/product/skirt" component={SkirtsPage} />
          <Route path="/product/bag" component={BagsPage} />
          <Route path="/product/search" component={SearchPage} />
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;

import React, { useRef } from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { HomePage } from "./layouts/HomePage/HomePage";
import { TeesPage } from "./layouts/ProductsPage/TeesPage";
import { PantsPage } from "./layouts/ProductsPage/PantsPage";
import { ShortsPage } from "./layouts/ProductsPage/ShortsPage";

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
          <Route path='/' exact>
            <Redirect to='/home'></Redirect>
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
          <Route path="/product/pants" component={PantsPage} />
          <Route path="/product/shorts" component={ShortsPage} />
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;

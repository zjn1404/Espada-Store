import React, { useRef } from "react";
import "./App.css";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { ExploreBestSeller } from "./layouts/HomePage/Components/ExploreBestSeller";
import { DisplayProduct } from "./layouts/HomePage/Components/DisplayProducts";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { HomePage } from "./layouts/HomePage/HomePage";

function App() {
  const displayProductRef = useRef<HTMLDivElement>(null);

  const handleFindOutClick = () => {
    if (displayProductRef.current) {
      displayProductRef.current.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <div>
      <Navbar />
      <HomePage
        displayProductRef={displayProductRef}
        handleFindOutClick={handleFindOutClick}
      />
      <Footer />
    </div>
  );
}

export default App;

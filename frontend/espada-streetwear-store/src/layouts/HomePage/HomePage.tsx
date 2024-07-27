import React from "react";
import { DisplayProduct } from "./Components/DisplayProducts";
import { ExploreBestSeller } from "./Components/ExploreBestSeller";

export const HomePage: React.FC<{
  displayProductRef: React.RefObject<HTMLDivElement>;
  handleFindOutClick: () => void;
}> = (props) => {
  return (
    <>
      <ExploreBestSeller handleFindOutClick={props.handleFindOutClick} />
      <div ref={props.displayProductRef}>
        <DisplayProduct />
      </div>
    </>
  );
};

import React from "react";
import { ExploreBestSeller } from "./Components/ExploreBestSeller";
import { BestSeller } from "../ProductsPage/BestSeller";

export const HomePage: React.FC<{
  displayProductRef: React.RefObject<HTMLDivElement>;
  handleFindOutClick: () => void;
}> = (props) => {
  return (
    <>
      <ExploreBestSeller handleFindOutClick={props.handleFindOutClick} />
      <div ref={props.displayProductRef}>
        <BestSeller />
      </div>
    </>
  );
};

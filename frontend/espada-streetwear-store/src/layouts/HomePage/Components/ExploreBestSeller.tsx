import React from "react";
import slider1 from "../../../img/slider/s4.jpeg";
import slider2 from "../../../img/slider/s2.jpeg";
import slider3 from "../../../img/slider/s5.jpg";

import { ReturnLookBook } from "./ReturnLookBook";

export const ExploreBestSeller : React.FC<{
  handleFindOutClick: () => void;
}> = (props) => {
  return (
    <div className="row">
      <div
        id="carouselExampleIndicators"
        className="carousel slide"
        data-bs-ride="carousel"
      >
        <div className="carousel-indicators">
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="0"
            className="active"
            aria-current="true"
            aria-label="Slide 1"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="1"
            aria-label="Slide 2"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="2"
            aria-label="Slide 3"
          ></button>
        </div>
        <div className="carousel-inner">
          <div className="carousel-item active" data-bs-interval="10000">
            <ReturnLookBook slider={slider1} handleFindOutClick={props.handleFindOutClick} alt="lookbook-p1" />
          </div>
          <div className="carousel-item" data-bs-interval="10000">
            <ReturnLookBook slider={slider2} handleFindOutClick={props.handleFindOutClick} alt="lookbook-p2" />
          </div>
          <div className="carousel-item" data-bs-interval="10000">
            <ReturnLookBook slider={slider3} handleFindOutClick={props.handleFindOutClick} alt="lookbook-p3" />
          </div>
        </div>
        <button
          className="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleIndicators"
          data-bs-slide="prev"
        >
          <span
            className="carousel-control-prev-icon"
            aria-hidden="true"
          ></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button
          className="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleIndicators"
          data-bs-slide="next"
        >
          <span
            className="carousel-control-next-icon"
            aria-hidden="true"
          ></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>
    </div>
  );
};

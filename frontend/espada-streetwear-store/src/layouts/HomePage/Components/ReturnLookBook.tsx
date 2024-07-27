import React from "react";

export const ReturnLookBook: React.FC<{
  slider: string;
  alt: string;
  handleFindOutClick: () => void;
}> = (props) => {
  return (
    <div>
      <img
        src={props.slider}
        className="d-block carousel-img"
        alt={props.alt}
      />
      <div className="carousel-caption">
        <strong>
          <h1>OUR BEST SELLER</h1>
        </strong>
        <button onClick={props.handleFindOutClick} className="btn btn btn-outline-light mt-3">
          FIND OUT
        </button>
      </div>
    </div>
  );
};

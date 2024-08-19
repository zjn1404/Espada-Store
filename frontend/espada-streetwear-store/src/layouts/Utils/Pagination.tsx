import React from "react";

export const Pagination: React.FC<{
  currentPage: number;
  totalPages: number;
  paginate: (pageNumber: number) => void;
}> = (props) => {
  const { currentPage, totalPages, paginate } = props;
  const pageNumbers = [];

  // Create pagination numbers
  if (totalPages <= 5) {
    // If there are 5 or fewer pages, show all
    for (let i = 1; i <= totalPages; i++) {
      pageNumbers.push(i);
    }
  } else {
    // Show a range around the current page
    if (currentPage > 3) {
      pageNumbers.push(currentPage - 2);
    }
    if (currentPage > 2) {
      pageNumbers.push(currentPage - 1);
    }
    pageNumbers.push(currentPage);
    if (currentPage < totalPages - 1) {
      pageNumbers.push(currentPage + 1);
    }
    if (currentPage < totalPages - 2) {
      pageNumbers.push(currentPage + 2);
    }
  }

  return (
    <nav aria-label="Page navigation">
      <ul className="pagination justify-content-center">
        <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
          <button
            className={`page-link ${currentPage === 1 ? "text-muted" : ""}`}
            onClick={() => currentPage > 1 && paginate(1)}
            disabled={currentPage === 1}
          >
            &laquo; First
          </button>
        </li>
        {pageNumbers.map((number) => (
          <li
            key={number}
            className={`page-item ${number === currentPage ? "active" : ""}`}
          >
            <button
              className={`page-link ${
                number === currentPage
                  ? "bg-secondary text-white"
                  : "text-secondary"
              }`}
              onClick={() => number !== currentPage && paginate(number)}
            >
              {number}
            </button>
          </li>
        ))}
        <li
          className={`page-item ${
            currentPage === totalPages ? "disabled" : ""
          }`}
        >
          <button
            className={`page-link ${
              currentPage === totalPages ? "text-muted" : ""
            }`}
            onClick={() => currentPage < totalPages && paginate(totalPages)}
            disabled={currentPage === totalPages}
          >
            Last &raquo;
          </button>
        </li>
      </ul>
    </nav>
  );
};

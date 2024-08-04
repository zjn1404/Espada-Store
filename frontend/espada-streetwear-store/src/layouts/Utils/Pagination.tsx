export const Pagination: React.FC<{
  currentPage: number,
  totalPages: number,
  paginate: any
}> = (props) => {
  const pageNumbers = [];

  if (props.currentPage === 1) {
    pageNumbers.push(props.currentPage);
    
    if (props.totalPages >= props.currentPage + 1) {
      pageNumbers.push(props.currentPage + 1);
    }
    
    if (props.totalPages >= props.currentPage + 2) {
      pageNumbers.push(props.currentPage + 2);
    }
    
  } else if (props.currentPage > 1) {
    if (props.currentPage >= 3) {
      pageNumbers.push(props.currentPage - 2);
    }
    pageNumbers.push(props.currentPage - 1);
    
    pageNumbers.push(props.currentPage);

    if (props.totalPages >= props.totalPages + 1) {
      pageNumbers.push(props.currentPage + 1);
    }

    if (props.totalPages >= props.currentPage + 2) {
      pageNumbers.push(props.currentPage + 2);
    }
  }

  return (
    <nav aria-label="...">
      <ul className="pagination">
        <li className="page-item" onClick={() => props.paginate(1)}>
          <button className="page-link">
            First Page
          </button>
        </li>
        {pageNumbers.map((number) => (
          <li key={number} className="page-item">
            <a
              onClick={() => props.paginate(number)}
              href="#"
              className={`page-link ${number === props.currentPage ? "active" : ""}`}
            >
              <button className="page-link">
                {number}
              </button>
            </a>
          </li>
        ))}
        <li className="page-item" onClick={() => props.paginate(props.totalPages)}>
          <button className="page-link">
            Last Page
          </button>
        </li>
      </ul>
    </nav>
  );
}
import userLogo from '../../img/logo/user-logo.jpeg';
import logo from '../../img/logo/espada.png';

export const Navbar = () => {
  return (
    <nav
      className="navbar navbar-expand-lg navbar-light"
      style={{ backgroundColor: "white" }}
    >
      <div
        className="container text-center"
        style={{ backgroundColor: "white" }}
      >
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 p-2 flex-fill">
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                Tops
              </a>
              <ul className="dropdown-menu">
                <li>
                  <a className="dropdown-item" href="#">
                    T-shirts
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Shirts
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Jackets
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Hoodies
                  </a>
                </li>
              </ul>
            </li>
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                Bottoms
              </a>
              <ul className="dropdown-menu">
                <li>
                  <a className="dropdown-item" href="#">
                    Pants
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Shorts
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Skirts
                  </a>
                </li>
              </ul>
            </li>
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                Accessories
              </a>
              <ul className="dropdown-menu">
                <li>
                  <a className="dropdown-item" href="#">
                    Bags
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Headwears
                  </a>
                </li>
              </ul>
            </li>
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                Join us
              </a>
              <ul className="dropdown-menu">
                <li>
                  <a className="dropdown-item text-body-secondary" href="#">
                    Sign in
                  </a>
                </li>
                <li>
                  <a className="dropdown-item text-body-secondary" href="#">
                    Sign up
                  </a>
                </li>
              </ul>
            </li>
          </ul>
          <a className="navbar-brand p-2 flex-fill" href="#">
            <img src={logo} alt="Espada-logo" height="50" />
          </a>
          <form className="d-flex p-2 flex-fill" role="search">
            <input
              className="form-control me-2 p-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
            />
            <button className="btn btn-outline-secondary" type="submit">
              Search
            </button>
          </form>
        {/* TODO: Only display when user has logged in d */}
          <div className="nav-item dropdown ms-3 dropstart">
            <a
              className="nav-link dropdown-toggle text-body-secondary"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img
                src={userLogo}
                style={{ borderRadius: "50%", height: 50 }}
                alt="user-logo"
              />
            </a>
            <ul className="dropdown-menu">
              <li>
                <a className="dropdown-item text-body-secondary" href="#">
                  Cart
                </a>
              </li>
              <li>
                <a className="dropdown-item text-body-secondary" href="#">
                  Change password
                </a>
              </li>
              <li>
                <a className="dropdown-item text-body-secondary" href="#">
                  Update information
                </a>
              </li>
              <li>
                <a className="dropdown-item text-body-secondary" href="#">
                  Sign out
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
  );
}

import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useContext } from "react";
import { AuthContext } from "./AuthContext";
import Logout from './Logout';

function NavBar() {
  const { isLoggedIn } = useContext(AuthContext);
  const userID = '30';

  return (
    <nav className="navbar navbar-expand navbar-light bg-light">
      <div className="container">
        <Link className="navbar-brand" to="#">Twitter 2</Link>
        <ul>
          <li className="nav-item">
            <Link to="/">Home</Link>
          </li>
          <li className="nav-item">
            <Link to={`/profile/${userID}`}>Profile</Link>
          </li>
          <li className="nav-item">
            <Link to="/signup">Signup</Link>
          </li>
          <li className="nav-item">
            {isLoggedIn ? (
              <Logout />
            ) : (
              <Link to="/login">Login</Link>
            )}
          </li>
          <li className="nav-item">
            <Link to="/feed">Feed</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;

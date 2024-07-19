import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useContext } from "react";
import { AuthContext } from "./AuthContext";
import Logout from './Logout';
import Cookies from "js-cookie";

function NavBar() {
  const { isLoggedIn } = useContext(AuthContext);

  const userID = Cookies.get("userId");

  return (
    <nav className="navbar navbar-expand navbar-light bg-light">
      <div className="container">
        <Link className="navbar-brand" to="#">Twitter 2</Link>
        <ul>
          <li className="nav-item">
            <Link to="/">Home</Link>
          </li>
          {userID && (
            <>
              <li className="nav-item">
                <Link to={`/profile/${userID}`}>Profile</Link>
              </li>
              <li className="nav-item">
                <Link to="/feed">Feed</Link>
              </li>
            </>
          )}
          <li className="nav-item">
            <Link to="/signup">Signup</Link>
          </li>
          <li className="nav-item">
            {Cookies.get('username') ? (
              <Logout/>
            ) : (
              <Link to="/login">Login</Link>
            )}
          </li>
          <li className="nav-item">
            <Link to="/CreateGroup">Create Group</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;

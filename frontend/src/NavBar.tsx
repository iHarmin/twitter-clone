import {Link} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useContext, useState} from "react";
import {AuthContext} from "./AuthContext.tsx";
import { useCookies } from "react-cookie";

function NavBar() {
  const { isLoggedIn } = useContext(AuthContext);
  const [cookies] = useCookies(['user']);
  // const userID = cookies.user;
  // TODO: placeholder for user id until cookie is implemented
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
              <Link to="/login">Logout</Link>
            ) : (
              <Link to="/login">Login</Link>
            )}
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;

import {Link} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useContext, useState} from "react";
import {AuthContext} from "./AuthContext.tsx";

function NavBar() {
  const { isLoggedIn } = useContext(AuthContext);

  return (
    <nav className="navbar navbar-expand navbar-light bg-light">
      <div className="container">
        <Link className="navbar-brand" to="#">Twitter 2</Link>
        <ul>
          <li className="nav-item">
            <Link to="/">Home</Link>
          </li>
          <li className="nav-item">
            <Link to="/profile/currentName">Profile</Link>
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
          <li className="nav-item">
            <Link to="/feed">Feed</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;

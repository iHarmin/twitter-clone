import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';
import Cookies from 'js-cookie';

const Logout = () => {
  const { setIsLoggedIn } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    Cookies.remove('userId'); // Remove the cookie
    Cookies.remove('username');
    setIsLoggedIn(false);
    navigate('/'); // Redirect to the home page or login page
    console.log('Logged out, dispatching Cookie Monster to eat your cookies! Nom nom nom!');
    alert('You have been logged out! Please reload page');
  };

  return (
    <button onClick={handleLogout}>
      Logout
    </button>
  );
};

export default Logout;

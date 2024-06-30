import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';
import Cookies from 'js-cookie';

const Logout = () => {
  const { setIsLoggedIn } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    Cookies.remove('authToken'); // Remove the cookie
    setIsLoggedIn(false);
    navigate('/'); // Redirect to the home page or login page
    console.log('Logged out, dispatching Cookie Monster to eat your cookies! Nom nom nom!'); 
  };

  return (
    <button onClick={handleLogout}>
      Logout
    </button>
  );
};

export default Logout;

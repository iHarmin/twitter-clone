// components/Login.js

import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';
import Cookies from 'js-cookie';

const Login = () => {
  const { setIsLoggedIn } = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();

    try {
      const formData = new URLSearchParams();
      formData.append('email', email);
      formData.append('password', password);

      const serverResponse = await fetch('http://localhost:8080/api/users/checkPasswordValid', {
        method: "POST",
        headers: {
          "Content-Type": 'application/x-www-form-urlencoded',
        },
        body: formData
      });

      if (!serverResponse.ok) {
        throw new Error('Login failed');
      }

      const result = await serverResponse.json();
      console.log(result);
      console.log('Success:', result);

      // Check if the request is pending
      if (result.requestStatus === 'PENDING') {
        alert('Your request is pending. Please wait for approval before logging in.');
        return;
      }

      if (result.requestStatus === 'REJECTED') {
        alert('Your request is rejected. You cannot able to login');
        return;
      }

      // Handle the admin check
      if (email === 'christian.simoneau@dal.ca' || email === 'moses.tong@dal.ca' || email === 'harmin.patel@dal.ca'
                || email === 'maitri.vasoya@dal.ca' || email === 'vraj.patel@dal.ca' || email === 'simon.losier@dal.ca') {
        Cookies.set('adminEmail', result.email);
      }
      
      Cookies.set('authToken', result.authToken); // Set the cookie
      Cookies.set('userId', result.id);
      Cookies.set('username', result.userName);
      Cookies.set('role', result.role); // Set the user role
      setIsLoggedIn(true);

      if (result.role === 'Admin') {
        navigate(`/profile/${result.id}`);// Navigate to the pending requests page if admin
      } else {
        navigate(`/profile/${result.id}`); // Redirect to the user's profile page if not admin
      }

    } catch (error) {
      console.error(error);
      alert('An error occurred during login.');
    }
  };

  return (
    <div className="container">
      <form onSubmit={handleLogin}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
        <a href="/forgotpassword">Forgot Password?</a>
      </form>
    </div>
  );
};

export default Login;

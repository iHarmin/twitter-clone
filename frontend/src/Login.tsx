import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';
import Cookies from 'js-cookie';

const Login = () => {
  const { setIsLoggedIn } = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
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

      console.log(serverResponse);

      if (!serverResponse.ok) {
        throw new Error('Login failed');
      }

      const result = await serverResponse.json();

      if (result.requestStatus === 'PENDING') {
        alert('Your request is pending approval. Please wait until your request is approved.');
        return;
      }

      if (result.requestStatus === 'REJECTED') {
        alert('Your request has been rejected. Please contact support.');
        return;
      }

      // For approved requests
      Cookies.set('authToken', result.authToken); // Set the cookie
      Cookies.set('userId', result.id);
      Cookies.set('username', result.userName);
      setIsLoggedIn(true);
      navigate(`/profile/${result.id}`); // Redirect to the user's profile page

    } catch (error) {
      console.error(error);
      alert('An error occurred during login.');
    }
  };

  return (
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
  );
};

export default Login;

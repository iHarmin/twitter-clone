import { useState } from 'react';
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';
import './App.css';

const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    // Simulate login logic and set a cookie
    if (email && password) {
      // Set a cookie for the logged-in user
      Cookies.set('user', email, { expires: 1 }); 
      setIsLoggedIn(true);
    } else {
      console.log('Invalid login attempt');
    }
  };

  const handleLogout = () => {
    // Remove the user cookie
    Cookies.remove('user');
    console.log('User logged out');
    setIsLoggedIn(false);
  };

  return (
    <div className="container">
      <h1>Twitter 2 Login</h1>
      {isLoggedIn ? (
        <div>
          <p>Welcome, {email}</p>
          <button onClick={handleLogout}>Logout</button>
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={handleEmailChange}
            required
          />
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            required
          />
          <button type="submit">Sign In</button>
          <Link to="/forgot-password">Forgot Password?</Link>
        </form>
      )}
    </div>
  );
}

export default Login;

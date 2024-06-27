import React from 'react';
import { Link } from 'react-router-dom';

const ErrorPage: React.FC = () => {
  return (
    <div className="error-container">
      <h2>You must be signed in to use this platform.</h2>
      <p>Please <Link to="/login">sign in</Link> to access this feature.</p>
    </div>
  );
};

export default ErrorPage;

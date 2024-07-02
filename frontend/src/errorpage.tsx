import React from 'react';
import { Link } from 'react-router-dom';

const ErrorPage: React.FC = () => {
  return (
    <div className="error-container">
      <h2>You must be signed in to use Twitter 2.</h2>
      <p>Please <Link to="/login">sign in</Link> here.</p>
    </div>
  );
};
export default ErrorPage;

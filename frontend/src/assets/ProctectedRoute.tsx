import { Navigate } from 'react-router-dom';
import Cookies from 'js-cookie';

const ProtectedRoute = ({ element: Component, ...rest }) => {
  const isAuth = Cookies.get('authToken'); // this should only allow access to the profile page if the user is logged in
    // Source: https://stackoverflow.com/questions/73161123/uncaught-error-protectedroute-is-not-a-route-component-all-component-child
    // used for helping to understand how to use the ProtectedRoutes to protect the profile page
  return isAuth ? <Component {...rest} /> : <Navigate to="/login" />;
};

export default ProtectedRoute;

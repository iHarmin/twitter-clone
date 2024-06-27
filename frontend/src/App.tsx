import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProfilePage from './ProfilePage';
import NavBar from './NavBar';
import 'bootstrap/dist/css/bootstrap.min.css'
import Login from "./Login.tsx";
import Signup from "./Signup.tsx";
import ForgotPassword from "./ForgotPassword";

function App() {
  return (
    <Router>
      <NavBar />
      <div>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/profile/:username" element={<ProfilePage />} />
          <Route path="/Forgot password" element={<ForgotPassword />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

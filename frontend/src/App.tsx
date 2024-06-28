import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import ProfilePage from './ProfilePage';
import NavBar from './NavBar';
import 'bootstrap/dist/css/bootstrap.min.css'
import Login from "./Login.tsx";
import Signup from "./Signup.tsx";
import Home from "./Home.tsx";
import ForgotPassword from "./ForgotPassword";
import {AuthContext} from './AuthContext';
import {useState} from 'react';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <AuthContext.Provider value={{isLoggedIn, setIsLoggedIn}}>
      <Router>
        <NavBar/>
        <div>
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/signup" element={<Signup/>}/>
            <Route path="/forgot-password" element={<ForgotPassword/>}/>
            <Route path="/profile/:username" element={<ProfilePage/>}/>
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;

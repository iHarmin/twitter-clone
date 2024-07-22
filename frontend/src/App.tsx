import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProfilePage from './ProfilePage';
import NavBar from './NavBar';
import 'bootstrap/dist/css/bootstrap.min.css'
import Login from "./Login.tsx";
import Signup from "./Signup.tsx";
// import Home from "./Home.tsx";
import ForgotPassword from "./ForgotPassword";
import ErrorPage from './errorpage.tsx';
import {AuthContext} from './AuthContext';
import  Feed from './Feed.tsx'
import {useState} from 'react';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <AuthContext.Provider value={{isLoggedIn, setIsLoggedIn}}>
      <Router>
        <NavBar/>
        <div>
          <Routes>
            <Route path="/" element={<div />}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/signup" element={<Signup/>}/>
            <Route path="/forgotpassword" element={<ForgotPassword/>}/>
            <Route path="/profile/:profileID" element={<ProfilePage/>}/>
            <Route path="/feed" element={<Feed />} />
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;

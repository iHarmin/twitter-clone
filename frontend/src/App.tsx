
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import { AuthContext } from './AuthContext.tsx';
import ChangeRole from './ChangeRole';
import Feed from './Feed.tsx';
import ForgotPassword from "./ForgotPassword";
import Login from "./Login.tsx";
import NavBar from './NavBar';
import ProfilePage from './ProfilePage';
import Signup from "./Signup.tsx";
import AddPerson from './addPerson.tsx';
import ErrorPage from './errorpage.tsx';
import RemovePerson from './removePerson.tsx';
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
            <Route path="/addPerson" element={<AddPerson />}/>
            <Route path="/removePerson" element={<RemovePerson />}/>
            <Route path="/changeRole" element={<ChangeRole />} />
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;

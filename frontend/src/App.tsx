import { useState } from 'react';

import {
  BrowserRouter as Router,
  Route,
  Routes,
} from 'react-router-dom';

import { AuthContext } from './AuthContext';
import CreateGroup from './CreateGroup.tsx';
import ErrorPage from './errorpage.tsx';
import Feed from './Feed.tsx';
import ForgotPassword from './ForgotPassword';
import GroupDetail from './GroupDetail.tsx';
import Login from './Login.tsx';
import NavBar from './NavBar';
import ProfilePage from './ProfilePage';
import Signup from './Signup.tsx';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>
      <Router>
        <NavBar />
        <div>
          <Routes>
            <Route path="/" element={<div />} />
            <Route path="/login" element={<Login />} />
            <Route path="/CreateGroup" element={<CreateGroup />} />
            <Route path="/group/:id" element={<GroupDetail />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/forgotpassword" element={<ForgotPassword />} />
            <Route path="/profile/:profileID" element={<ProfilePage />} />
            <Route path="/feed" element={<Feed />} />
            <Route path="*" element={<ErrorPage />} />
            <Route path="/addPerson" element={<AddPerson />}/>
            <Route path="/removePerson" element={<RemovePerson />}/>
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProfilePage from './ProfilePage';
import NavBar from './NavBar';

function App() {
  return (
    <Router>
      <NavBar />
      <div>
        <Routes>
          <Route path="/profile" element={<ProfilePage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

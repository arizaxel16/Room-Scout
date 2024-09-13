import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './pages/LandingPage/LandingPage';
import LoginRegister from './pages/LoginRegister/LoginRegister'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/user_auth" element={<LoginRegister />} />
      </Routes>
    </Router>
  );
}

export default App;

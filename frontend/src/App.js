import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './pages/LandingPage/LandingPage';
import LoginRegister from './pages/LoginRegister/LoginRegister'
import { ThemeProvider } from "./context/ThemeContext";

function App() {
  return (
    <ThemeProvider>
      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/user_auth" element={<LoginRegister />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;

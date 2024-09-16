import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './pages/LandingPage/LandingPage';
import LoginRegister from './pages/LoginRegister/LoginRegister'
import AdminHomePage from "./pages/AdminHomePage/AdminHomePage";
import UserAdminPage from "./pages/UserAdminPage/UserAdminPage";
import HotelAdminPage from "./pages/HotelAdminPage/HotelAdminPage";
import RoomAdminPage from "./pages/RoomAdminPage/RoomAdminPage";
import AddOnAdminPage from "./pages/AddOnAdminPage/AddOnAdminPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/admin" element={<AdminHomePage />} />
        <Route path="/admin/users" element={<UserAdminPage />} />
        <Route path="/admin/hotels" element={<HotelAdminPage />} />
        <Route path="/admin/rooms" element={<RoomAdminPage />} />
        <Route path="/admin/addOns" element={<AddOnAdminPage />} />
        <Route path="/" element={<LandingPage />} />
        <Route path="/user_auth" element={<LoginRegister />} />
        </Routes>
    </Router>
  );
}

export default App;

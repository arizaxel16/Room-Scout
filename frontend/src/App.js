import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './pages/LandingPage/LandingPage';
import LoginRegister from './pages/LoginRegister/LoginRegister'
import AdminHomePage from "./pages/AdminHomePage/AdminHomePage";
import UserAdminPage from "./pages/UserAdminPage/UserAdminPage";
import PropertiesAdminPage from "./pages/PropertiesAdminPage/PropertiesAdminPage";
import RoomAdminPage from "./pages/RoomAdminPage/RoomAdminPage";
import AddOnAdminPage from "./pages/AddOnAdminPage/AddOnAdminPage";
import BookingAdminPage from "./pages/BookingAdminPage/BookingAdminPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/admin" element={<AdminHomePage />} />
        <Route path="/admin/users" element={<UserAdminPage />} />
        <Route path="/admin/hotels" element={<PropertiesAdminPage />} />
        <Route path="/admin/rooms" element={<RoomAdminPage />} />
        <Route path="/admin/addOns" element={<AddOnAdminPage />} />
        <Route path="/admin/bookings" element={<BookingAdminPage />} />
        <Route path="/" element={<LandingPage />} />
        <Route path="/user_auth" element={<LoginRegister />} />
        </Routes>
    </Router>
  );
}

export default App;

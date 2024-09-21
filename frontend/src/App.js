import React from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import Home from "./pages/Home/Home";
import Auth from "./pages/Auth/Auth";
import AdminPanel from "./pages/AdminPanel/AdminPanel";
import PropertyRoomBooking from "./pages/PropertyRoomBooking/PropertyRoomBooking";
import { ThemeProvider } from "./context/ThemeContext";

function App() {
    return (
        <ThemeProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<Navigate to="/home" />} />
                    <Route path="/admin/*" element={<AdminPanel />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/rooms/:hotelId" element={<PropertyRoomBooking />} />
                    <Route path="/user_auth" element={<Auth />} />
                </Routes>
            </Router>
        </ThemeProvider>
    );
}

export default App;

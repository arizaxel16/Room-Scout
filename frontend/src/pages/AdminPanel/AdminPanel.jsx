import React from 'react';
import './AdminPanel.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import { Routes, Route } from 'react-router-dom';
import UserCRUD from '../../components/DynamicCrud/UserCRUD';
// import AdminCRUD from '../../components/DynamicCrud/AdminCRUD';
import PropertyCRUD from '../../components/DynamicCrud/PropertyCRUD';
import RoomCRUD from '../../components/DynamicCrud/RoomCRUD';
import AddOnCRUD from '../../components/DynamicCrud/AddOnCRUD';
import BookingCRUD from '../../components/DynamicCrud/BookingCRUD';
// import Placeholder from '../../components/Placeholder/Placeholder';

const AdminPanel = () => {
    return (
        <div className="main">
            <AdminNavBar />
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu />
                </div>
                <div className="contentContainer">
                    <Routes>
                        <Route path="/" element={<Placeholder title="Dashboard" />} />
                        {/* <Route path="/admins" element={<AdminCRUD />} /> */}
                        <Route path="/users" element={<UserCRUD />} />
                        <Route path="/properties" element={<PropertyCRUD />} />
                        <Route path="/rooms" element={<RoomCRUD />} />
                        <Route path="/addons" element={<AddOnCRUD />} />
                        <Route path="/bookings" element={<BookingCRUD />} />
                        {/* <Route path="/reports" element={<Placeholder title="Reports" />} /> */}
                        {/* <Route path="/posts" element={<Placeholder title="Posts" />} /> */}
                    </Routes>
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default AdminPanel;

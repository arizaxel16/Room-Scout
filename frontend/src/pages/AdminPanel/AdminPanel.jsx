import React from 'react';
import './AdminPanel.scss';
import AdminNavBar from "../../components/AdminPanel/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminPanel/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminPanel/AdminFooter/AdminFooter";
import { Routes, Route, Navigate } from 'react-router-dom';

import UserCRUD from '../../components/AdminPanel/DynamicCRUD/CRUD/UserCRUD';
import PropertyCRUD from '../../components/AdminPanel/DynamicCRUD/CRUD/PropertyCRUD';
import RoomTypeCRUD from '../../components/AdminPanel/DynamicCRUD/CRUD/RoomTypeCRUD';
import AddOnCRUD from '../../components/AdminPanel/DynamicCRUD/CRUD/AddOnCRUD';
import BookingCRUD from '../../components/AdminPanel/DynamicCRUD/CRUD/BookingCRUD';
import AdminStatView from '../../components/AdminPanel/AdminStatView/AdminStatView';

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
                        <Route path="/" element={<Navigate to="/admin/users" />} />
                        <Route path="panel" element={<AdminStatView />} />
                        <Route path="users" element={<UserCRUD />} />
                        <Route path="properties" element={<PropertyCRUD />} />
                        <Route path="rooms" element={<RoomTypeCRUD />} />
                        <Route path="addons" element={<AddOnCRUD />} />
                        <Route path="bookings" element={<BookingCRUD />} />
                    </Routes>
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default AdminPanel;

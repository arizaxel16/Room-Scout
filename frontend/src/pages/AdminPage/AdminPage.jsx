import React from 'react';
import './AdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";

const AdminPage = () => {
    return (
        <div className="main">
            <AdminNavBar />
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu />
                </div>
                <div className="contentContainer">
                    {/* ROUTE ADMIN CRUD COMPONENTS */}
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default AdminPage;






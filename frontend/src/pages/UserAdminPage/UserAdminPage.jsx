import React from 'react';
import './UserAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";

const UserAdminPage = () => {
    return (
        <div className="main">
            <AdminNavBar/>
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu/>
                </div>
                <div className="contentContainer">
                    User
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default UserAdminPage;

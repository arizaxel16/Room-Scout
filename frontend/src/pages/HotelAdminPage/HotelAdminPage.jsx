import React from 'react';
import './HotelAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";

const HotelAdminPage = () => {
    return (
        <div className="main">
            <AdminNavBar/>
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu/>
                </div>
                <div className="contentContainer">
                    Hotel
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default HotelAdminPage;

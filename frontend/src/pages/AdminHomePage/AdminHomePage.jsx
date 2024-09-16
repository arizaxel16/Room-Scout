import React from 'react';
import {Link, Outlet} from 'react-router-dom';
import './AdminHomePage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import AdminMenu from "../../components/AdminMenu/AdminMenu";


const AdminHomePage = () => {
    return (
        <div className="main">
            <AdminNavBar/>
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu/>
                </div>
                <div className="contentContainer">
                    HomePage
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default AdminHomePage;

import React from 'react';
import './AdminNavBar.scss';
import { CiSearch } from "react-icons/ci";
import { IoAppsSharp } from "react-icons/io5";
import { FaExpand } from "react-icons/fa";
import { IoMdSettings, IoMdNotifications  } from "react-icons/io";
import Logo from '../../Generic/Logo/Logo'

const AdminNavBar = () => {
    return (
        <div className="navbarAdmin">
            <div className="logo">
                <Logo />
                <span>AdminPanel</span>
            </div>
            <div className="icons">
                <CiSearch className="icon"/>
                <IoAppsSharp className="icon"/>
                <FaExpand className="icon"/>
                <div className="notification">
                    <IoMdNotifications className="icon"/>
                    <span>1</span>
                </div>
                <div className="user">
                    <img src="https://images.pexels.com/photos/11038549/pexels-photo-11038549.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load" alt="user"/>
                    <span>John Doe</span>
                </div>
                <IoMdSettings className="icon"/>
            </div>
        </div>
    );
};
export default AdminNavBar;
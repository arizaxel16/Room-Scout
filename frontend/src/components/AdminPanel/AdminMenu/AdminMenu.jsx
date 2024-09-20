import React from 'react';
import './AdminMenu.scss';
import { Link } from 'react-router-dom';

import { IoHomeOutline, IoBedOutline } from "react-icons/io5";
import { FaRegUser, FaHotel, FaChartLine } from "react-icons/fa";
import { FaUsers } from "react-icons/fa6";
import { IoIosWine } from "react-icons/io";
import { LuClipboardList } from "react-icons/lu";
import { BsFillFileEarmarkPostFill } from "react-icons/bs";

const menu = [
    {
        id: 1,
        title: "MAIN",
        listItems: [
            {
                id: 1,
                title: "Homepage",
                url: "/admin",
                icon: IoHomeOutline,
            },
            {
                id: 2,
                title: "Profile",
                url: "/admin/users/1",
                icon: FaRegUser,
            },
        ],
    },
    {
        id: 2,
        title: "LIST",
        listItems: [
            {
                id: 1,
                title: "Users",
                url: "/admin/users",
                icon: FaUsers,
            },
            {
                id: 2,
                title: "Properties",
                url: "/admin/hotels",
                icon: FaHotel,
            },
            {
                id: 3,
                title: "Room Types",
                url: "/admin/rooms",
                icon: IoBedOutline,
            },
            {
                id: 4,
                title: "Add-Ons",
                url: "/admin/addOns",
                icon: IoIosWine,
            },
            {
                id: 5,
                title: "Bookings",
                url: "/admin/bookings",
                icon: LuClipboardList,
            },
        ],
    },
    {
        id: 3,
        title: "ANALYTICS",
        listItems: [
            {
                id: 1,
                title: "Charts",
                url: "/admin",
                icon: FaChartLine,
            },
            {
                id: 2,
                title: "Logs",
                url: "/admin",
                icon: BsFillFileEarmarkPostFill,
            },
        ],
    },
];

const AdminMenu = () => {
    return (
        <div className="menu">
            {menu.map((menuItem) => (
                <div className="item" key={menuItem.id}>
                    <span className="title">{menuItem.title}</span>
                    {menuItem.listItems.map((listItem) => {
                        const IconComponent = listItem.icon;
                        return (
                            <Link to={listItem.url} className="listItem" key={listItem.id}>
                                {IconComponent && <IconComponent className="icon" />}
                                <span>{listItem.title}</span>
                            </Link>
                        );
                    })}
                </div>
            ))}
        </div>
    );
};

export default AdminMenu;

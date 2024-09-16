import React from 'react';
import './AdminMenu.scss';
import { Link } from 'react-router-dom';
import { IoHomeOutline, IoBedOutline } from "react-icons/io5";
import { FaRegUser, FaHotel, FaChartLine } from "react-icons/fa";
import { FaUsers } from "react-icons/fa6";
import { IoIosWine } from "react-icons/io";
import { LuClipboardList } from "react-icons/lu";
import { BsFillFileEarmarkPostFill } from "react-icons/bs";
import { menu } from "../../data.ts";

const iconMap = {
    IoHomeOutline: IoHomeOutline,
    FaRegUser: FaRegUser,
    FaUsers: FaUsers,
    FaHotel: FaHotel,
    IoBedOutline: IoBedOutline,
    IoIosWine: IoIosWine,
    LuClipboardList: LuClipboardList,
    FaChartLine: FaChartLine,
    BsFillFileEarmarkPostFill: BsFillFileEarmarkPostFill,
};

const AdminMenu = () => {
    return (
        <div className="menu">
            {menu.map((item) => (
                <div className="item" key={item.id}>
                    <span className="title">{item.title}</span>
                    {item.listItems.map((ListItem) => {
                        const IconComponent = iconMap[ListItem.icon];
                        return (
                            <Link to={ListItem.url} className="listItem" key={ListItem.id}>
                                {IconComponent && <IconComponent className="icon" />}
                                <span>{ListItem.title}</span>
                            </Link>
                        );
                    })}
                </div>
            ))}
        </div>
    );
};

export default AdminMenu;

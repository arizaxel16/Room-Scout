import React from 'react';
import './AdminHomePage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import TopBox from "../../components/TopBox/TopBox";
import ChartBox from "../../components/ChartBox/ChartBox";
import {chartBookings, chartBoxUser, chartRatio, chartRevenue} from "../../data.ts";


const AdminHomePage = () => {
    return (
        <div className="main">
            <AdminNavBar/>
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu/>
                </div>
                <div className="contentContainer">
                    <div className="home">
                        <div className="box box1"><TopBox/></div>
                        <div className="box box2"><ChartBox {...chartBoxUser}/></div>
                        <div className="box box3"><ChartBox {...chartBookings}/></div>
                        <div className="box box4">Box4</div>
                        <div className="box box5"><ChartBox{...chartRevenue}/></div>
                        <div className="box box6"><ChartBox{...chartRatio}/></div>
                        <div className="box box7">Box7</div>
                        <div className="box box8">Box8</div>
                        <div className="box box9">Box9</div>
                    </div>
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default AdminHomePage;

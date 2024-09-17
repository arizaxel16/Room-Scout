import React from 'react';
import './AdminHomePage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import TopBox from "../../components/TopBox/TopBox";
import ChartBox from "../../components/ChartBox/ChartBox";
import {chartBookings, chartBoxRevenue, chartBoxUser, chartBoxVisit, chartRatio, chartRevenue} from "../../data.ts";
import BarChartBox from "../../components/BarChartBox/BarChartBox";
import PieChartBox from "../../components/PieChartBox/PieChartBox";
import BigChartBox from "../../components/BigChartBox/BigChartBox";


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
                        <div className="box box4"><PieChartBox/></div>
                        <div className="box box5"><ChartBox{...chartRevenue}/></div>
                        <div className="box box6"><ChartBox{...chartRatio}/></div>
                        <div className="box box7"><BigChartBox/></div>
                        <div className="box box8"><BarChartBox{...chartBoxVisit}/></div>
                        <div className="box box9"><BarChartBox{...chartBoxRevenue}/></div>
                    </div>
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default AdminHomePage;

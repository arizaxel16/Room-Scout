import React from 'react';
import './ChartBox.scss';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Link } from 'react-router-dom';
import { FaUsersBetweenLines } from "react-icons/fa6";
import { LuClipboardList } from "react-icons/lu";
import { FaCoins } from "react-icons/fa";
import { MdAspectRatio } from "react-icons/md";

const ChartBox = (props) => {
    const { icon: Icon = FaUsersBetweenLines, title = "Default Title", number = "0", chartData = [], color = "#000", percentage = 0, dataKey = "value" } = props;

    return (
        <div className="chartBox">
            <div className="boxInfo">
                <div className="title">
                    <Icon className="icon" />
                    <span>{title}</span>
                </div>
                <h1>{number}</h1>
                <Link to="/admin" style={{ color: color }}>View all</Link>
            </div>
            <div className="chartInfo">
                <div className="chart">
                    <ResponsiveContainer width="99%" height="100%">
                        <LineChart data={chartData}>
                            <Tooltip
                                contentStyle={{ background: "transparent", border: "none" }}
                                labelStyle={{ display: "none" }}
                                position={{ x: 10, y: 60 }}
                            />
                            <Line
                                type="monotone"
                                dataKey={dataKey}
                                stroke={color}
                                strokeWidth={2}
                                dot={false}
                            />
                        </LineChart>
                    </ResponsiveContainer>
                </div>
                <div className="texts">
                    <span className="percentage" style={{ color: percentage < 0 ? "tomato" : "limegreen" }}>
                        {percentage}%
                    </span>
                    <span className="duration">this month</span>
                </div>
            </div>
        </div>
    );
};

export default ChartBox;

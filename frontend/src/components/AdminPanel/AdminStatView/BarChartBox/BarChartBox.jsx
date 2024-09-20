import React from 'react';
import './BarChartBox.scss';
import {Bar, BarChart, ResponsiveContainer, Tooltip} from "recharts";
import {FaUsersBetweenLines} from "react-icons/fa6";

const BarChartBox = (props) => {
    const { title = "Default Title", chartData = [], color = "#000", dataKey = "value" } = props;
    return (
        <div className="barChartBox">
            <h1>{title}</h1>
            <div className="chart">
                <ResponsiveContainer width="99%" height={150}>
                    <BarChart data={chartData}>
                        <Tooltip
                            contentStyle={{background: "#2a3447", borderRadius: "5px"}}
                            labelStyle={{display: "none"}}
                            cursor={{fill: "none"}}
                        />
                        <Bar dataKey={dataKey} fill={color} />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};
export default BarChartBox;
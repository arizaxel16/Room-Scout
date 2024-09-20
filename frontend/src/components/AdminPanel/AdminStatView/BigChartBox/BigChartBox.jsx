import React from 'react';
import './BigChartBox.scss';
import {Area, AreaChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";

const data = [
    {
        name: "Sun",
        Basic: 4000,
        Double: 2400,
        King: 2400,
    },
    {
        name: "Mon",
        Basic: 3000,
        Double: 1398,
        King: 2210,
    },
    {
        name: "Tue",
        Basic: 2000,
        Double: 9800,
        King: 2290,
    },
    {
        name: "Wed",
        Basic: 2780,
        Double: 3908,
        King: 2000,
    },
    {
        name: "Thu",
        Basic: 1890,
        Double: 4800,
        King: 2181,
    },
    {
        name: "Fri",
        Basic: 2390,
        Double: 3800,
        King: 2500,
    },
];


const BigChartBox = () => {
    return (
        <div className="bigChartBox">
            <h1>Revenue Analytics</h1>
            <div className="chart">
                <ResponsiveContainer width="99%" height="100%">
                    <AreaChart
                        data={data}
                        margin={{
                            top: 10,
                            right: 30,
                            left: 0,
                            bottom: 0,
                        }}
                    >
                        <XAxis dataKey="name" />
                        <YAxis />
                        <Tooltip />
                        <Area type="monotone" dataKey="Basic" stackId="1" stroke="#8884d8" fill="#8884d8" />
                        <Area type="monotone" dataKey="Double" stackId="1" stroke="#82ca9d" fill="#82ca9d" />
                        <Area type="monotone" dataKey="King" stackId="1" stroke="#ffc658" fill="#ffc658" />
                    </AreaChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};
export default BigChartBox;
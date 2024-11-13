import React from "react";
import "./AdminStatView.scss";
import TopBox from "./TopBox/TopBox";
import ChartBox from "./ChartBox/ChartBox";
import BarChartBox from "./BarChartBox/BarChartBox";
import PieChartBox from "./PieChartBox/PieChartBox";
import BigChartBox from "./BigChartBox/BigChartBox";
import {
	chartBookings,
	chartBoxRevenue,
	chartBoxUser,
	chartBoxVisit,
	chartRatio,
	chartRevenue,
} from "./data.ts";

const AdminStatView = () => {
	return (
		<div className="home">
			<div className="box box1">
				<TopBox />
			</div>
			<div className="box box2">
				<ChartBox {...chartBoxUser} />
			</div>
			<div className="box box3">
				<ChartBox {...chartBookings} />
			</div>
			<div className="box box4">
				<PieChartBox />
			</div>
			<div className="box box5">
				<ChartBox {...chartRevenue} />
			</div>
			<div className="box box6">
				<ChartBox {...chartRatio} />
			</div>
			<div className="box box7">
				<BigChartBox />
			</div>
			<div className="box box8">
				<BarChartBox {...chartBoxVisit} />
			</div>
			<div className="box box9">
				<BarChartBox {...chartBoxRevenue} />
			</div>
		</div>
	);
};

export default AdminStatView;

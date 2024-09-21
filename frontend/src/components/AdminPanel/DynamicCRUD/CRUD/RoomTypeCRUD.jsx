import React from "react";
import DynamicCrud from "../DynamicCRUD";

const columns = [
	{ field: "id", headerName: "ID", width: 90 },
	{ field: "name", headerName: "Name", width: 150, editable: true },
	{
		field: "guestCapacity",
		headerName: "Capacity",
		width: 150,
		editable: true,
	},
	{
		field: "numberOfBeds",
		headerName: "Beds",
		width: 150,
		type: "number",
		editable: true,
	},
	{
		field: "numberOfRooms",
		headerName: "Rooms",
		width: 150,
		type: "number",
		editable: true,
	},
	{
		field: "basePrice",
		headerName: "Price",
		width: 150,
		type: "number",
		editable: true,
	},
	{ field: "propertyId", headerName: "Property", width: 150, editable: true },
];

const formFields = [
	{ name: "name", label: "Name", type: "text" },
	{ name: "guestCapacity", label: "Capacity", type: "number" },
	{ name: "numberOfBeds", label: "Beds", type: "number" },
	{ name: "numberOfRooms", label: "Rooms", type: "number" },
	{ name: "basePrice", label: "Price", type: "number" },
	{ name: "propertyId", label: "Property", type: "text" },
];

const apiEndpoint = "http://157.173.114.224:8080/roomtypes";

const RoomTypeCRUD = () => {
	return (
		<DynamicCrud
			title="Room Type"
			columns={columns}
			apiEndpoint={apiEndpoint}
			formFields={formFields}
		/>
	);
};

export default RoomTypeCRUD;

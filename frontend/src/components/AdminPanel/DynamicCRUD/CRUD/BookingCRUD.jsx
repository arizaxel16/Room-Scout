import React from "react";
import DynamicCrud from "../DynamicCRUD";

const columns = [
	{ field: "id", headerName: "ID", width: 90 },
	{ field: "checkIn", headerName: "Check In", width: 150, type: "date", editable: true },
	{ field: "checkOut", headerName: "Check Out", width: 150, type: "date", editable: true },
	{ field: "totalPrice", headerName: "Amount", width: 150, type: "number", editable: true },
	{ field: "roomTypeId", headerName: "Property", width: 150, editable: true },
	{ field: "userId", headerName: "User", width: 150, editable: true },
];

const formFields = [
	{ name: "guestName", label: "Guest Name", type: "text" },
	{ name: "checkIn", label: "Check In", type: "date" },
	{ name: "checkOut", label: "Check Out", type: "date" },
	{ name: "amount", label: "Amount", type: "number" },
	{ name: "propertyId", label: "Property", type: "text" },
	{ name: "userId", label: "User", type: "text" },
];

const apiEndpoint = "http://157.173.114.224:8080/bookings";

const BookingCRUD = () => {
	return (
		<DynamicCrud
			title="Booking"
			columns={columns}
			apiEndpoint={apiEndpoint}
			formFields={formFields}
		/>
	);
};

export default BookingCRUD;

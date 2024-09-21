import React from "react";
import DynamicCrud from "../DynamicCRUD";

const columns = [
	{ field: "id", headerName: "ID", width: 90 },
	{ field: "email", headerName: "Email", width: 200, editable: true },
	{ field: "name", headerName: "Name", width: 150, editable: true },
	{
		field: "identification",
		headerName: "Identification",
		width: 150,
		editable: true,
	},
	{
		field: "phoneNumber",
		headerName: "Phone Number",
		width: 150,
		editable: true,
	},
];

const formFields = [
	{ name: "email", label: "Email", type: "text" },
	{ name: "name", label: "Name", type: "text" },
	{ name: "identification", label: "Identification", type: "text" },
	{ name: "phoneNumber", label: "Phone Number", type: "text" },
];

const apiEndpoint = "http://157.173.114.224:8080/users";

const UserCRUD = () => {
	return (
		<DynamicCrud
			title="User"
			columns={columns}
			apiEndpoint={apiEndpoint}
			formFields={formFields}
		/>
	);
};

export default UserCRUD;

import React from "react";
import DynamicCrud from "./DynamicCrud";

const columns = [
    { field: "id", headerName: "ID", width: 90 },
    { field: "name", headerName: "Name", width: 150, editable: true },
    { field: "address", headerName: "Address", width: 150, editable: true },
    { field: "country", headerName: "Country", width: 110, editable: true },
    { field: "city", headerName: "City", width: 110, editable: true },
    { field: "type", headerName: "Type", width: 110, editable: true }
];

const formFields = [
    { name: "name", label: "Name", type: "text" },
    { name: "address", label: "Address", type: "text" },
    { name: "country", label: "Country", type: "text" },
    { name: "city", label: "City", type: "text" },
    { name: "type", label: "Type", type: "text" }
];

const apiEndpoint = 'http://157.173.114.224:8080/properties';

const PropertyCRUD = () => {
    return (
        <DynamicCrud
        title="Property"
        columns={columns}
        apiEndpoint={apiEndpoint}
        formFields={formFields}
        />
    );
};

export default PropertyCRUD;

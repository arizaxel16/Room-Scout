import React, { useState, useEffect } from "react";
import "./RoomAdminPage.scss";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";
import axios from "axios";

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
        editable: true,
        type: "number",
    },
    {
        field: "numberOfRooms",
        headerName: "Rooms",
        width: 150,
        editable: true,
        type: "number",
    },
    {
        field: "basePrice",
        headerName: "Price",
        width: 150,
        editable: true,
        type: "number",
    },
    { field: "propertyId", headerName: "Property", width: 150, editable: true },
];

const RoomAdminPage = () => {
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState({
        name: "",
        guestCapacity: "",
        numberOfBeds: "",
        numberOfRooms: "",
        basePrice: "",
        propertyId: "",
    });
    const [rows, setRows] = useState([]);
    const [properties, setProperties] = useState([]);

    // Fetch rooms and add property names to the rows
    const fetchRooms = async () => {
        try {
        const response = await axios.get("http://157.173.114.224:8080/roomtypes");
        const rooms = response.data;
        const roomsWithPropertyNames = rooms.map((room) => ({
            ...room,
            propertyName:
            properties.find((property) => property.id === room.propertyId)
                ?.name || "Unknown",
        }));
        setRows(roomsWithPropertyNames);
        } catch (err) {
        console.error("Error fetching rooms:", err);
        }
    };

    // Fetch properties for the select field
    const fetchProperties = async () => {
        try {
        const response = await axios.get(
            "http://157.173.114.224:8080/properties"
        );
        setProperties(response.data);
        } catch (err) {
        console.error("Error fetching properties:", err);
        }
    };

    useEffect(() => {
        fetchProperties();
    }, []);

    useEffect(() => {
        if (properties.length > 0) {
        fetchRooms();
        }
    }, [properties]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevState) => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
        const response = await axios.post(
            "http://157.173.114.224:8080/roomtypes",
            formData
        );
        console.log("Room created successfully:", response.data);
        setOpen(false);
        fetchRooms(); // Refresh rooms after adding a new one
        } catch (err) {
        console.error("Error creating room:", err);
        }
    };

    return (
        <div className="rooms">
        <div className="info">
            <h1>Room Types</h1>
            <button onClick={() => setOpen(true)}>Add New Room Type</button>
        </div>
        <DataTable columns={columns} rows={rows} />
        {open && (
            <Add
            slug="rooms"
            columns={columns}
            formData={formData}
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            setOpen={setOpen}
            properties={properties} // Passing the properties for the select
            />
        )}
        </div>
    );
};

export default RoomAdminPage;

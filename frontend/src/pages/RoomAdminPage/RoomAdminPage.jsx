import React, { useState } from 'react';
import './RoomAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";
import axios from 'axios';

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'name', headerName: 'Name', width: 150, editable: true },
    { field: 'guestCapacity', headerName: 'Capacity', width: 150, editable: true,},
    { field: 'numberOfBeds', headerName: 'Beds', width: 150, editable: true, type: Number  },
    { field: 'basePrice', headerName: 'Price', width: 150, editable: true, type: Number},
    { field: 'propertyId', headerName: 'Property', width: 150, editable: true },
];

const rows = [
    { id: 1, name: 'Standard', guestCapacity: '2', numberOfBeds: '2', basePrice: '90000', propertyId: '1' },
];

const RoomAdminPage = () => {
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        guestCapacity: '',
        numberOfBeds: '',
        basePrice: '',
        propertyId: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/roomtypes', formData); 
            console.log('Room created successfully:', response.data);
            alert('Room created successfully!');
            setOpen(false);
        } catch (err) {
            console.error('Error creating room:', err);
            alert('Error creating room: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
        }
    };

    return (
        <div className="main">
            <AdminNavBar />
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu />
                </div>
                <div className="contentContainer">
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
                            />
                        )}
                    </div>
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default RoomAdminPage;



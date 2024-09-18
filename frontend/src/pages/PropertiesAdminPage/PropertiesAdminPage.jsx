import React, { useState } from 'react';
import './PropertiesAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";
import axios from 'axios';

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'name', headerName: 'Name', width: 150, editable: true },
    { field: 'address', headerName: 'Address', width: 150, editable: true },
    { field: 'country', headerName: 'Country', width: 110, editable: true },
    { field: 'city', headerName: 'City', width: 110, editable: true },
    { field: 'type', headerName: 'Type', width: 110, editable: true },
];

const rows = [
    { id: 1, name: 'Hilton Hotel', address: '123 Main St', country: 'USA', city: 'New York', type: 'Luxury' },
    { id: 2, name: 'Lannister Suites', address: '456 Kingsway Rd', country: 'UK', city: 'London', type: 'Boutique' },
    { id: 3, name: 'Golden Lion Inn', address: '789 Lionheart Ave', country: 'UK', city: 'Manchester', type: 'Inn' },
    { id: 4, name: 'Stark Lodge', address: '321 Winterfell Ln', country: 'Canada', city: 'Toronto', type: 'Motel' },
    { id: 5, name: 'Targaryen Resort', address: '456 Dragonstone Blvd', country: 'Spain', city: 'Barcelona', type: 'Resort' },
    { id: 6, name: 'Melisandre Retreat', address: '987 Mystic Rd', country: 'India', city: 'Rishikesh', type: 'Retreat' },
];

const PropertiesAdminPage = () => {
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        address: '',
        country: '',
        city: '',
        type: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/properties', formData);
            console.log('Property created successfully:', response.data);
            alert('Property created successfully!');
            setOpen(false);
        } catch (err) {
            console.error('Error creating property:', err);
            alert('Error creating property: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
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
                    <div className="properties">
                        <div className="info">
                            <h1>Properties</h1>
                            <button onClick={() => setOpen(true)}>Add New Property</button>
                        </div>
                        <DataTable columns={columns} rows={rows} />
                        {open && (
                            <Add
                                slug="properties"
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

export default PropertiesAdminPage;


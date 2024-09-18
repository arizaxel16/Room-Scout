import React, { useState, useEffect } from 'react';
import './AddOnAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";
import axios from 'axios';

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
        field: 'name',
        headerName: 'Name',
        width: 150,
        editable: true,
    },
    {
        field: 'price',
        headerName: 'Price',
        width: 150,
        type: 'number',
        editable: true,
    },
    { 
        field: 'propertyName', 
        headerName: 'Property', 
        width: 150, 
        editable: true,
    },
];

const AddOnAdminPage = () => {
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        price: '',
        propertyId: '',
    });
    const [rows, setRows] = useState([]);
    const [properties, setProperties] = useState([]);

    const fetchAddOns = async () => {
        try {
            const response = await axios.get('http://localhost:8080/addons');
            const addOns = response.data;
            const addOnsWithPropertyNames = addOns.map(addOn => ({
                ...addOn,
                propertyName: properties.find(property => property.id === addOn.propertyId)?.name || 'Unknown'
            }));
            setRows(addOnsWithPropertyNames);
        } catch (err) {
            console.error('Error fetching add-ons:', err);
            alert('Error fetching add-ons: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
        }
    };

    const fetchProperties = async () => {
        try {
            const response = await axios.get('http://localhost:8080/properties');
            setProperties(response.data);
        } catch (err) {
            console.error('Error fetching properties:', err);
            alert('Error fetching properties: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
        }
    };

    useEffect(() => {
        fetchProperties();
    }, []);

    useEffect(() => {
        fetchAddOns();
    }, [properties]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/addons', formData);
            console.log('Add-On created successfully:', response.data);
            alert('Add-On created successfully!');
            setOpen(false);
            fetchAddOns();
        } catch (err) {
            console.error('Error creating add-on:', err);
            alert('Error creating add-on: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
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
                    <div className="add-ons">
                        <div className="info">
                            <h1>Add Ons</h1>
                            <button onClick={() => setOpen(true)}>Add New Add-On</button>
                        </div>
                        <DataTable columns={columns} rows={rows} />
                        {open && (
                            <Add
                                slug="add-ons"
                                columns={columns}
                                formData={formData}
                                handleChange={handleChange}
                                handleSubmit={handleSubmit}
                                setOpen={setOpen}
                                properties={properties} 
                            />
                        )}
                    </div>
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default AddOnAdminPage;



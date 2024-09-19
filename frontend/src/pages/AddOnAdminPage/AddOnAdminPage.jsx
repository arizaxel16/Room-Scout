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
        field: 'propertyId', 
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
    const [errorMessage, setErrorMessage] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    const fetchAddOns = async () => {
        try {
            const response = await axios.get('http://157.173.114.224:8080/addons');
            const addOns = response.data;
            const addOnsWithPropertyNames = addOns.map(addOn => ({
                ...addOn,
                propertyName: properties.find(property => property.id === addOn.propertyId)?.name || 'Unknown'
            }));
            setRows(addOnsWithPropertyNames);
        } catch (err) {
            console.error('Error fetching add-ons:', err);
            setErrorMessage('Error fetching add-ons. Please try again later.');
        }
    };

    const fetchProperties = async () => {
        try {
            const response = await axios.get('http://157.173.114.224:8080/properties');
            setProperties(response.data);
        } catch (err) {
            console.error('Error fetching properties:', err);
            setErrorMessage('Error fetching properties. Please try again later.');
        }
    };

    useEffect(() => {
        fetchProperties();
    }, []);

    useEffect(() => {
        if (properties.length > 0) {
            fetchAddOns();
        }
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
            const response = await axios.post('http://157.173.114.224:8080/addons', formData);
            console.log('Add-On created successfully:', response.data);
            setSuccessMessage('Add-On created successfully!');
            setErrorMessage(null);
            setOpen(false);
            fetchAddOns();
        } catch (err) {
            console.error('Error creating add-on:', err);
            setErrorMessage('Error creating add-on. Please try again.');
            setSuccessMessage(null);
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



import React, { useState, useEffect, useCallback } from "react";
import DataTable from './DataTable/DataTable';
import Add from "./Add/Add";
import axios from "axios";
import './DynamicCRUD.scss'


const DynamicCrud = ({ title, columns, apiEndpoint, formFields }) => {
    const [open, setOpen] = useState(false);
    const [formData, setFormData] = useState({});
    const [rows, setRows] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [properties, setProperties] = useState([]);

    const fetchProperties = async () => {
        try {
            const response = await axios.get('http://157.173.114.224:8080/properties');
            setProperties(response.data);
        } catch (err) {
            console.error("Error fetching properties:", err);
        }
    };

    useEffect(() => {
        if (columns.some((column) => column.field === "propertyId")) {
            fetchProperties();
        }
    }, [columns]);

    useEffect(() => {
        const initialFormData = {};
        formFields.forEach((field) => (initialFormData[field.name] = ""));
        setFormData(initialFormData);
    }, [formFields]);

    const fetchRecords = useCallback(async () => {
        try {
            const response = await axios.get(apiEndpoint);
            setRows(response.data);
        } catch (err) {
            console.error(`Error fetching ${title.toLowerCase()}:`, err);
            setErrorMessage(`Error fetching ${title.toLowerCase()}. Please try again later.`);
        }
    }, [apiEndpoint, title]);

    useEffect(() => {
        fetchRecords();
    }, [fetchRecords]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevState) => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post(apiEndpoint, formData);
            setSuccessMessage(`${title} created successfully!`);
            setErrorMessage(null);
            setOpen(false);
            fetchRecords();
        } catch (err) {
            console.error(`Error creating ${title.toLowerCase()}:`, err);
            setErrorMessage(`Error creating ${title.toLowerCase()}. Please try again.`);
            setSuccessMessage(null);
        }
    };

    const handleUpdate = async (id, updatedData) => {
        try {
            await axios.put(`${apiEndpoint}/${id}`, updatedData);
            setSuccessMessage(`${title} updated successfully!`);
            setErrorMessage(null);
            fetchRecords();
        } catch (err) {
            console.error(`Error updating ${title.toLowerCase()}:`, err);
            setErrorMessage(`Error updating ${title.toLowerCase()}. Please try again.`);
            setSuccessMessage(null);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`${apiEndpoint}/${id}`);
            setSuccessMessage(`${title} deleted successfully!`);
            setErrorMessage(null);
            fetchRecords();
        } catch (err) {
            console.error(`Error deleting ${title.toLowerCase()}:`, err);
            setErrorMessage(`Error deleting ${title.toLowerCase()}. Please try again.`);
            setSuccessMessage(null);
        }
    };

    return (
        <div className="dynamic-crud">
            <div className="info">
                <h1>{title}</h1>
                <button onClick={() => setOpen(true)}>Add New {title}</button>
            </div>

            {errorMessage && <p className="error">{errorMessage}</p>}
            {successMessage && <p className="success">{successMessage}</p>}

            <DataTable
                columns={columns}
                rows={rows}
                handleUpdate={handleUpdate}
                handleDelete={handleDelete}
            />

            {open && (
                <Add
                    slug={title.toLowerCase()}
                    columns={columns}
                    formData={formData}
                    handleChange={handleChange}
                    handleSubmit={handleSubmit}
                    setOpen={setOpen}
                    properties={properties}
                />
            )}
        </div>
    );
};

export default DynamicCrud;

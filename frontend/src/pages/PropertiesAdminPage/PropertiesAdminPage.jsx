import React, { useState, useEffect } from 'react';
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

const PropertiesAdminPage = () => {
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    address: '',
    country: '',
    city: '',
    type: '',
  });
  const [rows, setRows] = useState([]);

  // Fetch properties from the API
  const fetchProperties = async () => {
    try {
      const response = await axios.get('http://localhost:8080/properties');
      setRows(response.data);
    } catch (err) {
      console.error('Error fetching properties:', err);
      alert('Error fetching properties: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
    }
  };

  useEffect(() => {
    fetchProperties();
  }, []);

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
      fetchProperties(); // Refresh the list after adding a new property
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



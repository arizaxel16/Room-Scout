import React, { useState } from 'react';
import './UserAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";
import axios from 'axios';

const columns = [
  { field: 'id', headerName: 'ID', width: 90 },
  { field: 'email', headerName: 'Email', width: 200, editable: true },
  { field: 'name', headerName: 'Name', width: 150, editable: true },
  { field: 'surname', headerName: 'Surname', width: 150, editable: true },
  { field: 'username', headerName: 'Username', width: 150, editable: true },
  { field: 'password', headerName: 'Password', width: 200, editable: true },
  {
    field: 'role',
    headerName: 'Role',
    width: 150,
    editable: true,
    renderCell: (params) => <span>{params.value}</span>,
  },
];

const rows = [
  { id: 1, email: 'jon.snow@nightswatch.org', name: 'Jon', surname: 'Snow', username: 'jsnow', password: 'password123', role: 'Admin' },
  { id: 2, email: 'cersei.lannister@casterlyrock.com', name: 'Cersei', surname: 'Lannister', username: 'clannister', password: 'queen456', role: 'User' },
  { id: 3, email: 'jaime.lannister@kingsguard.com', name: 'Jaime', surname: 'Lannister', username: 'jlannister', password: 'knight789', role: 'User' },
  { id: 4, email: 'arya.stark@winterfell.com', name: 'Arya', surname: 'Stark', username: 'astark', password: 'faceless321', role: 'Admin' },
];

const UserAdminPage = () => {
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    name: '',
    surname: '',
    password: '',
    role: 'Admin',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/users', formData);
      console.log('User created successfully:', response.data);
      alert('User created successfully!');
      setOpen(false);
    } catch (err) {
      console.error('Error creating user:', err);
      alert('Error creating user: ' + (err.response?.data?.message || err.message || 'Something went wrong'));
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
          <div className="users">
            <div className="info">
              <h1>Users</h1>
              <button onClick={() => setOpen(true)}>Add New Admin/User</button>
            </div>
            <DataTable columns={columns} rows={rows} />
            {open && (
              <Add
                slug="users"
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

export default UserAdminPage;





import React from 'react';
import './UserAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
        field: 'username',
        headerName: 'Username',
        width: 150,
        editable: true,
    },
    {
        field: 'email',
        headerName: 'Email',
        width: 200,
        editable: true,
    },
    {
        field: 'password',
        headerName: 'Password',
        width: 200,
        editable: true,
    },
    {
        field: 'rol',
        headerName: 'Role',
        width: 150,
        editable: true,
        renderCell: (params) => {
            // Solo permite "Admin" o "User" como rol
            const role = params.value === 'Admin' || params.value === 'User' ? params.value : 'User';
            return <span>{role}</span>;
        }
    },
];

const rows = [
    { id: 1, username: 'jsnow', email: 'jon.snow@nightswatch.org', password: 'password123', rol: 'Admin' },
    { id: 2, username: 'clannister', email: 'cersei.lannister@casterlyrock.com', password: 'queen456', rol: 'User' },
    { id: 3, username: 'jlannister', email: 'jaime.lannister@kingsguard.com', password: 'knight789', rol: 'User' }, // Cambiado a "User"
    { id: 4, username: 'astark', email: 'arya.stark@winterfell.com', password: 'faceless321', rol: 'Admin' },
    // Agregar más usuarios según sea necesario
];

const UserAdminPage = () => {
    const [open, setOpen] = React.useState(false);
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
                        {open && <Add slug="users" columns={columns} setOpen={setOpen} />}
                    </div>
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default UserAdminPage;


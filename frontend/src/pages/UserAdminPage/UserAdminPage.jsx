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
        field: 'email',
        headerName: 'Email',
        width: 200,
        editable: true,
    },
    {
        field: 'name',
        headerName: 'Name',
        width: 150,
        editable: true,
    },
    {
        field: 'surname',
        headerName: 'Surname',
        width: 150,
        editable: true,
    },
    {
        field: 'username',
        headerName: 'Username',
        width: 150,
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
            const role = params.value === 'Admin' || params.value === 'User' ? params.value : 'User';
            return <span>{role}</span>;
        }
    },
];

const rows = [
    { id: 1, email: 'jon.snow@nightswatch.org', name: 'Jon', surname: 'Snow', username: 'jsnow', password: 'password123', rol: 'Admin' },
    { id: 2, email: 'cersei.lannister@casterlyrock.com', name: 'Cersei', surname: 'Lannister', username: 'clannister', password: 'queen456', rol: 'User' },
    { id: 3, email: 'jaime.lannister@kingsguard.com', name: 'Jaime', surname: 'Lannister', username: 'jlannister', password: 'knight789', rol: 'User' },
    { id: 4, email: 'arya.stark@winterfell.com', name: 'Arya', surname: 'Stark', username: 'astark', password: 'faceless321', rol: 'Admin' },
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



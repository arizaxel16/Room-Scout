import React from 'react';
import './RoomAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
        field: 'name',
        headerName: 'Name',
        width: 150,
        editable: true,
    },

    {
        field: 'capacity',
        headerName: 'capacity',
        width: 150,
        type: 'number',
        editable: true,
    },
];
const rows = [
    {id: 1, name: 'Standard', capacity: '2'},
    {id: 2, name: 'Deluxe', capacity: '2'},
    {id: 3, name: 'Penthouse', capacity: '7'},
    {id: 4, name: 'King Suite', capacity: '3'},
    {id: 5, name: 'Family Suite', capacity: '4'},
];

const RoomAdminPage = () => {
    const [open, setOpen] = React.useState(false);
    return (
        <div className="main">
            <AdminNavBar/>
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu/>
                </div>
                <div className="contentContainer">
                    <div className="rooms">
                        <div className="info">
                            <h1>Room Types</h1>
                            <button onClick={() => setOpen(true)}>Add New Room Type</button>
                        </div>
                        <DataTable columns={columns} rows={rows}/>
                        {open && <Add slug="rooms" columns={columns} setOpen={setOpen}/>}
                    </div>
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default RoomAdminPage;

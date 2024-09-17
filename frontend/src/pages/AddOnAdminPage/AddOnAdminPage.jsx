import React from 'react';
import './AddOnAdminPage.scss';
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
        field: 'price',
        headerName: 'Price',
        width: 150,
        type: 'number',
        editable: true,
    },
];
const rows = [
    { id: 1, name: 'Breakfast Included', price: '10' },
    { id: 2, name: 'Spa Access', price: '25' },
    { id: 3, name: 'Airport Shuttle', price: '20' },
    { id: 4, name: 'Late Checkout', price: '15' },
    { id: 5, name: 'Parking', price: '12' },

];

const AddOnAdminPage = () => {
    const [open, setOpen] = React.useState(false);
    return (
        <div className="main">
            <AdminNavBar/>
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu/>
                </div>
                <div className="contentContainer">
                    <div className="add-ons">
                        <div className="info">
                            <h1>Add Ons</h1>
                            <button onClick={() => setOpen(true)}>Add New Add-On</button>
                        </div>
                        <DataTable columns={columns} rows={rows}/>
                        {open && <Add slug="add-ons" columns={columns} setOpen={setOpen}/>}
                    </div>
                </div>
            </div>
            <AdminFooter/>
        </div>
    );
};

export default AddOnAdminPage;

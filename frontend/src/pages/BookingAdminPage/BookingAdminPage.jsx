import React from "react";
import "./BookingAdminPage.scss";
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import Add from "../../components/Add/Add.tsx";

const columns = [
{ field: "id", headerName: "ID", width: 90 },
{
    field: "guestName",
    headerName: "Guest Name",
    width: 150,
    editable: false,
},
{
    field: "roomType",
    headerName: "Room Type",
    width: 150,
    editable: false,
},
{
    field: "checkInDate",
    headerName: "Check-in Date",
    width: 150,
    type: "date",
    editable: false,
},
{
    field: "checkOutDate",
    headerName: "Check-out Date",
    width: 150,
    type: "date",
    editable: false,
},
{
    field: "pricePerNight",
    headerName: "Price per Night",
    width: 150,
    type: "number",
    editable: false,
},
{
    field: "totalPrice",
    headerName: "Total Price",
    width: 150,
    type: "number",
    editable: false,
},
{
    field: "status",
    headerName: "Booking Status",
    width: 150,
    editable: true,
},
];

const rows = [
{
    id: 1,
    guestName: "John Doe",
    roomType: "Deluxe Suite",
    checkInDate: new Date("2023-05-01"),
    checkOutDate: new Date("2023-05-05"),
    pricePerNight: 200,
    totalPrice: 1000,
    status: "Confirmed",
},
{
    id: 2,
    guestName: "Jane Smith",
    roomType: "Standard Room",
    checkInDate: new Date("2023-05-10"),
    checkOutDate: new Date("2023-05-15"),
    pricePerNight: 150,
    totalPrice: 750,
    status: "Confirmed",
},
{
    id: 3,
    guestName: "Emily Johnson",
    roomType: "Suite",
    checkInDate: new Date("2023-06-01"),
    checkOutDate: new Date("2023-06-07"),
    pricePerNight: 250,
    totalPrice: 1500,
    status: "Pending",
},
{
    id: 4,
    guestName: "Michael Brown",
    roomType: "King Suite",
    checkInDate: new Date("2023-06-15"),
    checkOutDate: new Date("2023-06-20"),
    pricePerNight: 300,
    totalPrice: 1500,
    status: "Cancelled",
},
{
    id: 5,
    guestName: "Sara Wilson",
    roomType: "Standard Room",
    checkInDate: new Date("2023-07-05"),
    checkOutDate: new Date("2023-07-10"),
    pricePerNight: 150,
    totalPrice: 750,
    status: "Confirmed",
},
{
    id: 6,
    guestName: "David Lee",
    roomType: "Family Suite",
    checkInDate: new Date("2023-07-20"),
    checkOutDate: new Date("2023-07-25"),
    pricePerNight: 180,
    totalPrice: 900,
    status: "Confirmed",
},
];

const BookingAdminPage = () => {
    const [open, setOpen] = React.useState(false);
    return (
        <div className="booking">
        <div className="info">
            <h1>Booking</h1>
            <button onClick={() => setOpen(true)}>Add New Booking</button>
        </div>
        <DataTable columns={columns} rows={rows} />
        {open && <Add slug="add-ons" columns={columns} setOpen={setOpen} />}
        </div>
    );
};

export default BookingAdminPage;

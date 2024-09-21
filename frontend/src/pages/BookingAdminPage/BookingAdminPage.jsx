import React, { useState, useEffect } from 'react';
import './BookingAdminPage.scss';
import AdminNavBar from "../../components/AdminNavBar/AdminNavBar";
import AdminMenu from "../../components/AdminMenu/AdminMenu";
import AdminFooter from "../../components/AdminFooter/AdminFooter";
import DataTable from "../../components/DataTable/DataTable";
import axios from 'axios';

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
        field: 'guestName',
        headerName: 'Guest Name',
        width: 150,
        editable: false,
    },
    {
        field: 'roomType',
        headerName: 'Room Type',
        width: 150,
        editable: false,
    },
    {
        field: 'checkInDate',
        headerName: 'Check-in Date',
        width: 150,
        type: 'date',
        editable: false,
    },
    {
        field: 'checkOutDate',
        headerName: 'Check-out Date',
        width: 150,
        type: 'date',
        editable: false,
    },
    {
        field: 'totalPrice',
        headerName: 'Total Price',
        width: 150,
        type: 'number',
        editable: false,
    },
    {
        field: 'status',
        headerName: 'Booking Status',
        width: 150,
        editable: true,
    },
];

const BookingAdminPage = () => {
    const [rows, setRows] = useState([]);

    // Fetch bookings from the API
    const fetchBookings = async () => {
        try {
            const response = await axios.get('http://157.173.114.224:8080/bookings');
            const bookings = response.data.map(booking => ({
                ...booking,
                guestName: `${booking.userId}`, // This can be replaced with the actual user name if available
                roomType: `${booking.roomTypeId}`, // This can be replaced with the actual room type name if available
                checkInDate: new Date(booking.startDate),
                checkOutDate: new Date(booking.endDate),
                pricePerNight: booking.totalPrice / (new Date(booking.endDate) - new Date(booking.startDate)), // Example calculation
                status: 'Confirmed' // Example status, modify according to actual data
            }));
            setRows(bookings);
        } catch (err) {
            console.error('Error fetching bookings:', err);
        }
    };

    useEffect(() => {
        fetchBookings();
    }, []);

    return (
        <div className="main">
            <AdminNavBar />
            <div className="containerAdmin">
                <div className="menuContainer">
                    <AdminMenu />
                </div>
                <div className="contentContainer">
                    <div className="booking">
                        <div className="info">
                            <h1>Booking</h1>
                        </div>
                        <DataTable columns={columns} rows={rows} />
                    </div>
                </div>
            </div>
            <AdminFooter />
        </div>
    );
};

export default BookingAdminPage;


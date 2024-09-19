import React, { useEffect, useState } from "react";
import "./RoomTypePage.css";
import { Link, useLocation } from "react-router-dom";
import NavBar from "../../components/NavBar/NavBar";
import axios from "axios";

const RoomTypesPage = () => {
const location = useLocation();
const { propertyId, startDate, endDate } = location.state || {};

const [availabilityData, setAvailabilityData] = useState([]);
const [roomTypesData, setRoomTypesData] = useState([]);

useEffect(() => {
    const fetchAvailability = async () => {
    try {
        const availabilityResponse = await axios.get(
        `http://157.173.114.224:8080/bookings/availability?hotelId=${propertyId}&startDate=${startDate}&endDate=${endDate}`
        );
        setAvailabilityData(availabilityResponse.data);
    } catch (error) {
        console.error("Error fetching availability:", error);
    }
    };
    const fetchRoomTypes = async () => {
    try {
        const roomTypesResponse = await axios.get(
        "http://157.173.114.224:8080/roomtypes"
        );
        setRoomTypesData(roomTypesResponse.data);
    } catch (error) {
        console.error("Error fetching room types:", error);
    }
    };

    fetchAvailability();
    fetchRoomTypes();
}, [propertyId, startDate, endDate]);

const availableRoomTypes = roomTypesData.filter((roomType) =>
    availabilityData.some((availability) => availability[0] === roomType.id)
);

const handleBooking = async (room) => {
    const userId = localStorage.getItem("id");
    const totalPrice = room.basePrice; // Supongo que 'basePrice' es el precio total

    const bookingData = {
    startDate,
    endDate,
    totalPrice,
    roomTypeId: room.id,
    userId,
    };

    try {
    const response = await axios.post(
        "http://157.173.114.224:8080/bookings",
        bookingData
    );
    alert("Booking successful!");
    } catch (error) {
    console.error("Error creating booking:", error);
    alert("Booking failed. Please try again.");
    }
};

return (
    <div>
    <NavBar />
    <div className="roomTypesContainer">
        <h1 className="roomTypesTitle">Available Room Types</h1>
        <div className="roomCards">
        {availableRoomTypes.map((room) => {
            const availability = availabilityData.find(
            (avail) => avail[0] === room.id
            );
            return (
            <div className="roomCard" key={room.id}>
                <img
                src={room.image}
                alt={room.name}
                className="roomCardImage"
                />
                <div className="roomCardContent">
                <h2 className="roomCardTitle">{room.name}</h2>
                <p className="roomCardInfo">{room.description}</p>
                <p className="roomCardPrice">Price: ${room.basePrice}</p>
                <p className="roomCardAvailability">
                    Availability: {availability ? availability[1] : "Unknown"}
                </p>
                <button
                    onClick={() => handleBooking(room)}
                    className="roomCardButton"
                >
                    Book Now
                </button>
                </div>
            </div>
            );
        })}
        </div>
    </div>
    </div>
);
};

export default RoomTypesPage;

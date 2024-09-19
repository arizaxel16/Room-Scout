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
        `http://localhost:8080/bookings/availability?hotelId=${propertyId}&startDate=${startDate}&endDate=${endDate}`
        );

        alert(
        `Availability Data: ${JSON.stringify(availabilityResponse.data)}`
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

        alert(`Room Types Data: ${JSON.stringify(roomTypesResponse.data)}`);
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
                    Available Rooms:{" "}
                    {availability ? availability[1] : "Unknown"}
                </p>
                <Link to={`/book/${room.id}`} className="roomCardButton">
                    Book Now
                </Link>
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

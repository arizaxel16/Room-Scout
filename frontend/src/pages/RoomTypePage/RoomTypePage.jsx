import React from 'react';
import './RoomTypePage.css';
import { Link } from 'react-router-dom'; 
import NavBar from '../../components/NavBar/NavBar';

const RoomTypesPage = () => {
    const roomTypes = [
        {
            id: 1,
            name: 'Deluxe Suite',
            description: 'A luxurious room with a beautiful city view.',
            price: '$200 per night',
            image: 'https://via.placeholder.com/400x200', 
        },
        {
            id: 2,
            name: 'Standard Room',
            description: 'A comfortable room with modern amenities.',
            price: '$100 per night',
            image: 'https://via.placeholder.com/400x200', 
        },
        {
            id: 3,
            name: 'Family Room',
            description: 'A spacious room perfect for families.',
            price: '$150 per night',
            image: 'https://via.placeholder.com/400x200', 
        },
        {
            id: 4,
            name: 'Presidential Suite',
            description: 'The most luxurious suite with exclusive services.',
            price: '$500 per night',
            image: 'https://via.placeholder.com/400x200',
        }
    ];

    return (
        <div>
                <NavBar />
            <div className="roomTypesContainer">
                
                <h1 className="roomTypesTitle">Available Room Types</h1>
                <div className="roomCards">
                    {roomTypes.map(room => (
                        <div className="roomCard" key={room.id}>
                            <img src={room.image} alt={room.name} className="roomCardImage" />
                            <div className="roomCardContent">
                                <h2 className="roomCardTitle">{room.name}</h2>
                                <p className="roomCardInfo">{room.description}</p>
                                <p className="roomCardPrice">{room.price}</p>
                                <Link to={`/book/${room.id}`} className="roomCardButton">Book Now</Link>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div> 
    );
};

export default RoomTypesPage;

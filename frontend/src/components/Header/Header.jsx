import React from 'react';
import './Header.css';
import { FaHotel, FaBed } from "react-icons/fa";
import { MdVilla, MdApartment } from "react-icons/md";
import { FaIgloo, FaHouseChimney } from "react-icons/fa6";
import hotel from '../../assets/HOTEL_HEADER.jpg';
import villa from '../../assets/VILLA_HEADER.jpg';
import apartment from '../../assets/APARTMENT_HEADER.jpg';
import glamping from '../../assets/GLAMPING_HEADER.jpg';
import house from '../../assets/HOUSE_HEADER.jpg';
import motel from '../../assets/MOTEL_HEADER.jpg';

const Header = () => {
    const [selectedOption, setSelectedOption] = React.useState("hotel"); // Estado para la opción seleccionada
    const [selectedImage, setSelectedImage] = React.useState(hotel);

    // Mapea cada opción a una imagen
    const images = {
        hotel: hotel,
        villa: villa,
        apartment: apartment,
        glamping: glamping,
        house: house,
        motel: motel
    };

    const handleClick = (type) => {
        setSelectedOption(type); // Cambia la opción seleccionada
        setSelectedImage(images[type]); // Cambia la imagen de fondo
    };

    return (
        <div
            className="header"
            style={{ backgroundImage: `url(${selectedImage})`, backgroundSize: 'cover', backgroundPosition: 'center' }}
        >
            <div className="headerContainer">
                <div className="headerList">
                    <div
                        className={`headerListItem ${selectedOption === "hotel" ? "active" : ""}`}
                        onClick={() => handleClick("hotel")}
                    >
                        <FaHotel />
                        <span>Hotel</span>
                    </div>
                    <div
                        className={`headerListItem ${selectedOption === "villa" ? "active" : ""}`}
                        onClick={() => handleClick("villa")}
                    >
                        <MdVilla />
                        <span>Villa</span>
                    </div>
                    <div
                        className={`headerListItem ${selectedOption === "apartment" ? "active" : ""}`}
                        onClick={() => handleClick("apartment")}
                    >
                        <MdApartment />
                        <span>Apartment</span>
                    </div>
                    <div
                        className={`headerListItem ${selectedOption === "glamping" ? "active" : ""}`}
                        onClick={() => handleClick("glamping")}
                    >
                        <FaIgloo />
                        <span>Glamping</span>
                    </div>
                    <div
                        className={`headerListItem ${selectedOption === "house" ? "active" : ""}`}
                        onClick={() => handleClick("house")}
                    >
                        <FaHouseChimney />
                        <span>House</span>
                    </div>
                    <div
                        className={`headerListItem ${selectedOption === "motel" ? "active" : ""}`}
                        onClick={() => handleClick("motel")}
                    >
                        <FaBed />
                        <span>Motel</span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Header;

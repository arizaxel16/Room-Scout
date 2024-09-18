import React from 'react';
import { useHeaderContext } from '../../../context/HeaderContext';
import { FaHotel, FaBed } from "react-icons/fa";
import { MdVilla, MdApartment } from "react-icons/md";
import { FaIgloo, FaHouseChimney } from "react-icons/fa6";
import { useTheme } from '../../../context/ThemeContext';
import './HeaderList.css';

const iconMapping = {
    hotel: <FaHotel />,
    villa: <MdVilla />,
    apartment: <MdApartment />,
    glamping: <FaIgloo />,
    house: <FaHouseChimney />,
    motel: <FaBed />
};

const HeaderList = () => {
    const { theme } = useTheme();
    const { images, selectedImage, setSelectedImage, setSelectedCategory } = useHeaderContext();

    const handleClick = (type) => {
        setSelectedImage(images[type]);
        setSelectedCategory(type);
    };

    return (
        <div className={`headerList ${theme}`}>
            {Object.keys(images).map(type => (
                <div
                    key={type}
                    className={`headerListItem ${selectedImage === images[type] ? "active" : ""}`}
                    onClick={() => handleClick(type)}
                >
                    {iconMapping[type]}
                    <span>{type.charAt(0).toUpperCase() + type.slice(1)}</span>
                </div>
            ))}
        </div>
    );
};

export default HeaderList;

import React, { createContext, useState, useContext } from 'react';
import hotel from '../assets/HOTEL_HEADER.jpg';
import villa from '../assets/VILLA_HEADER.jpg';
import apartment from '../assets/APARTMENT_HEADER.jpg';
import glamping from '../assets/GLAMPING_HEADER.jpg';
import house from '../assets/HOUSE_HEADER.jpg';
import motel from '../assets/MOTEL_HEADER.jpg';

const HeaderContext = createContext();

export const HeaderProvider = ({ children }) => {
    const images = {
        hotel: hotel,
        villa: villa,
        apartment: apartment,
        glamping: glamping,
        house: house,
        motel: motel
    };

    const [selectedImage, setSelectedImage] = useState(images.hotel);
    const [selectedCategory, setSelectedCategory] = useState('hotel');
    const [searchData, setSearchData] = useState({
        destination: '',
        dates: [
            {
                startDate: new Date(),
                endDate: new Date(),
                key: 'selection'
            }
        ],
        options: {
            adult: 1,
            children: 0,
            room: 1
        }
    });
    return (
        <HeaderContext.Provider value={{ images, selectedImage, setSelectedImage, selectedCategory,
            setSelectedCategory, searchData, setSearchData }}>
            {children}
        </HeaderContext.Provider>
    );
};

export const useHeaderContext = () => useContext(HeaderContext);

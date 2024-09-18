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
    const data = {
        hotel: [
            { id: 1, name: 'Hotel 1', location: 'New York', image: 'https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'},
            { id: 2, name: 'Hotel 2', location: 'Paris', image:'https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'},
            { id: 3, name: 'Hotel 3', location: 'Paris', image:'https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'},
            { id: 4, name: 'Hotel 4', location: 'Paris', image:'https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'},
            { id: 2, name: 'Hotel 2', location: 'Paris', image:'https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'},
            { id: 2, name: 'Hotel 2', location: 'Paris', image:'https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'},
        ],
        villa: [
            { id: 1, name: 'Villa 1', location: 'Tuscany' },
            { id: 2, name: 'Villa 2', location: 'Bali' },
        ],
        apartment: [
            { id: 1, name: 'Apartment 1', location: 'Berlin' },
            { id: 2, name: 'Apartment 2', location: 'Madrid' },
        ],
        glamping: [
            { id: 1, name: 'Glamping 1', location: 'Iceland' },
            { id: 2, name: 'Glamping 2', location: 'New Zealand' },
        ],
        house: [
            { id: 1, name: 'House 1', location: 'Sydney' },
            { id: 2, name: 'House 2', location: 'Cape Town' },
        ],
        motel: [
            { id: 1, name: 'Motel 1', location: 'Las Vegas' },
            { id: 2, name: 'Motel 2', location: 'Los Angeles' },
        ]
    };

    return (
        <HeaderContext.Provider value={{ images, selectedImage, setSelectedImage, selectedCategory, data,
            setSelectedCategory, searchData, setSearchData }}>
            {children}
        </HeaderContext.Provider>
    );
};

export const useHeaderContext = () => useContext(HeaderContext);

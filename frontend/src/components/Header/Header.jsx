import React from 'react';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { DateRange } from 'react-date-range';
import {format} from 'date-fns';
import './Header.css';
import { FaHotel, FaBed } from "react-icons/fa";
import { MdVilla, MdApartment } from "react-icons/md";
import { FaIgloo, FaHouseChimney, FaPerson  } from "react-icons/fa6";
import { IoCalendarOutline } from "react-icons/io5";
import { BiWorld } from "react-icons/bi";
import hotel from '../../assets/HOTEL_HEADER.jpg';
import villa from '../../assets/VILLA_HEADER.jpg';
import apartment from '../../assets/APARTMENT_HEADER.jpg';
import glamping from '../../assets/GLAMPING_HEADER.jpg';
import house from '../../assets/HOUSE_HEADER.jpg';
import motel from '../../assets/MOTEL_HEADER.jpg';

const Header = ({ theme }) => {
    const [selectedOption, setSelectedOption] = React.useState("hotel");
    const [selectedImage, setSelectedImage] = React.useState(hotel);
    const [openDate, setOpenDate] = React.useState(false);
    const [date, setDate] = React.useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: 'selection'
        }
    ]);
    const [openOptions, setOpenOptions] = React.useState(false);
    const [options, setOptions] = React.useState({
        adult: 1,
        children: 0,
        room: 1
    });

    const handleOption = (name, operation) => {
        setOptions(prev => {
            return {
                ...prev,
                [name]: operation === "i" ? options[name] + 1 : options[name] - 1
            }
        });
    };

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
            className={`header ${theme}`}
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
                <div  className={`headerSearch ${theme}`}>
                    <div className="headerSearchItem">
                        <BiWorld className="headerIcon"/>
                        <input type="text" placeholder="Where are you going?" className={`headerSearchInput ${theme}`}/>
                    </div>
                    <div className="headerSearchItem">
                        <IoCalendarOutline className="headerIcon"/>
                        <span onClick={()=>setOpenDate(!openDate)} className="headerSearchText">{`${format(date[0].startDate, "MM/dd/yyyy")} to ${format(date[0].endDate, "MM/dd/yyyy")}`}</span>
                        {openDate  && <DateRange
                            editableDateInputs={true}
                            onChange={item => setDate([item.selection])}
                            moveRangeOnFirstSelection={false}
                            ranges={date}
                            className={`date ${theme}`}
                        />}
                    </div>
                    <div className="headerSearchItem">
                        <FaPerson className="headerIcon"/>
                        <span onClick={()=> setOpenOptions(!openOptions)} className="headerSearchText">{`${options.adult} adult • ${options.children} children • ${options.room} room`}</span>
                        {openOptions && <div className={`options ${theme}`}>
                            <div className="optionItem">
                                <span className="optionText">Adult</span>
                                <div className="optionCounter">
                                    <button disabled={options.adult <= 1} className={`optionCounterButton ${theme}`} onClick={()=>handleOption("adult","d")}>-</button>
                                    <span className="optionCounterNumber">{options.adult}</span>
                                    <button className={`optionCounterButton ${theme}`} onClick={()=>handleOption("adult","i")}>+</button>
                                </div>
                            </div>
                            <div className="optionItem">
                                <span className="optionText">Children</span>
                                <div className="optionCounter">
                                    <button disabled={options.children <= 0} className={`optionCounterButton ${theme}`} onClick={()=>handleOption("children","d")}>-</button>
                                    <span className="optionCounterNumber">{options.children}</span>
                                    <button className={`optionCounterButton ${theme}`} onClick={()=>handleOption("children","i")}>+</button>
                                </div>
                            </div>
                            <div className="optionItem">
                                <span className="optionText">Room</span>
                                <div className="optionCounter">
                                    <button disabled={options.room <= 1} className={`optionCounterButton ${theme}`} onClick={()=>handleOption("room","d")}>-</button>
                                    <span className="optionCounterNumber">{options.room}</span>
                                    <button className={`optionCounterButton ${theme}`}onClick={()=>handleOption("room","i")}>+</button>
                                </div>
                            </div>
                        </div>}
                    </div>
                    <div className="headerSearchItem">
                        <button className="headerBtn">Search</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Header;

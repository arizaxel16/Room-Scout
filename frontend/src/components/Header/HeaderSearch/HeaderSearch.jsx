import React from 'react';
import { useHeaderContext } from '../../../context/HeaderContext';
import { DateRange } from 'react-date-range';
import { format } from 'date-fns';
import { FaPerson } from "react-icons/fa6";
import { IoCalendarOutline } from "react-icons/io5";
import { BiWorld } from "react-icons/bi";
import { useTheme } from '../../../context/ThemeContext';
import './HeaderSearch.css';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';

const HeaderSearch = () => {
    const { theme } = useTheme();
    const { searchData, setSearchData  } = useHeaderContext();
    const [openDate, setOpenDate] = React.useState(false);
    const [openOptions, setOpenOptions] = React.useState(false);

    const handleOption = (name, operation) => {
        setSearchData(prev => ({
            ...prev,
            options: {
                ...prev.options,
                [name]: operation === "i" ? prev.options[name] + 1 : prev.options[name] - 1
            }
        }));
    };

    const toggleFilterDate = () => {
        setOpenDate(!openDate)
        setOpenOptions(false)
    }

    const toggleFilterOptions = () => {
        setOpenOptions(!openOptions)
        setOpenDate(false)
    }

    return (
        <div className={`headerSearch ${theme}`}>
            <div className="headerSearchItem">
                <BiWorld className={`headerIcon ${theme}`} />
                <input
                    type="text"
                    placeholder="Travel destination"
                    value={searchData.destination}
                    onChange={(e) => setSearchData(prev => ({ ...prev, destination: e.target.value }))}
                    className={`headerSearchInput ${theme}`}
                />
            </div>
            <div className="headerSearchItem">
                <IoCalendarOutline className={`headerIcon ${theme}`} />
                <span onClick={() => toggleFilterDate()} className={`headerSearchText ${theme}`}>
                    {`${format(searchData.dates[0].startDate, "MM/dd/yyyy")} to ${format(searchData.dates[0].endDate, "MM/dd/yyyy")}`}
                </span>
                {openDate && <DateRange
                    editableDateInputs={true}
                    onChange={item => setSearchData(prev => ({ ...prev, dates: [item.selection] }))}
                    moveRangeOnFirstSelection={false}
                    ranges={searchData.dates}
                    className={`date ${theme}`}
                />}
            </div>
            <div className="headerSearchItem">
                <FaPerson className={`headerIcon ${theme}`} />
                <span onClick={() => toggleFilterOptions()} className={`headerSearchText ${theme}`}>
                    {`${searchData.options.adult} adult • ${searchData.options.children} children • ${searchData.options.room} room`}
                </span>
                {openOptions && <div className={`options ${theme}`}>
                    <div className="optionItem">
                        <span className="optionText">Adult</span>
                        <div className="optionCounter">
                            <button disabled={searchData.options.adult <= 1} className={`optionCounterButton ${theme}`} onClick={() => handleOption("adult", "d")}>-</button>
                            <span className="optionCounterNumber">{searchData.options.adult}</span>
                            <button className={`optionCounterButton ${theme}`} onClick={() => handleOption("adult", "i")}>+</button>
                        </div>
                    </div>
                    <div className="optionItem">
                        <span className="optionText">Children</span>
                        <div className="optionCounter">
                            <button disabled={searchData.options.children <= 0} className={`optionCounterButton ${theme}`} onClick={() => handleOption("children", "d")}>-</button>
                            <span className="optionCounterNumber">{searchData.options.children}</span>
                            <button className={`optionCounterButton ${theme}`} onClick={() => handleOption("children", "i")}>+</button>
                        </div>
                    </div>
                    <div className="optionItem">
                        <span className="optionText">Room</span>
                        <div className="optionCounter">
                            <button disabled={searchData.options.room <= 1} className={`optionCounterButton ${theme}`} onClick={() => handleOption("room", "d")}>-</button>
                            <span className="optionCounterNumber">{searchData.options.room}</span>
                            <button className={`optionCounterButton ${theme}`} onClick={() => handleOption("room", "i")}>+</button>
                        </div>
                    </div>
                </div>}
            </div>
            <div className="headerSearchItem">
                <button className={`headerBtn ${theme}`}>Search</button>
            </div>
        </div>
    );
};

export default HeaderSearch;

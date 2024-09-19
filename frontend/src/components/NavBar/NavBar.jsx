import React from 'react';
import { Link } from 'react-router-dom';
import './NavBar.css';
import logo_light from '../../assets/LOGO_LIGHT.png';
import logo_dark from '../../assets/LOGO_DARK.png';
import { MdOutlineWbSunny, MdMenu } from "react-icons/md";
import { FaRegMoon, FaSearch, FaUser } from "react-icons/fa";
import { useTheme } from '../../context/ThemeContext';

const NavBar = () => {
    const { theme, setTheme } = useTheme();
    const [menuOpen, setMenuOpen] = React.useState(false);

    const toggleTheme = () => {
        setTheme(theme === 'light' ? 'dark' : 'light');
    };

    return (
        <nav className={`navbar ${theme}`}>
            <img src={theme === 'light' ? logo_light : logo_dark} alt="logo" className="logo" />
            
            <ul className={`nav-links ${menuOpen ? 'open' : ''}`}>
                <div className="search-box">
                    <input type="text" placeholder="Search" />
                    <FaSearch className="search-icon" />
                </div>
                <li>Home</li>
                <li>Room</li>
                <li>Features</li>
                <li>About</li>
                <li><Link to={'/admin'}>Admin</Link></li>
                
            </ul>

            <div className="icons">
                <MdOutlineWbSunny id='sun' className={`toggle-icon ${theme === 'light' ? '' : 'hidden'}`} onClick={toggleTheme} />
                <FaRegMoon id='moon' className={`toggle-icon ${theme === 'dark' ? '' : 'hidden'}`} onClick={toggleTheme} />
                <Link to="/user_auth" className="navLink-auth"><FaUser className="user-icon" /></Link>
                <MdMenu className="menu-icon" onClick={() => setMenuOpen(!menuOpen)} />
            </div>
        </nav>
    );
};

export default NavBar;

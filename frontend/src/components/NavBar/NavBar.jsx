import React from 'react';
import { Link } from 'react-router-dom';
import './NavBar.css';
import logo_light from '../../assets/logo-b.png';
import logo_dark from '../../assets/logo-w.png';
import search_icon_light from '../../assets/search-w.png';
import search_icon_dark from '../../assets/search-b.png';
import toggle_light from '../../assets/night.png';
import toggle_dark from '../../assets/day.png';

const NavBar = ({theme, setTheme}) => {
    const [menuOpen, setMenuOpen] = React.useState(false);
    const toggle_mode = () => {
        theme === 'light' ? setTheme('dark') : setTheme('light');
    }
    return (
        <nav>
            <img src={theme === 'light' ? logo_light : logo_dark} alt="" className="logo"/>
            <div className="menu" onClick={()=> setMenuOpen(!menuOpen)}>
                <span></span>
                <span></span>
                <span></span>
            </div>
            <ul className={`nav-links ${menuOpen ? 'open' : ''}`}>
                <li>Home</li>
                <li>Room</li>
                <li>Features</li>
                <li>About</li>
            </ul>
            <div className={`search-box ${menuOpen ? 'open' : ''}`}>
                <input type="text" placeholder='Search'/>
                <img src={theme === 'light' ? search_icon_light : search_icon_dark} className='search-icon' alt=""/>
            </div>
            <Link to={"/user_auth"}><button className={`register-button ${menuOpen ? 'open' : ''}`} >Register/Login</button></Link>


            <img onClick={() => {
                toggle_mode()
            }} src={theme === 'light' ? toggle_light : toggle_dark} alt="" className={`toggle-icon ${menuOpen ? 'open' : ''}`}/>


        </nav>
    );
};

export default NavBar;

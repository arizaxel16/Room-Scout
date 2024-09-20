import React from 'react'
import logo_light from '../../assets/LOGO_LIGHT.png';
import logo_dark from '../../assets/LOGO_DARK.png';
import { Link } from 'react-router-dom';

const Logo = () => {
    return (
        <Link to="/home">
            <img src={theme === 'light' ? logo_light : logo_dark} alt="logo" className="logo" />
        </Link>
    )
}

export default Logo
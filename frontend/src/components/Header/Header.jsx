import React from 'react';
import { useHeaderContext } from '../../context/HeaderContext';
import HeaderList from './HeaderList/HeaderList';
import HeaderSearch from './HeaderSearch/HeaderSearch';
import './Header.css';

const Header = () => {
    const { selectedImage, theme } = useHeaderContext();

    return (
        <div
            className={`header ${theme}`}
            style={{ backgroundImage: `url(${selectedImage})`, backgroundSize: 'cover', backgroundPosition: 'center' }}
        >
            <div className="headerContainer">
                <HeaderList />
                <HeaderSearch />
            </div>
        </div>
    );
};

export default Header;

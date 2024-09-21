import React from 'react';
import HeaderList from '../HeaderList/HeaderList';
import HeaderSearch from '../HeaderSearch/HeaderSearch';
import { useHeaderContext } from '../../../context/HeaderContext';
import { useTheme } from '../../../context/ThemeContext';
import './Header.css';

const Header = () => {
    const { theme } = useTheme();
    const { selectedImage } = useHeaderContext();

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

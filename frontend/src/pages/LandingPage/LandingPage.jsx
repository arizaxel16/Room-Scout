import React from 'react';
import './LandingPage.css';
import NavBar from "../../components/NavBar/NavBar";
import Header from "../../components/Header/Header";
import { HeaderProvider } from "../../context/HeaderContext";
import { useTheme } from '../../context/ThemeContext';

const LandingPage = () => {
    const { theme } = useTheme();
    return (
        <div className={`container ${theme}`}>
            <NavBar />
            <HeaderProvider>
                <Header />
            </HeaderProvider>
        </div>
    );
};

export default LandingPage;

import React from 'react';
import './Home.css';
import NavBar from "../../components/Generic/NavBar/NavBar";
import Header from "../../components/Home/Header/Header";
import { HeaderProvider } from "../../context/HeaderContext";
import { useTheme } from '../../context/ThemeContext';
import CategoryResults from '../../components/Home/CategoryResults/CategoryResults';

const LandingPage = () => {
    const { theme } = useTheme();
    return (
        <div className={`container ${theme}`}>
            <NavBar />
            <HeaderProvider>
                <Header />
                <CategoryResults />
            </HeaderProvider>
        </div>
    );
};

export default LandingPage;

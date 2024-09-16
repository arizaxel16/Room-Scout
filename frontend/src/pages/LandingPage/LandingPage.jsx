import React from 'react';
import './LandingPage.css';
import NavBar from "../../components/NavBar/NavBar";
import Header from "../../components/Header/Header";
import { HeaderProvider } from "../../context/HeaderContext";
import { ThemeProvider } from "../../context/ThemeContext";

const LandingPage = () => {
    const current_theme = localStorage.getItem('theme')
    const [theme, setTheme] = React.useState(current_theme? current_theme : 'light');

    React.useEffect(() => {
        localStorage.setItem('theme', theme);
    }, [theme])

    return (
        <div className={`container ${theme}`}>
            <NavBar theme={theme} setTheme={setTheme}/>
            <HeaderProvider>
                <Header theme={theme} setTheme={setTheme}/>
            </HeaderProvider>
        </div>
    );
};

export default LandingPage;

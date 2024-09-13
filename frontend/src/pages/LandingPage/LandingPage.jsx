import React from 'react';
import './LandingPage.css';
import NavBar from "../../components/NavBar/NavBar";

const LandingPage = () => {
    const current_theme = localStorage.getItem('theme')
    const [theme, setTheme] = React.useState(current_theme? current_theme : 'light');

    React.useEffect(() => {
        localStorage.setItem('theme', theme);
    }, [theme])
    return (
        <div className={`container ${theme}`}>
            <NavBar theme={theme} setTheme={setTheme}/>
        </div>
    );
};

export default LandingPage;

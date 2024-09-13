import React from 'react';
import './LandingPage.css';
import NavBar from "../../Components/NavBar/NavBar";

const LandingPage = () => {
    const current_theme = localStorage.getItem('theme')
    const [theme, setTheme] = React.useState(current_theme? current_theme : 'light');

    React.useEffect(() => {
        localStorage.setItem('theme', theme);
    }, [theme])
    return (
<<<<<<< feature/nav-bar
        <div className={`container ${theme}`}>
            <NavBar theme={theme} setTheme={setTheme}/>
=======
        <div className='landing-page'>
            <h1>LANDING PAGE - PLACEHOLDER</h1>
>>>>>>> feature/create-landingpage
        </div>
    );
};

export default LandingPage;

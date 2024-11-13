import React, { createContext, useContext, useState, useEffect } from 'react';

const ThemeContext = createContext();

export const ThemeProvider = ({ children }) => {
    const [theme, setTheme] = useState(localStorage.getItem('theme') || 'light');
    const [previousTheme, setPreviousTheme] = useState(null);
    const [isThemeEnforced, setIsThemeEnforced] = useState(false); 

    useEffect(() => {
        localStorage.setItem('theme', theme);
    }, [theme]);

    const forceTheme = (newTheme) => {
        if (!isThemeEnforced) { 
            setPreviousTheme(theme);
        }
        setTheme(newTheme);
        setIsThemeEnforced(true);
    };

    const restorePreviousTheme = () => {
        if (isThemeEnforced && previousTheme) {
            setTheme(previousTheme);
            setIsThemeEnforced(false);
        }
    };

    return (
        <ThemeContext.Provider value={{ theme, setTheme, forceTheme, restorePreviousTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};

export const useTheme = () => useContext(ThemeContext);
